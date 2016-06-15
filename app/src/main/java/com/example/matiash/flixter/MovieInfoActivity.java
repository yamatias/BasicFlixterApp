package com.example.matiash.flixter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
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
//        RelativeLayout rlMovieInfoActivity = (RelativeLayout)findViewById(R.id.rlPoster);
        ImageView ivFilter = (ImageView)findViewById(R.id.ivFilter);

        tvMovieTitle.setText(getIntent().getStringExtra("movie_title"));

        String imageUri = "http://image.tmdb.org/t/p/w500"+getIntent().getStringExtra("movie_poster_link");
        String filterUri = "http://themes.wdfiles.com/local--files/semi-trans/semi-transbgtransparent.png";
        Picasso.with(MovieInfoActivity.this).load(imageUri).into(ivPosterBackground); //This may not work!
        Picasso.with(MovieInfoActivity.this).load(filterUri).into(ivFilter);
//        rlMovieInfoActivity.setBackground();
        tvOverview.setText(getIntent().getStringExtra("movie_overview"));



    }

}
