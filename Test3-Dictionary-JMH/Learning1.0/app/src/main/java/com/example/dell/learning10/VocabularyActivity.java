package com.example.dell.learning10;

/**
 * Created by dell on 2018/9/19.
 */
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Map;
import android.widget.SimpleAdapter;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.HashMap;

public class VocabularyActivity extends Activity{
    private ListView listView;
    private VocabularySQLiteHelper dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
    }
}
