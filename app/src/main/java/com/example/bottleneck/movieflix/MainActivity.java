package com.example.bottleneck.movieflix;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.bottleneck.movieflix.models.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static ImageButton movieButton;
    static int value;
    static int pos;
  static  public List <MovieModel>movieModelList;
    static int postitonMain;
    private static boolean flag=true;
    DatabaseHelper myDb;
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);

        Settings obj=new Settings();

        String vale=obj.valu;
    if(flag)
    {
        vale="top_rated";
        flag=false;
    }
        boolean net=isOnline();
        if(net)
        {
        new Task().execute("http://api.themoviedb.org/3/discover/movie?sort_by="+ vale+".desc&api_key="+"API_KEY");}

            else
        {
            String message="Check Your Network Connection";
            Toast.makeText(this,message, Toast.LENGTH_LONG).show();

        }

        }



    public class Task extends AsyncTask<String,String,List<MovieModel>>
    {

        GridView movieView=(GridView)findViewById(R.id.movieView);


        @Override
        protected List<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection=null;

            try{
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();

                connection.connect();

                InputStream stream=connection.getInputStream();

                BufferedReader br=new BufferedReader(new InputStreamReader(stream));


                StringBuffer buffer=new StringBuffer();
                String line="";
                while((line=br.readLine())!=null)
                {
                    buffer.append(line);
                }


                String finalJson=buffer.toString();

                JSONObject JSON=new JSONObject(finalJson);
                JSONArray parentarray=JSON.getJSONArray("results");
                List<MovieModel> movieList=new ArrayList<>();
                for(int i=0;i<parentarray.length();i++)
                {
                    JSONObject finalObject=parentarray.getJSONObject(i);
                    MovieModel movieModel=new MovieModel();

                    movieModel.setId(finalObject.getInt("id"));
                    movieModel.setPoster_path(finalObject.getString("poster_path"));
                    movieList.add(movieModel);

                }

                return movieList;
            }
            catch (IOException e)
            {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection!=null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieModel> outcome) {
            super.onPostExecute(outcome);
            MovieAdapter movieAdapter=new MovieAdapter(getApplicationContext(),R.layout.main_layout,outcome);
            movieView.setAdapter(movieAdapter);

        }
    }
    Context co;
    public class MovieAdapter extends ArrayAdapter
    {
        private int res;
        private Context context;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List <MovieModel>objects) {

            super(context, resource, objects);
            co=context;
            movieModelList=objects;
            res=resource;
            inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        }

//gets the list view form the content view and loads the image of the poplular movies
        //used to go to the next activity on clicking on the movie icon

       @Override
        public View getView(  int position, View convertView, ViewGroup parent) throws NullPointerException
        {

            if(convertView==null)
                convertView = inflater.inflate(R.layout.main_layout, parent, false);




            movieButton=(ImageButton)convertView.findViewById(R.id.movieButton);
            movieButton.setTag(Integer.valueOf(position));
            pos=position;
            int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
            int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
            movieButton.setPadding(0, 0, 0, 0);

            String URL="http://image.tmdb.org/t/p/w185/"+movieModelList.get(position).getPoster_path();
            Picasso.with(co).load(URL).resize((width / 2), (height / 2)).into(movieButton);

            return convertView;

        }
    }





    public void showMovieDetail(View view)//starts a new activity
    {
        postitonMain = (Integer)view.getTag();//gets the positon of the button, required to get the details of the movie
        value=movieModelList.get(postitonMain).getId();//gets the ID of the movie
         Intent intent = new Intent(this, movieDetail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reload) {
            Intent in = new Intent(this, Settings.class);
            startActivity(in);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
