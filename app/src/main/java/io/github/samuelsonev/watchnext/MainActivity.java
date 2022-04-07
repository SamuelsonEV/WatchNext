package io.github.samuelsonev.watchnext;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import java.util.ArrayList;
import java.util.Hashtable;


public class MainActivity extends AppCompatActivity implements APIResponse {


    protected String API_KEY = "<<<<INSERT API KEY HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!>>>>";  // <<<<<<---------

    // Setting URL`s
    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=";
    String genreIDsUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en-US";
    Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();

    // creating variables for our UI components.
    static int page = 1;
    static int totalPages = 2;
    static RequestQueue queue;
    private ArrayList<MovieModel> moviesArrayList;
    public static RecyclerView movieRV;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    private Boolean acquiredGenresDict = false;
    private APIGetter apiGetter;

    @Override
    public void onDestroy() {
        super.onDestroy();
        page = 1; // Go back to first page when application is destroyed
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing our variables.
        movieRV = findViewById(R.id.idRVMovies);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);
        // initializing our array list.
        moviesArrayList = new ArrayList<>();

        // V  Setting layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        movieRV.setLayoutManager(manager);
        loadingPB.setVisibility(View.VISIBLE); // Making our progress bar visible.
        apiGetter = new APIGetter(MainActivity.this, API_KEY);
        apiGetter.getNowPlayingMovies(page);
        page++; // increase page count

        // Scroll change listener to know when reached the end of page
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are check when users scroll to the bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        apiGetter.getNowPlayingMovies(page);
                        Log.d("DEBUG", "AAAAAAAAA<><>" + page);
                        page++; // increase page count


                }
            }
        });
    }

    // Is called when APIGetter receives get a response from the API
    public void updateMoviesList(ArrayList<MovieModel> moviesArrayList, Context context) {
        //  Adding our array list to our adapter class.
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(context, moviesArrayList);
//        //   Setting adapter to our recycler view.
        movieRV.setAdapter(movieRecyclerViewAdapter);

    }
}
