package com.example.matiash.flixter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MoviesAdapter adapter;
    boolean darkmode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug","onCreate has been called");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);


        //Get Actual Movies
        movies = new ArrayList<>();

        //Constantly and asynchronously checking for newest movies
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray results;
                try {
                    results = response.getJSONArray("results");

                } catch (JSONException e) {
                    e.printStackTrace();
                    results = new JSONArray();
                }

                //Going through each result, making a new JSONObject out of each and extracting info
                for(int x= 0;x<results.length();x++) {
                    JSONObject current_movie_info;
                    try {

                        current_movie_info = (JSONObject) results.get(x);
                        String title = current_movie_info.getString("title");
                        String overview = current_movie_info.getString("overview");
                        String posterUrl = current_movie_info.getString("poster_path");
                        double rating = current_movie_info.getDouble("vote_average");
                        int popularity = current_movie_info.getInt("popularity");
                        String backdropUrl = current_movie_info.getString("backdrop_path");
                        movies.add(new Movie(title,rating,overview,popularity,posterUrl,backdropUrl));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("debug","did not get movie info. No movies added");

                    }

                }

                final ListView lvMovies = (ListView)findViewById(R.id.lvMovies);
                adapter = new MoviesAdapter(MoviesActivity.this,movies);
                if(lvMovies != null) {
                    lvMovies.setAdapter(adapter);
                }

                lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MoviesActivity.this,MovieInfoActivity.class);
                        intent.putExtra("movie_title",movies.get(i).movieTitle);
                        intent.putExtra("movie_overview",movies.get(i).overview);
                        intent.putExtra("movie_poster_link",movies.get(i).posterLink);
                        intent.putExtra("movie_rating",movies.get(i).rating);
                        intent.putExtra("movie_popularity",movies.get(i).popularity);
                        intent.putExtra("movie_backdrop_link",movies.get(i).backdropLink);
                        startActivity(intent);
                    }
                });

                lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        toggleDarkMode(lvMovies,adapter);
                        return true;
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("debug","failure - did not get internet connection to pull up movies");
            }
        });
    }

    public void toggleDarkMode(ListView lv, MoviesAdapter adapter) {
        darkmode = !darkmode;

        if(darkmode) {
            lv.setBackgroundResource(R.color.dark);
            adapter.setMode("darkmode");
            Toast.makeText(MoviesActivity.this,"Darkmode Enabled",Toast.LENGTH_SHORT).show();
        }
        else {
            lv.setBackgroundResource(R.color.white);
            adapter.setMode("l");
            Toast.makeText(MoviesActivity.this,"Darkmode Disabled",Toast.LENGTH_SHORT).show();

        }
    }


}
