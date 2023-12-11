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
import com.example.movieapp.televModel.televResults;

public class tvDetail extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    String televTitle, televDate, televDesc, televPp, televBp;
    Double televRate;
    ImageView tvLogo, tvProf, tvBp, tvFav, tvBackbt;
    TextView tvtitle, tvDate, tvrate, tvdesc;
    televResults tvresult;

    DatabaseHelper databaseHelper;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        databaseHelper = new DatabaseHelper(this);

        tvLogo = findViewById(R.id.telev_logo);
        tvProf = findViewById(R.id.telev_Poster);
        tvBp = findViewById(R.id.telev_bdrop);
        tvFav = findViewById(R.id.telev_favbt);
        tvBackbt = findViewById(R.id.telev_backbt);

        tvtitle = findViewById(R.id.telev_title);
        tvDate = findViewById(R.id.telev_date);
        tvrate = findViewById(R.id.telev_rate);
        tvdesc = findViewById(R.id.telev_desc);

        tvresult = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        televTitle = tvresult.getName();
        televDesc = tvresult.getOverview();
        televDate = tvresult.getFirstAirDate();
        televRate = tvresult.getVoteAverage();
        televPp = tvresult.getPosterPath();
        televBp = tvresult.getBackdropPath();

        if (televTitle != null) {
            tvtitle.setText(televTitle);
        } else {
            tvtitle.setText("N/A");
        }

        if (televDate != null) {
            tvDate.setText(televDate);
        } else {
            tvDate.setText("N/A");
        }

        if (televRate != null) {
            tvrate.setText(String.valueOf(televRate));
        } else {
            tvrate.setText("N/A");
        }

        if (televDesc != null) {
            tvdesc.setText(televDesc);
        } else {
            tvdesc.setText("N/A");
        }

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185" + televPp)
                .into(tvProf);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original" + televBp)
                .into(tvBp);

        if (televTitle != null) {
            isFavorite = databaseHelper.isFavorite(televTitle, "tv_show");
        }

        if (isFavorite) {
            tvFav.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        tvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    databaseHelper.removeFavorite(televTitle, "tv_show");
                    tvFav.setImageResource(R.drawable.baseline_favorite_border_24);
                    isFavorite = false;
                    Toast.makeText(tvDetail.this, televTitle + " removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.addFavorite(televTitle, "tv_show",televPp,televDate,televDesc,televBp,televRate);
                    tvFav.setImageResource(R.drawable.baseline_favorite_24);
                    isFavorite = true;
                    Toast.makeText(tvDetail.this, televTitle + " added to favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvBackbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
