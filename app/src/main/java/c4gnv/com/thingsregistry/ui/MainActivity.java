package c4gnv.com.thingsregistry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c4gnv.com.thingsregistry.App;
import c4gnv.com.thingsregistry.R;
import c4gnv.com.thingsregistry.net.ServiceCallback;
import c4gnv.com.thingsregistry.net.ServiceError;
import c4gnv.com.thingsregistry.net.model.EventPostRequest;
import c4gnv.com.thingsregistry.net.model.EventPostResponse;
import c4gnv.com.thingsregistry.net.model.Thing;
import c4gnv.com.thingsregistry.util.PrefsUtil;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.thing_list_recycler_view)
    RecyclerView thingListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.main_activity_title);
        setSupportActionBar(toolbar);

        thingListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddThingActivity.newIntent(MainActivity.this));
            }
        });

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ListAdapter adapter = new ListAdapter(PrefsUtil.getThings(this));
        thingListRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class ListAdapter extends RecyclerView.Adapter<ThingHolder> {

        private List<Thing> things;

        private ListAdapter(List<Thing> things) {
            this.things = things;
        }

        @Override
        public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ThingHolder(parent);
        }

        @Override
        public void onBindViewHolder(ThingHolder holder, int position) {
            holder.bind(things.get(position));
        }

        @Override
        public int getItemCount() {
            return this.things.size();
        }
    }

    public class ThingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thing_name)
        TextView thingName;

        @BindView(R.id.thing_serial)
        TextView thingSerial;

        private Thing thing;

        ThingHolder(ViewGroup parent) {
            super(LayoutInflater.from(MainActivity.this).inflate(R.layout.row_thing, parent, false));
            ButterKnife.bind(this, itemView);
        }

        void bind(Thing thing) {
            this.thing = thing;
            this.thingName.setText(thing.getName());
            this.thingSerial.setText(thing.getSerialNumber());
        }

        @OnClick(R.id.row_layout)
        public void onClickRow() {
            startActivity(ThingDetailActivity.newIntent(MainActivity.this, this.thing));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_things) {
            PrefsUtil.deleteAllThings(this);
            updateUI();
        }
        return true;
    }
}
