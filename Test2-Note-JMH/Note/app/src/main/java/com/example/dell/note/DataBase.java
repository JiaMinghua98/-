package com.example.dell.note;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.UserDictionary;
import android.content.Context;
import android.widget.Toast;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import com.example.dell.note.DataBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.view.ContextMenu;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.TableLayout;
/**
 * Created by dell on 2018/9/16.
 */

public class DataBase extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    //建表SQL
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE " + Data.data.TABLE_NAME + " (" +
            Data.data.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Data.data.COLUMN_NAME_CONTEXT + " TEXT"+ ","
            +Data.data.COLUMN_NAME_TIME + " TEXT" + " )";

    //删表SQL
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Data.data.TABLE_NAME;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("TAG","444");
        //创建数据库
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //当数据库升级时被调用，首先删除旧表，然后调用OnCreate()创建新表
        Log.d("TAG","555");
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}


