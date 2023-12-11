package com.example.movieapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.movieapp.R;
import com.example.movieapp.fragment.FavFragment;
import com.example.movieapp.fragment.MovieFragment;
import com.example.movieapp.fragment.tvFragment;

public class MainActivity extends AppCompatActivity {

    ImageView telev,movie,fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telev = findViewById(R.id.televbt);
        movie = findViewById(R.id.moviebt);
        fav = findViewById(R.id.favoritebt);


        FragmentManager fragmentManager = getSupportFragmentManager();
        MovieFragment homeFragment = new MovieFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(MovieFragment.class.getSimpleName());
        if (!(fragment instanceof MovieFragment)) {
            fragmentManager.beginTransaction().add(R.id.container, homeFragment,
                    MovieFragment.class.getSimpleName()).commit();
        }

        telev.setOnClickListener( v -> switchFragment(new tvFragment()) );
        movie.setOnClickListener( v -> switchFragment(new MovieFragment()));
        fav.setOnClickListener( v -> switchFragment(new FavFragment()));
    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragment instanceof MovieFragment){
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).commit();
        } else {
            transaction.replace(R.id.container, fragment, MovieFragment.class.getSimpleName()).addToBackStack(null).commit();
        }

    }
}