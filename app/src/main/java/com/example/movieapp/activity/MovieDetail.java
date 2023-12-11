package com.example.movieapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.database.DatabaseHelper;
import com.example.movieapp.movieModel.MovieResults;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    String MovieTitle,MovieDate,MovieDesc,MoviePp,MovieBp;
    Double MovieRate;
    ImageView movLogo,movProf,movBp,movFav,movBackbt;
    TextView movtitle,movDate,movrate,movdesc;
    MovieResults movieResult;

    DatabaseHelper databaseHelper;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        databaseHelper = new DatabaseHelper(this);


        movLogo = findViewById(R.id.mov_logo);
        movProf = findViewById(R.id.Mov_Poster);
        movBp = findViewById(R.id.mov_bdrop);
        movFav= findViewById(R.id.mov_favbt);
        movBackbt= findViewById(R.id.mov_backbt);

        movtitle = findViewById(R.id.mov_title);
        movDate = findViewById(R.id.mov_date);
        movrate= findViewById(R.id.move_rate);
        movdesc= findViewById(R.id.mov_desc);

        movieResult = getIntent().getParcelableExtra(EXTRA_MOVIE);
        MovieTitle = movieResult.getTitle();
        MovieDesc = movieResult.getOverview();
        MovieDate = movieResult.getReleaseDate();
        MovieRate = movieResult.getVoteAverage();
        MoviePp = movieResult.getPosterPath();
        MovieBp = movieResult.getBackdropPath();

        movtitle.setText(MovieTitle);
        movDate.setText(MovieDate);
        movrate.setText(String.valueOf(MovieRate));
        movdesc.setText(MovieDesc);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185"+ MoviePp)
                .into(movProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original"+ MovieBp)
                .into(movBp);

        if (MovieTitle != null) {
            isFavorite = databaseHelper.isFavorite(MovieTitle, "movie");
        }

        if (isFavorite) {
            movFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            movFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        movFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    databaseHelper.removeFavorite(MovieTitle, "movie");
                    movFav.setImageResource(R.drawable.baseline_favorite_border_24);
                    isFavorite = false;
                    Toast.makeText(MovieDetail.this, MovieTitle + " removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.addFavorite(MovieTitle, "movie",MoviePp,MovieDate,MovieDesc,MovieBp,MovieRate);
                    movFav.setImageResource(R.drawable.baseline_favorite_24);
                    isFavorite = true;
                    Toast.makeText(MovieDetail.this, MovieTitle + " added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        movBackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}