package com.android.ejsoft.video_upload_000web.dbManage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.ejsoft.video_upload_000web.dbManage.VS_tableConfig.Product;

/**
 * Created by E J on 9/20/2017.
 */

public class VS_dbConfig extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DB_videoStream";
    public static final int DATABASE_VERSION =1;

    public static final String SQL_CREATE_PRODUCT = "CREATE TABLE "+ Product.TABLE_NAME+" (\n" +
            "\t"+Product._ID+"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t"+Product.COLUMN_NAME_CLIP_NAME+"\tTEXT,\n" +
            "\t"+Product.COLUMN_NAME_FLAG+"\tBOOLEAN\n" +
            ");";

    public static final String SQL_DELETE_PRODUCT = "DROP TABLE IF EXISTS product";


    public VS_dbConfig(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCT);
        onCreate(db);

    }
}
