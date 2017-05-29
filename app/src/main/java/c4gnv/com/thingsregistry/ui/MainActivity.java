package c4gnv.com.thingsregistry.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4gnv.com.thingsregistry.App;
import c4gnv.com.thingsregistry.R;
import c4gnv.com.thingsregistry.net.ServiceCallback;
import c4gnv.com.thingsregistry.net.ServiceError;
import c4gnv.com.thingsregistry.net.model.EventPostRequest;
import c4gnv.com.thingsregistry.net.model.EventPostResponse;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                /*
                String url = "https://ingestion-7aaxydpjj81u.us3.sfdcnow.com/streams/davestantoninputs001/davestantoninputs001/event";
                String authorizationToken = "Bearer " + "EvvuS9dp4VrzpV9X1Z5yw7OOyWYrSDKOjFQ6hoBB67pdXQt212b21LFwLYD1lMPnh3qgZS0gzhwkh3JNHmlxBG";

                EventPostRequest eventPostRequest = new EventPostRequest();
                eventPostRequest.setSerialNumber("G030JF055216JC4M");
                eventPostRequest.setBatteryVoltage("1708mV");
                eventPostRequest.setClickType("SINGLE");

                App.get().getServiceApi().postEvent(url, authorizationToken, eventPostRequest).enqueue(new ServiceCallback<EventPostResponse>() {
                    @Override
                    public void onSuccess(EventPostResponse response) {
                        Toast.makeText(MainActivity.this, "Count: " + response.getCount(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFail(ServiceError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                */
            }
        });
    }
}
