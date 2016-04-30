package com.example.bottleneck.movieflix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bottleneck.movieflix.models.FavourateModel;

/**
 * Created by priyankpatel on 4/17/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Favourate.db";
    public static final String TABLE_NAME="favourateTable";
    public static final String COL_1="ID";
    public static final String COL_2="NAME";
    public static final String COL_3="RELEASE_DATE";
    public static final String COL_4="OVERVIEW";
    public static final String COL_5="POSTER";
    public static final String COL_6="RATING";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,RELEASE_DATE TEXT,OVERVIEW TEXT,POSTER TEXT,RATING TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String name, String release_date, String overview,String poster,String rating)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,release_date);
        contentValues.put(COL_4,overview);
        contentValues.put(COL_5,poster);
        contentValues.put(COL_6,rating);

        long result=db.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    FavourateModel getfavourateList(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{COL_1,COL_2,COL_3,COL_4,COL_5,COL_6},COL_1+"=?",new String[]{ String.valueOf(id) }, null, null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        FavourateModel favourateModel=new FavourateModel();
        favourateModel.setId(cursor.getString(0));
        favourateModel.setName(cursor.getString(1));
        favourateModel.setRelease_date(cursor.getString(2));
        favourateModel.setOverview(cursor.getString(3));
        favourateModel.setPoster(cursor.getString(4));
        favourateModel.setRating(cursor.getString(5));
        return  favourateModel;
    }
}
