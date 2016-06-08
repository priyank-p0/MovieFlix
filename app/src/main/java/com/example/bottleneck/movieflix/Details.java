package com.example.bottleneck.movieflix;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by priyankpatel on 6/7/16.
 */
public class Details extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_details
        );

        Intent in=getIntent();
     int   value=in.getIntExtra("value",0);
        FragmentManager manager=getFragmentManager();
       movieDetail f2= (movieDetail) manager.findFragmentById(R.id.fragment2);
       // if(f2!=null)
            f2.execute(value);


    }
}
