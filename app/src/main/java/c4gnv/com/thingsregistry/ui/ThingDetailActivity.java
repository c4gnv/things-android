package c4gnv.com.thingsregistry.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import c4gnv.com.thingsregistry.net.model.Piece;
import c4gnv.com.thingsregistry.net.model.Thing;
import c4gnv.com.thingsregistry.net.model.ThingType;
import c4gnv.com.thingsregistry.util.StringUtil;

public class ThingDetailActivity extends AppCompatActivity {

    private static final String KEY_THING = "thingDetailThing";

    @BindView(R.id.thing_detail_description)
    TextView thingDetailDescription;

    @BindView(R.id.thing_detail_piece_list)
    RecyclerView thingDetailPieceList;

    private Thing thing;
    private ListAdapter adapter;
    private int pieceCount = 0;
    private int piecesCompleted = 0;

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
        adapter = new ListAdapter(thing.getPieces());
        thingDetailPieceList.setAdapter(adapter);
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

        public List<Piece> getPieces() {
            return pieces;
        }
    }

    public class PieceHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {

        @BindView(R.id.piece_name)
        TextView pieceName;

        @BindView(R.id.piece_spinner)
        AppCompatSpinner pieceSpinner;

        private Piece piece;

        PieceHolder(ViewGroup parent) {
            super(LayoutInflater.from(ThingDetailActivity.this).inflate(R.layout.row_piece, parent, false));
            ButterKnife.bind(this, itemView);
        }

        void bind(Piece piece) {
            this.piece = piece;
            this.pieceName.setText(piece.getName() + " (" + piece.getSerialNumber().substring(0, 5) + ")");
            ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(ThingDetailActivity.this, R.array.state_array, android.R.layout.simple_spinner_item);
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pieceSpinner.setOnItemSelectedListener(this);
            pieceSpinner.setAdapter(stateAdapter);
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            piece.setState(Piece.PieceState.values()[i]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // No-op
        }
    }

    @OnClick(R.id.thing_detail_send_button)
    public void onClickSendEvents(View view) {
        List<Piece> pieces = adapter.getPieces();
        pieceCount = pieces.size();

        for (final Piece piece : adapter.getPieces()) {
            String url = piece.getUrl();
            String authToken = "Bearer " + piece.getToken();
            EventPostRequest request;

            Piece.PieceState state = piece.getState();
            switch (state) {
                case DIAGNOSTIC:
                    request = piece.getDiagnosticEvent();
                    break;
                case WARNING:
                    request = piece.getWarningEvent();
                    break;
                case FAULT:
                    request = piece.getFaultEvent();
                    break;
                case NORMAL:
                default:
                    request = piece.getNormalEvent();
                    break;
            }

            request.setSerialNumber(piece.getSerialNumber());

            App.get().getServiceApi().postEvent(url, authToken, request).enqueue(new ServiceCallback<EventPostResponse>() {
                @Override
                public void onSuccess(EventPostResponse response) {
                    Toast.makeText(ThingDetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(ServiceError error) {
                    Toast.makeText(ThingDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
