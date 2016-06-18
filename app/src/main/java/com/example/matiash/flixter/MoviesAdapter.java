package com.example.matiash.flixter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MoviesAdapter extends ArrayAdapter<Movie> {

    String mode;

    private static class ViewHolder {
        TextView tvTitle;
        ImageView ivPoster;
        TextView tvItemOverview;
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.item_movie,movies);
        this.mode = "lightmode";
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            viewHolder.tvItemOverview = (TextView) convertView.findViewById(R.id.tvItemOverview);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // Lookup view for data population
//            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
//        ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(movie.movieTitle);
        if(mode.equals("darkmode"))
        {
            viewHolder.tvTitle.setTextColor(Color.WHITE);
            viewHolder.tvItemOverview.setTextColor(Color.WHITE);
        }
        else {
            viewHolder.tvTitle.setTextColor(Color.BLACK);
            viewHolder.tvItemOverview.setTextColor(Color.BLACK);
        }

//        Log.d("MoviesAdapter","Position " + position);


        String imageUri = "";
        //Load correct image depending on the orientation
        if(convertView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageUri = "http://image.tmdb.org/t/p/w500"+movie.posterLink;
            Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.imageplaceholder).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivPoster);
        }
        else {
            imageUri = "http://image.tmdb.org/t/p/w500"+movie.backdropLink;
            Picasso.with(getContext()).load(imageUri).placeholder(R.drawable.imageplaceholderlandscape).transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivPoster);
        }



        viewHolder.tvItemOverview.setText(movie.overview);
        // Return the completed view to render on screen
        return convertView;

    }
}
