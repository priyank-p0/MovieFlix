package com.example.bottleneck.movieflix;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.widget.Toast;

import com.example.bottleneck.movieflix.models.MovieModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Communicator {
    private static final String STATE_MOVIES ="state_movies" ;
    static int pos;
  static  public ArrayList <MovieModel>movieModelList;
    private static boolean flag=true;
    DatabaseHelper myDb;
   static String vale;
    FragmentManager manager;
    fragmentname f1;
    movieDetail f2;
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

      vale  =obj.valu;
        if(savedInstanceState!=null)
        {
          movieModelList=savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }
        else {
            if (flag) {
                vale = "popular";
                flag = false;
            }
            boolean net = isOnline();
            if (net) {

                    manager=getFragmentManager();
                    f1= (fragmentname) customFragment();
                     //f1.setCommunicator(this);



            } else {
                String message = "Check Your Network Connection";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            }
        }


        }

    private Fragment customFragment(){
        Bundle bundle=new Bundle();
        bundle.putString(vale,"value");
        fragmentname name=new fragmentname();
        name.setArguments(bundle);
        return name;
    }

@Override
public void onSaveInstanceState(Bundle outstate)
{
    super.onSaveInstanceState(outstate);
    outstate.putParcelableArrayList(STATE_MOVIES, (ArrayList<? extends Parcelable>) movieModelList);
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


    @Override
    public void respond(int id) {
    f2=(movieDetail)manager.findFragmentById(R.id.fragment2);
        if(f2!=null&& f2.isVisible())
        {
            f2.execute(id);
        }
        else
        {
            Intent in = new Intent(this, Details.class);
            in.putExtra("value",id);
            startActivity(in);
        }
    }

}
