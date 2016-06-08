package com.example.bottleneck.movieflix;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.bottleneck.movieflix.models.FavourateModel;
import com.example.bottleneck.movieflix.models.MovieModel;
import java.util.List;
import static com.example.bottleneck.movieflix.R.layout.activity_favourate_detail;


public class FavourateDetail extends AppCompatActivity {
DatabaseHelper myDb;
    MainActivity obj=new MainActivity();
    int position;
    List<MovieModel> movieModelList;
    CheckBox favBox;

     Favourates favourates= new Favourates();
     FavourateModel favourateModel;
     int pos;
     Cursor r;
      @Override
     protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_favourate_detail);
        myDb=new DatabaseHelper(this);
        movieModelList=obj.movieModelList;
        position=obj.pos;
        favBox=(CheckBox)findViewById(R.id.checkBox);
      /*   if(res.getPosition()==-1)
            res.moveToNext();
       if(res.getCount()>0) {


            favourateModel= myDb.getfavourateList(favourates.favposition +1);

            res.close();

        }*/

          int disp=favourates.favposition+1;
    r=myDb.getrow(favourates.favposition+1);
          int po=r.getPosition();
        r.moveToFirst();
     // id =favourateModel.getId();
         String name=r.getString(1);
        String date=r.getString(2);
        String overview=r.getString(3);

        String temposter=r.getString(4);


        byte [] encodeByte= Base64.decode(temposter,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        String rating=r.getString(5);

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
    public void onCheckboxClicked(View v)
    {

        myDb.deleteContact(favourates.favposition+1,myDb);
       // mv.arrayList.remove(position);

       // myDb.update();
        Intent intent = new Intent(this, Favourates.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


        startActivity(intent);

    }
}
