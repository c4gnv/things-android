package c4gnv.com.thingsregistry.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import c4gnv.com.thingsregistry.R
import c4gnv.com.thingsregistry.net.model.Thing
import c4gnv.com.thingsregistry.util.PrefsUtil
import c4gnv.com.thingsregistry.util.bindView

class MainActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val fab: FloatingActionButton by bindView(R.id.fab)
    val thingListRecyclerView: RecyclerView by bindView(R.id.thing_list_recycler_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.main_activity_title)

        thingListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        fab.setOnClickListener { startActivity(AddThingActivity.newIntent(this@MainActivity)) }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val adapter = ListAdapter(PrefsUtil.getThings(this))
        thingListRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private inner class ListAdapter constructor(private val things: List<Thing>) : RecyclerView.Adapter<ThingHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingHolder {
            return ThingHolder(parent)
        }

        override fun onBindViewHolder(holder: ThingHolder, position: Int) {
            holder.bind(things[position])
        }

        override fun getItemCount(): Int {
            return this.things.size
        }
    }

    inner class ThingHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.row_thing, parent, false)) {

        val thingName: TextView by bindView(R.id.thing_name)
        val thingSerial: TextView by bindView(R.id.thing_serial)
        val rowLayout: LinearLayout by bindView(R.id.row_layout)
        private var thing: Thing = Thing()

        internal fun bind(thing: Thing) {
            this.thing = thing
            this.thingName.text = thing.name
            this.thingSerial.text = thing.serialNumber

            rowLayout.setOnClickListener({
                startActivity(ThingDetailActivity.newIntent(this@MainActivity, this.thing))
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_things) {
            PrefsUtil.deleteAllThings(this)
            updateUI()
        }
        return true
    }
}
