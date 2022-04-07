package io.github.samuelsonev.watchnext;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;



public class APIGetter {
    private Context context;
    protected String API_KEY;
    private String url;
    private String genreIDsUrl;
    private ArrayList<MovieModel> moviesArrayList = new ArrayList<>();
    private Boolean acquiredGenresDict = false;
    static RequestQueue queue;
    public  Hashtable<Integer, String> my_dict = new Hashtable<Integer, String>();
    static int totalPages;  // Implement something so it dont try to access more pages than exist

    public APIGetter(Context context, String API_KEY ){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.API_KEY = API_KEY;
        this.url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=";
        this.genreIDsUrl = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + API_KEY + "&language=en-US";
    }

    
    private void acquireGenreIDDict() {
        // Request to Genre ids and names API
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, genreIDsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSONObject) {
                // Get the JSON array from the JSON object received
                JSONArray results = null;
                try {
                    results = responseJSONObject.getJSONArray("genres");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Iterate to create a dictionary with genreID numbers as keys and genreName as keypair
                for (int i = 0; i < results.length(); i++) {
                    try {
                        JSONObject responseObj = results.getJSONObject(i);
                        int genreID = responseObj.getInt("id");
                        String genreName = responseObj.getString("name");
                        my_dict.put(genreID, genreName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("DEBUG", " my_dict.toString() " +  my_dict.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Failed to get the data...  :(", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        acquiredGenresDict = true;
    }

    public void getNowPlayingMovies(Integer page) {
        // Check if already has a dictionary to translate the genre numbers to genre Names
        if (!acquiredGenresDict) {
            acquireGenreIDDict();
        }
        // Request the nowplaying page from the API
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + page, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSONObject) {
                JSONArray results = null;
                try {
                    results = responseJSONObject.getJSONArray("results");
                    totalPages = responseJSONObject.getInt("total_pages");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < results.length(); i++) {
                    try {
                        JSONObject responseObj = results.getJSONObject(i);
                        //  Getting Data from the API response
                        String movieOriginalTitle = responseObj.getString("original_title");
                        String movieImageUrl = responseObj.getString("poster_path");
                        String movieOverview = responseObj.getString("overview");
                        String originalLanguage = responseObj.getString("original_language");
                        // Date must be transformed to "dd/MM/yyyy" format
                        String sDate1 = responseObj.getString("release_date");
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                        String pattern = "dd/MM/yyyy";
                        DateFormat df = new SimpleDateFormat(pattern);
                        String movieReleaseDate = df.format(date1);
                        // Use dictionary to translate genres Numbers to genre Names
                        JSONArray genreIds = responseObj.getJSONArray("genre_ids");
                        String[] genreArray = {};
                        for (int g = 0; g < genreIds.length(); g++) {
                            genreArray = ArrayUtils.appendToArray(genreArray, my_dict.get(genreIds.getInt(g)));
                        }
                        moviesArrayList.add(new MovieModel(movieOriginalTitle, originalLanguage,
                                movieReleaseDate, movieImageUrl, movieOverview, genreArray));
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                };
//                Callback to main when results are ready
                APIResponse callBack = new MainActivity();
                callBack.updateMoviesList(moviesArrayList, context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Failed to get the data...  :(", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
