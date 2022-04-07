package io.github.samuelsonev.watchnext;

import android.content.Context;
import java.util.ArrayList;

interface APIResponse {
    void updateMoviesList(ArrayList<MovieModel> movieArray, Context context);
}