package amktest.music.gustavo.amktest;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import amktest.music.gustavo.amktest.Adapters.CategoriesAdapter;
import amktest.music.gustavo.amktest.Models.Categories;
import amktest.music.gustavo.amktest.Utilities.Communication;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class CategoryFragment extends Fragment {

    private RecyclerView rvCategories;
    private ArrayList<Categories> categories;

    Communication mCallback;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.category_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).updateToolbarTitle("Categories");
        rvCategories=view.findViewById(R.id.rv_categories);

        categories=new ArrayList<>();
        categories.add(new Categories("Metal", ContextCompat.getDrawable(getContext(), R.mipmap.metal)));
        categories.add(new Categories("Rock", ContextCompat.getDrawable(getContext(), R.mipmap.rock)));
        categories.add(new Categories("Punk", ContextCompat.getDrawable(getContext(), R.mipmap.punk)));
        categories.add(new Categories("Clasic", ContextCompat.getDrawable(getContext(), R.mipmap.clasic)));
        categories.add(new Categories("Reggae", ContextCompat.getDrawable(getContext(), R.mipmap.reggae)));

        CategoriesAdapter categoryAdapter=new CategoriesAdapter(categories,mCallback);
        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);

    }

}
