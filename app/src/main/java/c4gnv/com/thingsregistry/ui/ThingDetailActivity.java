package c4gnv.com.thingsregistry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import c4gnv.com.thingsregistry.R;
import c4gnv.com.thingsregistry.net.model.Piece;
import c4gnv.com.thingsregistry.net.model.Thing;

public class ThingDetailActivity extends AppCompatActivity {

    private static final String KEY_THING = "thingDetailThing";

    @BindView(R.id.thing_detail_description)
    TextView thingDetailDescription;

    @BindView(R.id.thing_detail_piece_list)
    RecyclerView thingDetailPieceList;

    private Thing thing;

    public static Intent newIntent(Context context, Thing thing) {
        Intent thingDetailIntent = new Intent(context, ThingDetailActivity.class);
        thingDetailIntent.putExtra(KEY_THING, thing);
        return thingDetailIntent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_detail);
        ButterKnife.bind(this);

        setTitle(R.string.thing_detail_activity_title);

        this.thing = (Thing) getIntent().getExtras().getSerializable(KEY_THING);
        setTitle(thing.getName() + " (" + thing.getSerialNumber().substring(0, 5) + ")");
        thingDetailDescription.setText(getString(R.string.thing_detail_description, thing.getDescription()));
        thingDetailPieceList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        thingDetailPieceList.setAdapter(new ListAdapter(thing.getPieces()));
    }

    @OnClick(R.id.thing_detail_send_button)
    public void onClickSendEvents() {
        Toast.makeText(this, "Clicked Send Events", Toast.LENGTH_LONG).show();
    }

    private class ListAdapter extends RecyclerView.Adapter<PieceHolder> {

        private List<Piece> pieces;

        private ListAdapter(List<Piece> pieces) {
            this.pieces = pieces;
        }

        @Override
        public PieceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PieceHolder(parent);
        }

        @Override
        public void onBindViewHolder(PieceHolder holder, int position) {
            holder.bind(pieces.get(position));
        }

        @Override
        public int getItemCount() {
            return this.pieces.size();
        }
    }

    public class PieceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.piece_name)
        TextView pieceName;

        @BindView(R.id.piece_serial)
        TextView pieceSerial;

        private Piece piece;

        PieceHolder(ViewGroup parent) {
            super(LayoutInflater.from(ThingDetailActivity.this).inflate(R.layout.row_piece, parent, false));
            ButterKnife.bind(this, itemView);
        }

        void bind(Piece piece) {
            this.piece = piece;
            this.pieceName.setText(piece.getName());
            this.pieceSerial.setText(piece.getSerialNumber());
        }
    }

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
