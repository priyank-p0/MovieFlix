package com.example.bottleneck.movieflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bottleneck.movieflix.models.FavourateModel;

import java.util.ArrayList;

/**
 * Created by priyankpatel on 4/17/16.
 */

    public class Adapter extends BaseAdapter {
      static   Context c;
        ArrayList<FavourateModel> favourateModels;
        public Adapter(Context c,ArrayList<FavourateModel>favourateModels)

        {
            this.c=c;
            this.favourateModels=favourateModels;
        }
        @Override

        public int getCount() {
            return favourateModels.size();
        }

        @Override
        public Object getItem(int position) {
            return favourateModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent) throws NullPointerException
        {
            TextView tv=null;
            View co=convertView;
            LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
                convertView=inflater.inflate(R.layout.favourates_layout,parent,false);


            if (convertView != null) {
                tv= (TextView) convertView.findViewById(R.id.nameView);
                tv.setText (favourateModels.get(position).getName());

            }


            return convertView;
        }
    }


