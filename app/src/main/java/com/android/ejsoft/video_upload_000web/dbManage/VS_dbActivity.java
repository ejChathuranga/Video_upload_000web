package com.android.ejsoft.video_upload_000web.dbManage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import com.android.ejsoft.video_upload_000web.dbManage.VS_tableConfig.Product;

/**
 * Created by E J on 9/20/2017.
 */

public class VS_dbActivity extends AppCompatActivity{

    private final VS_dbConfig dbHelp;

    public VS_dbActivity(Context c){
        dbHelp = new VS_dbConfig(c);
    }

    public String sendData(Long aLong) {

        String fileName = String.valueOf(aLong)+".mp4";

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelp.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_NAME_CLIP_NAME,fileName);
        values.put(Product.COLUMN_NAME_FLAG,Boolean.FALSE);

        Long newRowID = db.insert(Product.TABLE_NAME,null,values);

        if(newRowID !=-1) {
//            Log.d("Success ------>>>>>>>>" + fileName, "   into  " + newRowID);
            return "Success ------>>>>>>>>" + fileName + " into " + newRowID;
        }
        else return "Failed to insert clip name into db";

    }


    public String viewFlagData(){

        SQLiteDatabase db = dbHelp.getReadableDatabase();

//        SQLite Reset Primary Key Field
//        db.execSQL("delete from "+Product.TABLE_NAME);
//        db.execSQL("delete from sqlite_sequence where name='"+Product.TABLE_NAME+"'");

        String clipName="";
        String countQuery = "SELECT * FROM "+Product.TABLE_NAME+" WHERE "+Product.COLUMN_NAME_FLAG+"=0 LIMIT 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
            clipName = cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME_CLIP_NAME));
        }
        return clipName;

    }
    public VS_ReturnValues viewFlagStatus(){

        SQLiteDatabase db = dbHelp.getReadableDatabase();

        int id=0;
        String clipName= "";
        Boolean flagStatus =false;
        String countQuery = "SELECT * FROM "+Product.TABLE_NAME+" WHERE "+Product.COLUMN_NAME_FLAG+"=0 LIMIT 1";
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.moveToFirst()){
            id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product._ID)));
            clipName = cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME_CLIP_NAME));
            flagStatus = true;
        }
//        rv.setFileName(clipName);
//        rv.setFlag(flagStatus);
        VS_ReturnValues rv = new VS_ReturnValues(id,clipName,flagStatus);
        return rv;

    }

    public void deleteUploaded(){
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        // Define 'where' part of query.
        String selection = Product._ID + " = ?";
        // Specify arguments in placeholder order.
        //String[] selectionArgs = { txtID.getText().toString() };
        // Issue SQL statement.
        //db.delete(Product.TABLE_NAME, selection, selectionArgs);
    }

}
