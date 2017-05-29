package c4gnv.com.thingsregistry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import c4gnv.com.thingsregistry.R;

public class AddThingActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, AddThingActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);
        ButterKnife.bind(this);

        setTitle(R.string.add_thing_activity_title);
    }
}
