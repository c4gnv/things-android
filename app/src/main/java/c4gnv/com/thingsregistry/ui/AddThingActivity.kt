package c4gnv.com.thingsregistry.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast

import c4gnv.com.thingsregistry.App
import c4gnv.com.thingsregistry.R
import c4gnv.com.thingsregistry.net.ServiceCallback
import c4gnv.com.thingsregistry.net.ServiceError
import c4gnv.com.thingsregistry.net.model.Piece
import c4gnv.com.thingsregistry.net.model.Thing
import c4gnv.com.thingsregistry.net.model.ThingType
import c4gnv.com.thingsregistry.util.PrefsUtil
import c4gnv.com.thingsregistry.util.bindView

class AddThingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val typeSpinner: AppCompatSpinner by bindView(R.id.type_spinner)
    val thingSpinner: AppCompatSpinner by bindView(R.id.thing_spinner)
    val createThingButton: Button by bindView(R.id.create_thing_button)

    private var thingTypes: List<ThingType>? = null
    private var things: List<Thing>? = null
    private var selectedThingType: ThingType? = null
    private var selectedThing: Thing? = null
    private var isLoading: Boolean = false
    private var pieceCount = 0
    private var piecesProcessed = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thing)

        setTitle(R.string.add_thing_activity_title)

        createThingButton.setOnClickListener({
            if (createThingButton.isEnabled && selectedThing != null) {
                selectedThing!!.generateSerial()
                pieceCount = selectedThing!!.pieceId!!.size
                for (pieceId in selectedThing!!.pieceId!!) {
                    App.get().getServiceApi().getPieceById(pieceId.toString()).enqueue(object : ServiceCallback<Piece>() {
                        override fun onSuccess(response: Piece) {
                            response.generateSerial()
                            selectedThing!!.addPiece(response)
                            piecesProcessed++
                            finishIfComplete()
                        }

                        override fun onFail(error: ServiceError) {
                            apiError()
                            finish()
                        }
                    })
                }
            }
        })


        updateUI()

        loadThingTypes()
    }

    private fun loadThingTypes() {
        isLoading = true
        App.get().getServiceApi().types.enqueue(object : ServiceCallback<List<ThingType>>() {
            override fun onSuccess(response: List<ThingType>) {
                thingTypes = response
                val thingTypeAdapter = ArrayAdapter(this@AddThingActivity, android.R.layout.simple_spinner_item, response)
                thingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                typeSpinner.adapter = thingTypeAdapter
                typeSpinner.onItemSelectedListener = this@AddThingActivity
                isLoading = false
                updateUI()
            }

            override fun onFail(error: ServiceError) {
                apiError()
                isLoading = false
                updateUI()
            }
        })
    }

    private fun loadThings(thingType: String) {
        isLoading = true
        App.get().getServiceApi().getThingsByType(thingType).enqueue(object : ServiceCallback<List<Thing>>() {
            override fun onSuccess(response: List<Thing>) {
                things = response
                val thingAdapter = ArrayAdapter(this@AddThingActivity, android.R.layout.simple_spinner_item, response)
                thingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                thingSpinner.adapter = thingAdapter
                thingSpinner.onItemSelectedListener = this@AddThingActivity
                isLoading = false
                updateUI()
            }

            override fun onFail(error: ServiceError) {
                apiError()
                isLoading = false
                updateUI()
            }
        })
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        when (adapterView.id) {
            R.id.type_spinner -> {
                this.selectedThingType = thingTypes!![i]
                loadThings(selectedThingType!!.id!!)
            }
            R.id.thing_spinner -> this.selectedThing = things!![i]
            else -> {}
        }

        updateUI()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {
        // No-op
    }

    private fun updateUI() {
        createThingButton.isEnabled = selectedThingType != null && selectedThing != null && !isLoading
    }

    private fun finishIfComplete() {
        if (piecesProcessed == pieceCount) {
            PrefsUtil.addThing(this, selectedThing!!)
            finish()
        }
    }

    private fun apiError() {
        Toast.makeText(this@AddThingActivity, R.string.api_error, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddThingActivity::class.java)
        }
    }
}
