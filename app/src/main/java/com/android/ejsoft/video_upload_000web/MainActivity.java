package com.android.ejsoft.video_upload_000web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.ejsoft.video_upload_000web.dbManage.VS_dbConfig;

public class MainActivity extends AppCompatActivity {


    private VS_dbConfig dbHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelp = new VS_dbConfig(this);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }
}
