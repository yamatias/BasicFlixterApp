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
                        movies.add(new Movie(title,rating,overview,popularity,posterUrl));
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
                        startActivity(intent);
                    }
                });

                lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //TODO make the dark mode work
                        darkmode = !darkmode;

                        if(darkmode) {
                            lvMovies.setBackgroundResource(R.color.dark);
//                            TextView title = (TextView)adapter.getView(i,view,adapterView).findViewById(R.id.tvTitle);
//                            title.setBackgroundColor(0xFFFF00);
//                            adapter.notifyDataSetChanged();
//                            lvMovies.setAdapter(adapter);
                            adapter.setMode("darkmode");
                            Toast.makeText(MoviesActivity.this,"Darkmode Enabled",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            lvMovies.setBackgroundResource(R.color.white);
//                            TextView title2 = (TextView)adapter.getView(i,view,adapterView).findViewById(R.id.tvTitle);
//                            title2.setBackgroundColor(0xFFFFFF);
//                            adapter.notifyDataSetChanged();
//                            lvMovies.setAdapter(adapter);
                            adapter.setMode("l");
                            Toast.makeText(MoviesActivity.this,"Darkmode Disabled",Toast.LENGTH_SHORT).show();

                        }


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


}
