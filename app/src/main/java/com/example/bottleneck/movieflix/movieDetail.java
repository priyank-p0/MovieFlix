package com.example.bottleneck.movieflix;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottleneck.movieflix.models.FavourateModel;
import com.example.bottleneck.movieflix.models.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class movieDetail extends AppCompatActivity {
ListView Trailerview;
    CheckBox favBox ;
   static ArrayList<String>arrayList=new ArrayList<>();
     String array[];
     Cursor cursor;
     MainActivity obj=new MainActivity();
    String Title="";
    String date="";
    String overview="";
    DatabaseHelper myDb;
    ArrayList videol=new ArrayList<String>();
    double popu=0.0;
    String poster="";
   static String byteArray;
    static String rString;
    String arrReview[]=new String[100];
    int position;
     List<MovieModel>movieModelList;
    String va;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
         favBox = (CheckBox) findViewById(R.id.favouratecheckBox);

        setContentView(R.layout.activity_movie_detail);
         position=obj.pos;
        movieModelList=obj.movieModelList;

        myDb = new DatabaseHelper(this);

        cursor=myDb.getData();

        int val=  obj.value;
        va = Integer.toString(val);
         array=new String[cursor.getCount()];
        new JSONTask().execute("http://api.themoviedb.org/3/movie/" + va + "?api_key="+"API_KEY","http://api.themoviedb.org/3/movie/"+va+"/videos?api_key=API_KEY","http://api.themoviedb.org/3/movie/"+va+"/reviews?api_key=API_KEY");



    }



        public void onCheckboxClicked (View v){




            boolean isInserted = myDb.insertData(Title, date, overview, byteArray, rString);
            if (isInserted) {
                Toast.makeText(movieDetail.this, "Added to favourate", Toast.LENGTH_LONG).show();

        }

    }






    public class JSONTask extends AsyncTask<String, Void,Void> {

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            HttpURLConnection connection1=null;
            HttpURLConnection connection2=null;
            BufferedReader br = null;
            BufferedReader b=null;
            BufferedReader bufferedReader=null;
            try {
                URL url = new URL(params[0]);
                URL ur=new URL(params[1]);
                URL u=new URL(params[2]);
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
                rString=Double.toString(popu);
                poster=JSON.getString("poster_path");
                try {


                    Bitmap immagex=Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+poster).get();;



                    ByteArrayOutputStream baoes=new  ByteArrayOutputStream();
                    immagex.compress(Bitmap.CompressFormat.PNG,100, baoes);
                    byte [] bes=baoes.toByteArray();
                     byteArray=Base64.encodeToString(bes, Base64.DEFAULT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                connection.disconnect();


                connection1=(HttpURLConnection) ur.openConnection();
                InputStream i=connection1.getInputStream();
                b=new BufferedReader(new InputStreamReader(i));
                String info;
                StringBuffer buffer=new StringBuffer();
                while((info=b.readLine())!=null){
                    buffer.append(info);}
                String videoJson =buffer.toString();
                JSONObject JObject=new JSONObject(videoJson);
                JSONArray parentarray=JObject.getJSONArray("results");
                for(int j=0;j<parentarray.length();j++)
                {
                    JSONObject finalObject=parentarray.getJSONObject(j);
                   // arrayId[j]=finalObject.getString("id");
                    videol.add("https://www.youtube.com/watch?v="+finalObject.getString("key"));
                }
                connection1.disconnect();



                connection2=(HttpURLConnection)u.openConnection();
                InputStream inputStream=connection2.getInputStream();
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String review;
                StringBuffer stringBuffer=new StringBuffer();
                while((review=bufferedReader.readLine())!=null){
                    stringBuffer.append(review);
                }
                String reviewJson=stringBuffer.toString();
                JSONObject object=new JSONObject(reviewJson);
                JSONArray arr= object.getJSONArray("results");

                for(int k=0;k<arr.length();k++)
                {
                    JSONObject finalreview=arr.getJSONObject(k);
                    arrReview[k]=finalreview.getString("content");

                }

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
            TextView review;
            title=(TextView)findViewById(R.id.title);
            pic=(ImageView)findViewById(R.id.icon);
            release_date=(TextView)findViewById(R.id.release_date);
            Overview=(TextView)findViewById(R.id.overview);
            Trailerview=(ListView)findViewById(R.id.videolist);
            rbar=(RatingBar)findViewById(R.id.ratingBar);

            title.setText(Title);
            rbar.setRating((float) (popu / 2));
            release_date.setText("Release Date:" + date);
            String sy="SYNOPSIS";
            String ry="REVIEWS";
            int ctr=1;
            Overview.setText("SYNOPSIS:"+"\n"+overview+"\n\n\n\n"+"REVIEWS:"+"\n\n");
            String URL="http://image.tmdb.org/t/p/w185/"+poster;
            Picasso.with(getApplicationContext()).load(URL).resize(450, 700).into(pic);
            StringBuilder builder = new StringBuilder();
            for (int i=0;i<arrReview.length;i++) {
                String s = arrReview[i];

                if (s != null) {
                    builder.append(s + "\n ");
                    Overview.append(builder.toString());
                }
                int in=0;

                if (cursor.moveToFirst()) {
                    do {
                        CheckBox fav=(CheckBox)findViewById(R.id.favouratecheckBox);

                        if(cursor.getString(1).equalsIgnoreCase(Title))
                        {
                            fav.setChecked(true);
                            fav.setClickable(false);
                            break;
                        }
                       // arrayList.add(cursor.getString(1));
                       // String t=Title;
                       // if(arrayList.get(in).toString().equalsIgnoreCase(Title))
                         // in++;

                    } while (cursor.moveToNext()&&in<cursor.getCount());
                }
                v();

            }
        }


        protected void onPostExecute(Void m) {

            super.onPostExecute(m);



            exec();


        }




    }
    public void v()
    {
         ArrayAdapter<String>  arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, videol);
        Trailerview.setAdapter(arrayAdapter);
        Trailerview.setOnItemClickListener(ListClickHandler);


    }


private AdapterView.OnItemClickListener ListClickHandler=new AdapterView.OnItemClickListener(){

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String u= (String) videol.get(position);
        Uri uri = Uri.parse(u); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    };


}





