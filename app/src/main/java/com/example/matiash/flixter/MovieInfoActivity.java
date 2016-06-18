package com.example.matiash.flixter;

import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        TextView tvMovieTitle = (TextView)findViewById(R.id.tvMovieTitle);
        ImageView ivPosterBackground = (ImageView)findViewById(R.id.ivPosterBackground);
        TextView tvOverview = (TextView)findViewById(R.id.tvOverview);
        ImageView ivFilter = (ImageView)findViewById(R.id.ivFilter);
        RatingBar rbRating = (RatingBar)findViewById(R.id.rbRating);
//        ProgressBar pbPopularity = (ProgressBar)findViewById(R.id.pbPopularity);
        TextView tvRating = (TextView)findViewById(R.id.tvRating);

        String imageUri = "";
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageUri = "http://image.tmdb.org/t/p/w500"+getIntent().getStringExtra("movie_poster_link");

        }
        else {
            imageUri = "http://image.tmdb.org/t/p/w500"+getIntent().getStringExtra("movie_backdrop_link");
        }

        String filterUri = "http://themes.wdfiles.com/local--files/semi-trans/semi-transbgtransparent.png";
        Picasso.with(MovieInfoActivity.this).load(imageUri).into(ivPosterBackground); //This may not work!
        Picasso.with(MovieInfoActivity.this).load(filterUri).into(ivFilter);
        float rating = (float)getIntent().getDoubleExtra("movie_rating",0.0f)/2;
        Log.d("debug",rating+"");
        rbRating.setRating((float)getIntent().getDoubleExtra("movie_rating",0.0f)/2);
        //pbPopularity.setProgress(getIntent().getIntExtra("movie_popularity",0));


        rbRating.getProgressDrawable().setColorFilter(new LightingColorFilter(0xFFFF00,0x333333));
        tvRating.setText(getIntent().getIntExtra("movie_popularity",0)+"");

        //Lightens up the Filter (it was a bit dark)
        ivFilter.setColorFilter(new LightingColorFilter(0x000000,0x333333));

        tvMovieTitle.setText(getIntent().getStringExtra("movie_title"));
        tvOverview.setText(getIntent().getStringExtra("movie_overview"));
        tvOverview.setMovementMethod(new ScrollingMovementMethod());


    }

}
