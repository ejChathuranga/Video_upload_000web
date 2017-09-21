package com.android.ejsoft.video_upload_000web.uploadManage;


import android.os.AsyncTask;

/**
 * Created by E J on 9/18/2017.
 */

public class uploadConfig extends AsyncTask<String, Void, String> {

    @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                String filePath = params[0];
                Upload u = new Upload();
                String msg = u.uploadVideo(filePath);
                return msg;
            }


}
