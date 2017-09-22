package com.android.ejsoft.video_upload_000web.dbManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.android.ejsoft.video_upload_000web.dbManage.VS_tableConfig.Product;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by E J on 9/20/2017.
 */

public class VS_dbActivity extends AppCompatActivity{

    private final VS_dbConfig dbHelp;

    public VS_dbActivity(Context c){
        dbHelp = new VS_dbConfig(c);
    }

    public String sendData(Long fileName) {

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_NAME_CLIP_NAME,fileName);
        values.put(Product.COLUMN_NAME_FLAG,Boolean.FALSE);

        Long newRowID = db.insert(Product.TABLE_NAME,null,values);

        if(newRowID !=-1)
            return "Success ------>>>>>>>>"+newRowID;
        else return "Failed to insert clip name into db";

    }


    public String viewFlagData(){

        SQLiteDatabase db = dbHelp.getReadableDatabase();
//        db.execSQL("delete from "+ Product.TABLE_NAME);
//        db.execSQL("vacuum");
        String clipName="";
        String countQuery = "SELECT * FROM "+Product.TABLE_NAME+" WHERE "+Product.COLUMN_NAME_FLAG+"=0 LIMIT 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
            clipName = cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME_CLIP_NAME));
        }
        return "********************************************************************"+clipName+".mp4";

    }

}
