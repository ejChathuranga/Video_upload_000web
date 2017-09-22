package com.android.ejsoft.video_upload_000web;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean netCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Cam2VideoFrag.newInstance())
                    .commit();
        }

        // Checking network connection
        notifyNetCon();


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
}
