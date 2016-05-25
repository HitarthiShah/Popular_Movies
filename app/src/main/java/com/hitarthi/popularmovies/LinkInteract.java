package com.hitarthi.popularmovies;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hitarthi shah on 28-04-2016.
 */
public class LinkInteract {


    private static final String TAG = LinkInteract.class.getSimpleName();


    public static final String TOP_RATED = "top_rated";
    public static final String POPULAR = "popular";
    private static final String API_KEY = "api_key";
    public Context context;

    MovieCallback movieCallback;

    private ArrayList<Movie> movies = null;


    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";

    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String BACKDROP_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/";


    public LinkInteract(Context context, MainActivity mainActivity) {
        this.context = context;
        this.movieCallback = mainActivity;
        this.movies =  new ArrayList<>();
    }


    public void fetchMovies(String genre){

        movies.clear();

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(genre)
                .appendQueryParameter(API_KEY, this.context.getResources().getString(R.string.api_key))
                .build();

        try{
            URL url =  new URL(uri.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url.toString(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("results");
                                for(int i = 0; i< jsonArray.length(); i++){
                                    JSONObject singleMovieData = jsonArray.getJSONObject(i);
                                    String poster_path = POSTER_BASE_URL + singleMovieData.getString("poster_path");
                                    String overview = singleMovieData.getString("overview");
                                    String release_date = singleMovieData.getString("release_date");
                                    int id = singleMovieData.getInt("id");
                                    String original_title = singleMovieData.getString("original_title");
                                    String backdrop_path = BACKDROP_POSTER_BASE_URL + singleMovieData.getString("backdrop_path");
                                    float vote_average = (float) singleMovieData.getDouble("vote_average");
                                    movies.add(new Movie(poster_path, overview, release_date, original_title, backdrop_path, id, vote_average));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(movieCallback != null){
                                movieCallback.onSuccess(movies);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (movieCallback != null){
                                movieCallback.onFailure();
                            }
                        }
                    }
            );

            AppController.getInstance().addToRequestQueue(jsonObjectRequest, TAG);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    public interface MovieCallback{
        void onSuccess(ArrayList<Movie> movies);
        void onFailure();
    }

}