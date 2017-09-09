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

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class ArtistSongsFragment extends Fragment {

    private Communication mCallback;
    private RecyclerView rvArtistsSongs;
    private String myArtist;
    private ProgressBar pbSongs;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (Communication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Communication");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myArtist = bundle.getString(Constants.ARTIST, "");
        }
        rvArtistsSongs = view.findViewById(R.id.rv_artists_songs);
        pbSongs = view.findViewById(R.id.pb_songs);

        ((MainActivity)getActivity()).updateToolbarTitle(myArtist);
        if(Network.isOnline(getContext())) {
            new Network(getContext(), myArtist, "song", onPostsLoaded, onPostsError);

        }
        else{
            pbSongs.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(view, "No Internet", Snackbar.LENGTH_LONG).
                            setActionTextColor(Color.RED);

            snackbar.show();
        }

    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            pbSongs.setVisibility(View.GONE);
            Gson gson = gsonBuilder.create();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");

            ResponseITunes posts = gson.fromJson(response, ResponseITunes.class);
            initList(posts.getResults());
            for (Result post : posts.getResults()) {
                Log.i("PostActivity", post.getArtistName() + ": " + post.getTrackName());
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            pbSongs.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Error al obtener datos" , Toast.LENGTH_LONG).show();
        }
    };


    private void initList(List<Result> items) {
        ArrayList<Result> listItems= new ArrayList<>(items);
        SongAlbumAdapter adapter=new SongAlbumAdapter(listItems, mCallback, getContext(), Constants.SONG_ITEM);
        rvArtistsSongs.setHasFixedSize(true);
        rvArtistsSongs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvArtistsSongs.setAdapter(adapter);


    }

}
