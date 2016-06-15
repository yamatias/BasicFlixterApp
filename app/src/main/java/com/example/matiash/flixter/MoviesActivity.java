package com.example.matiash.flixter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                        movies.add(new Movie(title,overview,posterUrl));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("debug","did not get movie info. No movies added");

                    }

                }

//                Log.d("debug",movies.toString());
                ListView lvMovies = (ListView)findViewById(R.id.lvMovies);
                MoviesAdapter adapter = new MoviesAdapter(MoviesActivity.this,movies);
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
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("debug","failure");
            }
        });
    }


}
