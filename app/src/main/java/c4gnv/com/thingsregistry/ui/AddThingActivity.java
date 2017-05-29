package c4gnv.com.thingsregistry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import c4gnv.com.thingsregistry.App;
import c4gnv.com.thingsregistry.R;
import c4gnv.com.thingsregistry.net.ServiceCallback;
import c4gnv.com.thingsregistry.net.ServiceError;
import c4gnv.com.thingsregistry.net.model.Thing;
import c4gnv.com.thingsregistry.net.model.ThingType;

public class AddThingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.type_spinner)
    AppCompatSpinner typeSpinner;

    @BindView(R.id.thing_spinner)
    AppCompatSpinner thingSpinner;

    @BindView(R.id.create_thing_button)
    Button createThingButton;

    private List<ThingType> thingTypes;
    private List<Thing> things;
    private ThingType selectedThingType;
    private Thing selectedThing;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddThingActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);
        ButterKnife.bind(this);

        setTitle(R.string.add_thing_activity_title);

        updateUI();

        loadThingTypes();
    }

    private void loadThingTypes() {
        App.get().getServiceApi().getTypes().enqueue(new ServiceCallback<List<ThingType>>() {
            @Override
            public void onSuccess(List<ThingType> response) {
                thingTypes = response;
                ArrayAdapter<ThingType> thingTypeAdapter = new ArrayAdapter<>(AddThingActivity.this, android.R.layout.simple_spinner_item, response);
                thingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(thingTypeAdapter);
                typeSpinner.setOnItemSelectedListener(AddThingActivity.this);
            }

            @Override
            public void onFail(ServiceError error) {
                Toast.makeText(AddThingActivity.this, R.string.api_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadThings(String thingType) {
        App.get().getServiceApi().getThingsByType(thingType).enqueue(new ServiceCallback<List<Thing>>() {
            @Override
            public void onSuccess(List<Thing> response) {
                things = response;
                ArrayAdapter<Thing> thingAdapter = new ArrayAdapter<>(AddThingActivity.this, android.R.layout.simple_spinner_item, response);
                thingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                thingSpinner.setAdapter(thingAdapter);
                thingSpinner.setOnItemSelectedListener(AddThingActivity.this);
            }

            @Override
            public void onFail(ServiceError error) {
                Toast.makeText(AddThingActivity.this, R.string.api_error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.type_spinner:
                this.selectedThingType = thingTypes.get(i);
                loadThings(selectedThingType.getId());
                break;
            case R.id.thing_spinner:
                this.selectedThing = things.get(i);
                break;
            default:
                break;
        }

        updateUI();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // No-op
    }

    private void updateUI() {
        if (selectedThingType != null && selectedThing != null) {
            createThingButton.setEnabled(true);
        } else {
            createThingButton.setEnabled(false);
        }
    }
}
