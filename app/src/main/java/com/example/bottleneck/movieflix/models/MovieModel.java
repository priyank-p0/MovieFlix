package com.example.bottleneck.movieflix.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by priyankpatel on 1/12/16.
 */
public class MovieModel implements Parcelable{




    private String poster_path;

    public int id;
    private int favPosition=0;


    public MovieModel() {
    }

    public MovieModel(Parcel input)
    {
        poster_path=input.readString();
        id=input.readInt();
        favPosition=input.readInt();
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }



    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFavPosition() {
        return favPosition;
    }

    public void setFavPosition(int favPosition) {
        this.favPosition = favPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(poster_path);
            dest.writeInt(id);
        dest.writeInt(favPosition);
    }

    public static final Parcelable.Creator<MovieModel> CREATOR
            = new Parcelable.Creator<MovieModel>() {
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

}
