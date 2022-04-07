package io.github.samuelsonev.watchnext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieModel> movieModelArrayList;
    private LayoutInflater inflater;

    // CONSTRUCTOR
    public MovieRecyclerViewAdapter(Context context, ArrayList<MovieModel> movieModelArrayList) {
        this.context = context;
        this.movieModelArrayList = movieModelArrayList;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        MovieModel movies = movieModelArrayList.get(position);
        holder.movieTitle.setText(movies.getMovieOriginalTitle());
        holder.movieReleaseDate.setText(movies.getMovieReleaseDate());
        Picasso.get().load("https://image.tmdb.org/t/p/w200" + movies.getMovieImageUrl()).into(holder.movieImage);
        // Making card click response
        holder.entireCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Details.class);
                Bundle bundle = new Bundle();
                bundle.putString("image", movies.getMovieImageUrl());
                bundle.putString("title", movies.getMovieOriginalTitle());
                bundle.putString("release", movies.getMovieReleaseDate());
                bundle.putString("overview", movies.getMovieOverview());
                bundle.putString("original_language", movies.getOriginalLanguage());
                bundle.putStringArray("genreArray", movies.getGenreArray());
                intent.putExtra("bundle", bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // Flag to avoid main activity being killed while opening new tab
                context.startActivity(intent);
            }
        });
        // Adding genre Tags
        String[] genreArray = movies.getGenreArray();
        GenreTags.setTaglistOnLayout(context, genreArray, holder.genrePlace);
    }

    @Override
    public int getItemCount() {
        return movieModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private final TextView movieTitle;
        private final TextView movieReleaseDate;
        private final ImageView movieImage;
        private final CardView entireCard;
        private final  FlexboxLayout genrePlace;
        // Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = itemView.findViewById(R.id.movieReleaseDate);
            entireCard = itemView.findViewById(R.id.movieCardItem);
            genrePlace = itemView.findViewById(R.id.cardGenresHolder);
        }
    }
}