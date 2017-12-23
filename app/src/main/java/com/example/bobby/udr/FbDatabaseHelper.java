package com.example.bobby.udr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FbDatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name="udrfbsignin.db";
    public static final String Table_Name="udrfbsignin_table";
    public static final String COL_1="ID";
    public static final String COL_2="logout";
    public static final String COL_3="fbid";
    public static final String COL_4="fbname";
    public static final String COL_5="fbpic";


    SQLiteDatabase db;
    public FbDatabaseHelper(Context context) {
        super(context, Database_Name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table udrfbsignin_table(ID INTEGER PRIMARY KEY AUTOINCREMENT,logout Text,fbid TEXT,fbname TEXT, fbpic TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public boolean insertdata(String logout,String fbid,String fbname,String fbpic)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues b1=new ContentValues();
        b1.put(COL_2,logout);
        b1.put(COL_3,fbid);
        b1.put(COL_4,fbname);
        b1.put(COL_5,fbpic);
        long b2=db.insert(Table_Name, null, b1);
        if(b2==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor b1=db.rawQuery("Select * from "+Table_Name,null);
        return b1;
    }


    public boolean deletedata(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long b2=db.delete(Table_Name, null,null);
        if(b2>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

