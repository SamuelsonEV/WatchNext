package io.github.samuelsonev.watchnext;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import java.util.Locale;


public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        // Declaring Views
        ImageView movieImageView = findViewById(R.id.cover);
        TextView titleView = findViewById(R.id.title);
        TextView releaseView = findViewById(R.id.detailReleaseDate);
        TextView movieOverviewView = findViewById(R.id.overview);
        TextView originalLanguageView = findViewById(R.id.language);

        // Getting intent
        Intent intent = getIntent();

        // Getting Bundle from intent
        Bundle bundle;
        bundle = intent.getBundleExtra("bundle");

        // Setting local vars according to the ones received by the bundle
        String title = bundle.getString("title"); // Getting Strings from bundle
        String release = bundle.getString("release");
        String movieImgURL = bundle.getString("image");
        String movieOverviewText = bundle.getString("overview");
        String originalLanguageText = bundle.getString("original_language");
        String[] genreArray = bundle.getStringArray("genreArray"); // Getting StringArray from bundle

        // Adding Genre Tags !!!
        FlexboxLayout genrePlace;
        genrePlace = findViewById(R.id.genresTagsHolder);
        GenreTags.setTaglistOnLayout(this, genreArray, genrePlace);


        // Setting Views
        titleView.setText(title);
        releaseView.setText(release);
        movieOverviewView.setText(movieOverviewText);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movieImgURL).into(movieImageView);

        // Transform the two letters representing the Local (ISO 639-1 ) on a Complete written name !!!
        Locale loc = new Locale(originalLanguageText);
        originalLanguageView.setText(loc.getDisplayLanguage(Locale.ENGLISH)); // Locale.ENGLISH is the language to translate the Language name
    }
}