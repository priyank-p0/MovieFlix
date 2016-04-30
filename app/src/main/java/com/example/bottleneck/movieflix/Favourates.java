package com.example.bottleneck.movieflix;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottleneck.movieflix.models.FavourateModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Favourates extends AppCompatActivity {


static int favposition;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState)throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_favourates);
        gridView=(GridView)findViewById(R.id.favourateList);

        Adapter adapter=new Adapter(this,getFavList());

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               favposition=position;
                Intent intent=new Intent(getApplicationContext(),FavourateDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
      });

        }


    private ArrayList<FavourateModel> getFavList() {
         ArrayList<FavourateModel> favList=new ArrayList<FavourateModel>();

        DatabaseHelper dB=new DatabaseHelper(this);

        Cursor res=dB.getData();
        while(res.moveToNext())
        {
            FavourateModel favourateModel=new FavourateModel();
            favourateModel.setId(res.getString(0));
            favourateModel.setName(res.getString(1));
            favourateModel.setRelease_date(res.getString(2));
            favourateModel.setOverview(res.getString(3));
            favourateModel.setPoster(res.getString(4));
            favList.add(favourateModel);

        }
        return favList;

    }









        }
