package com.hitarthi.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hitarthi shah on 25-05-2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Movie> movies;
    private MovieTouchFeedback feedback;

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies, MainActivity activity){
        this.context = context;
        this.movies = movies;
        this.feedback = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.single_movie_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Movie movie = movies.get(position);

        Picasso.with(this.context).load(movie.backdrop_path).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        holder.textView.setText(movie.original_title);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback!=null)
                    feedback.onItemTouch(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (movies != null && movies.size() != 0)? movies.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_single_movie_view);
            imageView = (ImageView) itemView.findViewById(R.id.iv_single_movie_view);
            textView = (TextView) itemView.findViewById(R.id.tv_single_movie_view);

        }
    }

    public interface MovieTouchFeedback{
        void onItemTouch(int position);
    }

}
