package com.example.dell.learning10;

/**
 * Created by dell on 2018/9/20.
 */
import android.provider.BaseColumns;

public class Data {
    public Data() {
    }

    public static abstract class data implements BaseColumns {
        public static final String TABLE_NAME="Words";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_Chinese="isChinese";
        public static final String COLUMN_NAME_key="key";
        public static final String COLUMN_NAME_fy="fy";
    }
}
