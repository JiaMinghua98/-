package com.example.dell.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ArrayList<Map<String, String>> items= (ArrayList<Map<String, String>>) bundle.getSerializable("result");

        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Data.data.COLUMN_NAME_ID,Data.data.COLUMN_NAME_CONTEXT},
                new int[]{R.id.noteID,R.id.textViewContext});

        ListView list = (ListView) findViewById(R.id.lstSearchResult);

        list.setAdapter(adapter);
    }
}
