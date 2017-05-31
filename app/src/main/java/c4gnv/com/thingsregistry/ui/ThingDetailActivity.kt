package c4gnv.com.thingsregistry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import c4gnv.com.thingsregistry.App
import c4gnv.com.thingsregistry.R
import c4gnv.com.thingsregistry.net.ServiceCallback
import c4gnv.com.thingsregistry.net.ServiceError
import c4gnv.com.thingsregistry.net.model.EventPostRequest
import c4gnv.com.thingsregistry.net.model.EventPostResponse
import c4gnv.com.thingsregistry.net.model.Piece
import c4gnv.com.thingsregistry.net.model.Thing
import c4gnv.com.thingsregistry.util.bindView

class ThingDetailActivity : AppCompatActivity() {

    val thingDetailDescription: TextView by bindView(R.id.thing_detail_description)
    val thingDetailPieceList: RecyclerView by bindView(R.id.thing_detail_piece_list)
    val thingDetailSendButton: Button by bindView(R.id.thing_detail_send_button)

    private var thing: Thing? = null
    private var adapter: ListAdapter? = null
    private var pieceCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thing_detail)

        setTitle(R.string.thing_detail_activity_title)

        this.thing = intent.extras.getSerializable(KEY_THING) as Thing
        title = thing!!.name + " (" + thing!!.serialNumber!!.substring(0, 5) + ")"
        thingDetailDescription.text = getString(R.string.thing_detail_description, thing!!.description)
        thingDetailPieceList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ListAdapter(thing!!.getPieces())
        thingDetailPieceList.adapter = adapter

        thingDetailSendButton.setOnClickListener({
            val pieces = adapter!!.pieces
            pieceCount = pieces.size

            for (piece in adapter!!.pieces) {
                val url = piece.url!!
                val authToken = "Bearer " + piece.token
                val request: EventPostRequest

                val state = piece.state
                when (state) {
                    Piece.PieceState.DIAGNOSTIC -> request = piece.diagnosticEvent!!
                    Piece.PieceState.WARNING -> request = piece.warningEvent!!
                    Piece.PieceState.FAULT -> request = piece.faultEvent!!
                    Piece.PieceState.NORMAL -> request = piece.normalEvent!!
                    else -> request = piece.normalEvent!!
                }

                request.serialNumber = piece.serialNumber
                val serialNumber = piece.serialNumber!!.substring(0, 5)

                App.get().getServiceApi().postEvent(url, authToken, request).enqueue(object : ServiceCallback<EventPostResponse>() {
                    override fun onSuccess(response: EventPostResponse) {
                        Toast.makeText(this@ThingDetailActivity, "Success ($serialNumber)", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(error: ServiceError) {
                        Toast.makeText(this@ThingDetailActivity, "Failure ($serialNumber)", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })
    }

    private inner class ListAdapter constructor(val pieces: List<Piece>) : RecyclerView.Adapter<PieceHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieceHolder {
            return PieceHolder(parent)
        }

        override fun onBindViewHolder(holder: PieceHolder, position: Int) {
            holder.bind(pieces[position])
        }

        override fun getItemCount(): Int {
            return this.pieces.size
        }
    }

    inner class PieceHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(this@ThingDetailActivity).inflate(R.layout.row_piece, parent, false)), AdapterView.OnItemSelectedListener {

        val pieceName: TextView by bindView(R.id.piece_name)
        val pieceSpinner: AppCompatSpinner by bindView(R.id.piece_spinner)

        private var piece: Piece? = null

        internal fun bind(piece: Piece) {
            this.piece = piece
            val name = piece.name
            val serial = piece.serialNumber!!.substring(0, 5)
            this.pieceName.text = "$name ($serial)"
            val stateAdapter = ArrayAdapter.createFromResource(this@ThingDetailActivity, R.array.state_array, android.R.layout.simple_spinner_item)
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pieceSpinner.onItemSelectedListener = this
            pieceSpinner.adapter = stateAdapter
        }

        override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
            piece!!.state = Piece.PieceState.values()[i]
        }

        override fun onNothingSelected(adapterView: AdapterView<*>) {
            // No-op
        }
    }

    companion object {

        private val KEY_THING = "thingDetailThing"

        fun newIntent(context: Context, thing: Thing): Intent {
            val thingDetailIntent = Intent(context, ThingDetailActivity::class.java)
            thingDetailIntent.putExtra(KEY_THING, thing)
            return thingDetailIntent
        }
    }
}
