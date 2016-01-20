package com.example.bottleneck.movieflix;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bottleneck.movieflix.models.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class movieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MainActivity obj=new MainActivity();
        String pos;


        int val=  obj.value;
        String v= Integer.toString(val);
        new JSONTask().execute("http://api.themoviedb.org/3/movie/" + v + "?api_key="+"API_KEY");


    }

    String Title="";
    String date="";
    String overview="";
    double popu=0.0;
    String poster="";

    public class JSONTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream ir = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(ir));
                String information;
                StringBuffer bf = new StringBuffer();
                while ((information = br.readLine()) != null) {
                    bf.append(information);
                }
                String movieJson = bf.toString();
                JSONObject JSON = new JSONObject(movieJson);


                Title = JSON.getString("title");
                date = JSON.getString("release_date");
                overview = JSON.getString("overview");
                popu = JSON.getDouble("vote_average");
                poster=JSON.getString("poster_path");

                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public void exec()
        {
            TextView orignal_title;
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

            title.setText(Title);
            rbar.setRating((float) (popu / 2));
            release_date.setText("Release Date:" + date);
            Overview.setText("Synopsis:"+"\n"+overview);
            String URL="http://image.tmdb.org/t/p/w185/"+poster;
            Picasso.with(getApplicationContext()).load(URL).resize(450, 700).into(pic);
        }


        protected void onPostExecute(Void m) {
            super.onPostExecute(m);
            exec();


        }




    }



}
