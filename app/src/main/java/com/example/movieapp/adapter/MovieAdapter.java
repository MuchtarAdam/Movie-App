package com.example.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.activity.MovieDetail;
import com.example.movieapp.movieModel.MovieResults;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MvViewHolder> {

    private Context context;
    private List<MovieResults> movieResultList;


    public MovieAdapter(Context context, List<MovieResults> movieResultList) {
        this.context = context;
        this.movieResultList = movieResultList;
    }
    public void addMovies(List<MovieResults> newMovies) {
        int startPos = movieResultList.size();
        movieResultList.addAll(newMovies);
        notifyItemRangeInserted(startPos, newMovies.size());
    }



    @NonNull
    @Override
    public MovieAdapter.MvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_movie, parent, false );

        MovieAdapter.MvViewHolder viewHolder = new MovieAdapter.MvViewHolder(view);
        viewHolder.MovieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), MovieDetail.class);
                MovieResults movieResult = new MovieResults();
                movieResult.setTitle(movieResultList.get(viewHolder.getAdapterPosition()).getTitle());
                movieResult.setOverview(movieResultList.get(viewHolder.getAdapterPosition()).getOverview());
                movieResult.setBackdropPath(movieResultList.get(viewHolder.getAdapterPosition()).getBackdropPath());
                movieResult.setVoteAverage(movieResultList.get(viewHolder.getAdapterPosition()).getVoteAverage());
                movieResult.setReleaseDate(movieResultList.get(viewHolder.getAdapterPosition()).getReleaseDate());
                movieResult.setPosterPath(movieResultList.get(viewHolder.getAdapterPosition()).getPosterPath());
                intent.putExtra(MovieDetail.EXTRA_MOVIE, movieResult);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MvViewHolder holder, int position) {
        holder.mvTitle.setText(movieResultList.get(position).getTitle());
        String releaseDate = movieResultList.get(position).getReleaseDate();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.mvYear.setText(year);
        } else {
            holder.mvYear.setText("");
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + movieResultList.get(position).getPosterPath())
                .into(holder.mvPoster)
        ;
    }

    @Override
    public int getItemCount() {
        return movieResultList.size();
    }

    public class MvViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout MovieLayout;
        ImageView mvPoster;
        TextView mvTitle, mvYear;
        public MvViewHolder(@NonNull View itemView) {
            super(itemView);

            mvPoster = itemView.findViewById(R.id.movieImg);
            mvTitle = itemView.findViewById(R.id.movie_title);
            mvYear = itemView.findViewById(R.id.movie_year);
            MovieLayout = itemView.findViewById(R.id.movieLayout);
        }
    }
}
