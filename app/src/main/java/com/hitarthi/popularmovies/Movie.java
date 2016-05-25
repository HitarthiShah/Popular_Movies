package com.hitarthi.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hitarthi shah on 25-05-2016.
 */
public class Movie implements Parcelable{
    String poster_path, overview, release_date, original_title, backdrop_path;
    int id;
    float vote_average;

    public Movie(String poster_path, String overview, String release_date, String original_title, String backdrop_path, int id, float vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.vote_average = vote_average;
    }

    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeInt(id);
        dest.writeFloat(vote_average);
    }
}
