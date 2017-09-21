package com.android.ejsoft.video_upload_000web.dbManage;

import android.content.Context;
import android.content.ContentValues;
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

    public String sendData(String filePath) {

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_NAME_CLIP_NAME,filePath);
        values.put(Product.COLUMN_NAME_FLAG,Boolean.FALSE);

        Long newRowID = db.insert(Product.TABLE_NAME,null,values);

        if(newRowID !=-1)
            return "Success ------>>>>>>>>"+newRowID;
        else return "False";

    }
}
