package com.example.bottleneck.movieflix;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {
 static String valu;
    MainActivity mainActivity=new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btn=(Button)findViewById(R.id.button2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }
    public void setTop(View v)
    {
        valu="top_rated";

        Intent i = new Intent(Settings.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);


    }


    public void setPopular(View v)
    {
        valu="popularity";


        Intent i = new Intent(Settings.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }




}
