package com.example.bottleneck.movieflix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bottleneck.movieflix.models.FavourateModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.example.bottleneck.movieflix.R.layout.activity_favourate_detail;
import static com.example.bottleneck.movieflix.R.layout.activity_movie_detail;

public class FavourateDetail extends AppCompatActivity {
DatabaseHelper myDb;
  static  Bitmap bmp;

    Favourates favourates=new Favourates();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_favourate_detail);
        myDb=new DatabaseHelper(this);
        FavourateModel favourateModel=myDb.getfavourateList(favourates.favposition+1);
        String name=favourateModel.getName();
        String date=favourateModel.getRelease_date();
        String overview=favourateModel.getOverview();

        String temposter=favourateModel.getPoster();


        byte [] encodeByte= Base64.decode(temposter,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        String rating=favourateModel.getRating();
        TextView release_date;
        TextView Overview;
        RatingBar rbar;
        ImageView pic;
        TextView title;

        title=(TextView)findViewById(R.id.title);
        pic=(ImageView)findViewById(R.id.icon);
        release_date=(TextView)findViewById(R.id.release_date);
        Overview=(TextView)findViewById(R.id.overview);
        rbar=(RatingBar)findViewById(R.id.ratingBar);
        rbar.setRating(Float.parseFloat(rating)/2);
        title.setText(name);
        release_date.setText(date);
        Overview.setText(overview);
        pic.setImageBitmap(bitmap);


    }
}
