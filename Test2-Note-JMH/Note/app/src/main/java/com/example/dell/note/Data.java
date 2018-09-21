package com.example.dell.note;

/**
 * Created by dell on 2018/9/17.
 */
import android.provider.BaseColumns;

public class Data {
    public Data() {
    }

    public static abstract class data implements BaseColumns {
        public static final String TABLE_NAME="data";
        public static final String COLUMN_NAME_ID="id";//列：id
        public static final String COLUMN_NAME_CONTEXT="context";//列：内容
        public static final String COLUMN_NAME_TIME="time";//列：时间
    }
}

