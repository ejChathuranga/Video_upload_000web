package com.android.ejsoft.video_upload_000web.dbManage;

import android.provider.BaseColumns;

/**
 * Created by E J on 9/20/2017.
 */

public class VS_tableConfig {
    private VS_tableConfig(){}

    /* Inner class that defines the table contents */
    public static class Product implements BaseColumns {
        public static final String TABLE_NAME = "video_clips";
        public static final String COLUMN_NAME_CLIP_NAME= "clip_name";
        public static final String COLUMN_NAME_FLAG = "flag";
    }
}
