package c4gnv.com.thingsregistry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import c4gnv.com.thingsregistry.R;

public class ThingDetailActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ThingDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        ButterKnife.bind(this);

        setTitle(R.string.thing_detail_activity_title);
    }
}
