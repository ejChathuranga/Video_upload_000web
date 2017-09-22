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


    private void viewAllData(){
        SQLiteDatabase db = dbHelp.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Product._ID,
                Product.COLUMN_NAME_CLIP_NAME,
                Product.COLUMN_NAME_FLAG
        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        // String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Product.COLUMN_NAME_CLIP_NAME + " DESC";

        Cursor cursor = db.query(
                Product.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // group the rows
                null,                                     // filter by row groups
                sortOrder                                 // The sort order
        );

        StringBuffer str = new StringBuffer();
        while (cursor.moveToNext()){
            str.append("-----------------------------------------------------------\n");
            str.append("ID: "+cursor.getString(0)+"\n");
            str.append("Item: "+cursor.getString(1)+"\n");
            str.append("Price: "+cursor.getString(2)+"\n");
            str.append("------------------------------\n");
        }

        System.out.println(str);
//        tvShowData.setText(str);

    }

}
