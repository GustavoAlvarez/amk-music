package amktest.music.gustavo.amktest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import amktest.music.gustavo.amktest.Models.Categories;
import amktest.music.gustavo.amktest.Models.Constants;
import amktest.music.gustavo.amktest.R;
import amktest.music.gustavo.amktest.Utilities.Communication;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<Categories> items;
    public Communication mCallBack;

    public CategoriesAdapter(ArrayList<Categories> items, Communication mCallBack) {
        this.items = items;
        this.mCallBack = mCallBack;
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Categories categories = items.get(position);

        holder.tvName.setText(categories.getName());
        holder.ivImage.setImageDrawable(categories.getImage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView ivImage;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_category_name);
            ivImage = v.findViewById(R.id.iv_category_image);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallBack.onResponse(Constants.CATEGORY_ITEM, items.get(getAdapterPosition()));
        }
    }

}
