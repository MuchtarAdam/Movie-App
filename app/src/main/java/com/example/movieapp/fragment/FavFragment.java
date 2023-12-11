package com.example.movieapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapter.FavAdapter;
import com.example.movieapp.database.DatabaseHelper;

public class FavFragment extends Fragment {

    private RecyclerView favRV;
    private FavAdapter favAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        favRV = view.findViewById(R.id.favRV);
        favRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favAdapter = new FavAdapter(getContext());
        favRV.setAdapter(favAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        favAdapter.fetchFavoriteMovies();
        favAdapter.fetchFavoriteTVShows();

        return view;
    }
}