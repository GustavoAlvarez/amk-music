package amktest.music.gustavo.amktest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import amktest.music.gustavo.amktest.Models.Constants;
import amktest.music.gustavo.amktest.Models.Result;
import amktest.music.gustavo.amktest.R;
import amktest.music.gustavo.amktest.Utilities.Communication;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class SongAlbumAdapter extends RecyclerView.Adapter<SongAlbumAdapter.ViewHolder>{

    private ArrayList<Result> items;
    public Communication mCallBack;
    Context ctx;
    String origin;

    public SongAlbumAdapter(ArrayList<Result> items, Communication mCallBack, Context ctx, String origin) {
        this.items = items;
        this.mCallBack = mCallBack;
        this.ctx = ctx;
        this.origin = origin;
    }

    @Override
    public SongAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);

        return new SongAlbumAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result item = items.get(position);
        holder.tvArtist.setText(item.getArtistName());
        holder.tvAlbum.setText(item.getCollectionName());

        String priceCollection = String.valueOf(item.getCollectionPrice());
        String currency = String.valueOf(item.getCurrency());
        holder.tvPriceDisc.setText(priceCollection + " " + currency);

        if (item.getTrackPrice() != null) {
            String price = String.valueOf(item.getTrackPrice());
            holder.tvPriceTrack.setText(price + " " + currency);

        } else {
            holder.tvPriceTrack.setVisibility(View.GONE);
            holder.tvSongPriceLabel.setVisibility(View.GONE);
        }

        holder.tvCountry.setText(item.getCountry());

        Picasso.with(ctx).load(item.getArtworkUrl100()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivImage;
        private TextView tvArtist;
        private TextView tvAlbum;
        private TextView tvPriceDisc;
        private TextView tvPriceTrack;
        private TextView tvCountry;
        private TextView tvSongPriceLabel;


        ViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.iv_artist_image);
            tvArtist = v.findViewById(R.id.tv_artist);
            tvAlbum = v.findViewById(R.id.tv_album);
            tvPriceDisc = v.findViewById(R.id.tv_price_disc);
            tvPriceTrack = v.findViewById(R.id.tv_price_track);
            tvCountry = v.findViewById(R.id.tv_country);
            tvSongPriceLabel = v.findViewById(R.id.tv_song_price_label);

            v.setOnClickListener(this);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View view) {
            if (origin.equals(Constants.ARTIST_ITEM))
                mCallBack.onResponse(Constants.ARTIST_ITEM, items.get(getAdapterPosition()));
            else {
                mCallBack.onResponse(Constants.SONG_ITEM, items.get(getAdapterPosition()));
            }
        }
    }

}
