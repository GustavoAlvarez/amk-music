package amktest.music.gustavo.amktest;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import amktest.music.gustavo.amktest.Adapters.SongAlbumAdapter;
import amktest.music.gustavo.amktest.Models.Constants;
import amktest.music.gustavo.amktest.Models.ResponseITunes;
import amktest.music.gustavo.amktest.Models.Result;
import amktest.music.gustavo.amktest.Utilities.Communication;
import amktest.music.gustavo.amktest.Utilities.Network;
import io.realm.Realm;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class ArtistsFragment extends Fragment {

    private Communication mCallback;
    private RecyclerView rvArtists;
    private String myCategory;
    private ProgressBar pbAlbums;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (Communication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ICommunication");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artists, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myCategory = bundle.getString(Constants.CATEGORY, "");
        }
        rvArtists = view.findViewById(R.id.rv_artists);
        pbAlbums = view.findViewById(R.id.pb_albums);
        ((MainActivity) getActivity()).updateToolbarTitle(myCategory);
        if (Network.isOnline(getContext())) {
            new Network(getContext(), myCategory, "album", onPostsLoaded, onPostsError);
        } else {
            pbAlbums.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(view, "No Internet", Snackbar.LENGTH_LONG).
                            setActionTextColor(Color.RED);

            snackbar.show();

            List<Result> localData = new ArrayList<>();

            localData.addAll(ITunesApplication.getInstance().getRealm().where(Result.class).findAll());

            initList(localData);
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            pbAlbums.setVisibility(View.GONE);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");

            final ResponseITunes posts = gson.fromJson(response, ResponseITunes.class);
            initList(posts.getResults());

            /*ITunesApplication.getInstance().getRealm().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(posts.getResults());
                }
            });*/
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            pbAlbums.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Error getting data", Toast.LENGTH_LONG).show();
        }
    };

    private void initList(List<Result> items) {
        ArrayList<Result> listItems = new ArrayList<>(items);
        SongAlbumAdapter adapter = new SongAlbumAdapter(listItems, mCallback, getContext(), Constants.ARTIST_ITEM);
        rvArtists.setHasFixedSize(true);
        rvArtists.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArtists.setAdapter(adapter);


    }

}
