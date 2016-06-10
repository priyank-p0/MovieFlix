package com.example.bottleneck.movieflix;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bottleneck.movieflix.models.MovieModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by priyankpatel on 6/5/16.
 */

public class fragmentname extends Fragment

{

    private static final String STATE_MOVIES ="state_movies" ;

    static ImageButton movieButton;
      public ArrayList <MovieModel>movieModelList;
    ImageButton button;
    GridView movieView;
    Communicator communicator;
    Context co;
    static boolean flag=false;
 static  MovieAdapter movieAdapter;
    static String value;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.fragment_name, container, false);

            movieView = (GridView) view.findViewById(R.id.movieView);
            button = (ImageButton) getActivity().findViewById(R.id.movieButton);

            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState != null)
            {//retriving the movielist when the screen is rotated from previous state
                movieModelList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);


                flag = true;
                movieAdapter = new MovieAdapter(getActivity(), R.layout.fragment_name, movieModelList);//adding the list into the adapter
                movieView.setAdapter(movieAdapter);

            }
            return view;



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

            MainActivity obj = new MainActivity();
            value = obj.vale;
            communicator = (Communicator) getActivity();
            movieAdapter = new MovieAdapter(getActivity());
            movieView.setAdapter(movieAdapter);
            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState != null) {


                movieModelList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);//obtaining moviemodellist from the savedinstance state
                movieAdapter = new MovieAdapter(getActivity(), R.layout.fragment_name, movieModelList);
                movieView.setAdapter(movieAdapter);


            }

        if(isOnline()) {
            new Task().execute("http://api.themoviedb.org/3/movie/" + value + "?api_key=" + "API_KEY");
        }
                else
        {
            String message = "Check Your Network Connection";
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outstate)//saving the moviemodellist in before the activity is destroyed
    {
        super.onSaveInstanceState(outstate);
        outstate.putParcelableArrayList(STATE_MOVIES, (ArrayList<? extends Parcelable>) movieModelList);
    }








    public class Task extends AsyncTask<String,String,ArrayList<MovieModel>>  {



        @Override
        protected ArrayList<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection=null;

                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(stream));


                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }


                    String finalJson = buffer.toString();

                    JSONObject JSON = new JSONObject(finalJson);
                    JSONArray parentarray = JSON.getJSONArray("results");
                    ArrayList<MovieModel> movieList = new ArrayList<>();

                    for (int i = 0; i < parentarray.length(); i++) {
                        JSONObject finalObject = parentarray.getJSONObject(i);
                        MovieModel movieModel = new MovieModel();
                        movieModel.setId(finalObject.getInt("id"));
                        movieModel.setPoster_path(finalObject.getString("poster_path"));
                        movieList.add(movieModel);

                    }

                    return movieList;
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> outcome) {
            super.onPostExecute(outcome);
            movieAdapter=new MovieAdapter(getActivity(),R.layout.fragment_name,outcome);
            movieView.setAdapter(movieAdapter);


        }


    }

    public class MovieAdapter extends ArrayAdapter implements View.OnClickListener {

        @Override
        public boolean isEnabled(int i) {
            return true;
        }

        private int res;
        private Context context;
        private  LayoutInflater mInflater;

        private LayoutInflater inflater;
        public MovieAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public MovieAdapter(Context context, int resource, ArrayList <MovieModel>objects) {

            super(context, resource, objects);
            co=context;
            movieModelList=objects;
            res=resource;
            inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

//gets the list view form the content view and loads the image of the poplular movies
        //used to go to the next activity on clicking on the movie icon



        @Override
        public View getView(  int position, View convertView, ViewGroup parent) throws NullPointerException
        {

            if(convertView==null)
                convertView = inflater.inflate(R.layout.content_fragmentname, parent, false);




            movieButton=(ImageButton)convertView.findViewById(R.id.movieButton);
            movieButton.setTag(Integer.valueOf(position));


            int width = getActivity().getResources().getDisplayMetrics().widthPixels;
            int height = getActivity().getResources().getDisplayMetrics().heightPixels;
            movieButton.setPadding(0, 0, 0, 0);

            String URL="http://image.tmdb.org/t/p/w185/"+movieModelList.get(position).getPoster_path();
            Picasso.with(co).load(URL).resize((width / 2), (height / 2)).into(movieButton);
            movieButton.setOnClickListener(this);
            return convertView;

        }


        @Override
        public void onClick(View v) {

            int identity= movieModelList.get((Integer) v.getTag()).getId();

            communicator.respond(identity);
        }
    }









   @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reload) {
            Intent in = new Intent(getActivity(), Settings.class);
            startActivity(in);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
