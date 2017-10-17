package com.android.ejsoft.video_upload_000web;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.ejsoft.video_upload_000web.dbManage.VS_ReturnValues;
import com.android.ejsoft.video_upload_000web.dbManage.VS_dbActivity;
import com.android.ejsoft.video_upload_000web.uploadManage.uploadConfig;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;


    public uploadConfig uv;
    VS_dbActivity deleteVsDbActivity  = new VS_dbActivity(this);

    private Boolean netCon;
    private Boolean flagStatus;
    private String fileName;
    private String selectedPath;
    private int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Cam2VideoFrag.newInstance())
                    .commit();
        }


        // Checking network connection
        notifyNetCon();
        checkFlag();
        startRepeatingTask();


    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkStatus(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };


    public void checkStatus(){

        if(uv.getStatus() == AsyncTask.Status.PENDING){
            // My AsyncTask has not started yet
            Log.d("---------------","------------: PENDING");
            uv = null;
            checkFlag();
        }

        if(uv.getStatus() == AsyncTask.Status.RUNNING){
            // My AsyncTask is currently doing work in doInBackground()
            Log.d("---------------","------------: RUNNING");
        }

        if(uv.getStatus() == AsyncTask.Status.FINISHED){
            // My AsyncTask is done and onPostExecute was called
            Toast.makeText(this, "UPLOAD COMPLETE", Toast.LENGTH_SHORT).show();
            deleteSendClipNameFromDB();

            Log.d("---------------","------------: FINISHED");
        }
    }

    private void deleteSendClipNameFromDB() {
        String dd = deleteVsDbActivity.deleteUploadedFileName(String.valueOf(id));
        Log.d("deleteSendClip","------======>>>>>"+dd);
        uv = null;
        id = 0;
        fileName = null;
        flagStatus = null;
        selectedPath = null;
        checkFlag();
    }


    private void checkFlag(){

        uv = new uploadConfig(); // to continue checkStatus method we have to create this object here

        VS_dbActivity dbActivity = new VS_dbActivity(getBaseContext());
        VS_ReturnValues getValues = dbActivity.viewFlagStatus(); // creating object using getter and setter
        flagStatus = getValues.getFlag();
        fileName = getValues.getFileName();
        id =getValues.getId();
        selectedPath = getVideoFilePath(fileName);
        if(flagStatus==true){
            uploadVideo(selectedPath);
            Log.d(id+"-----------"+fileName,"-------------"+flagStatus);
        }
        else{
            Toast.makeText(this, "All The files are uploadede", Toast.LENGTH_SHORT).show();
            Log.d("=====++++++>>>>>>>>>>>","All The files are uploadede");

        }
//        Toast.makeText(getBaseContext(), id+"%%%%%"+fileName+"%%%"+flagStatus, Toast.LENGTH_SHORT).show();

    }

    private void uploadVideo(String path) {

        uv = new uploadConfig();
        uv.execute(path);
        selectedPath = null;
    }

    private String getVideoFilePath(String fileName) {
//        final File dir = context.getExternalFilesDir(null);
        final File dir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)));
        return (dir == null ? "" : (dir.getAbsolutePath() + "/"))+ fileName;

    }

    private void notifyNetCon(){
        netCon = isNetworkAvailable();
        if(!netCon) showAlert(this);
        else Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

    }

    private Dialog showAlert(final Context c) {
            return new AlertDialog.Builder(c)
                    .setTitle(R.string.permission_network_title)
                    .setMessage(R.string.permission_network)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(c, "PLEASE TURN ON NETWORK", Toast.LENGTH_SHORT).show();
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(c, "Without internet can't sync videos \n" +
                                            "After turned on internet videos will sync", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPause(){
        super.onPause();
        stopRepeatingTask();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopRepeatingTask();
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

}
