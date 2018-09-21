package com.example.dell.note;

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
import android.support.v7.widget.Toolbar;

public class NewNote extends AppCompatActivity {

    private Button Save;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        //创建SQLiteOpenHelper对象，注意第一次运行时，此时数据库并没有被创建
        dataBase = new DataBase(this);
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        //Save = (Button) findViewById(R.id.save);
        final String strID=((EditText)tableLayout.findViewById(R.id.txtID)).getText().toString();
        final String strContext=((EditText)tableLayout.findViewById(R.id.txtContext)).getText().toString();

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存便签
                InsertUserSql(strID,strContext);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView dataID=null;
        TextView dataContext=null;

        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;

        switch (item.getItemId()){
            case R.id.deleteNote:
                //删除便签
                info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                itemView=info.targetView;
                break;
            case R.id.insertPhoto:
                //插入图片
                break;
        }
        return true;
    }
    //设置适配器，在列表中显示单词
    private void setWordsListView(ArrayList<Map<String, String>> items){
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Data.data.COLUMN_NAME_ID,Data.data.COLUMN_NAME_CONTEXT},
                new int[]{R.id.noteID,R.id.textViewContext});
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }
    //使用Sql语句查找
    private ArrayList<Map<String, String>> getAll() {
        SQLiteDatabase db = dataBase.getReadableDatabase();

        String sql="select * from data";
        Cursor c=db.rawQuery(sql,null);

        return ConvertCursor2List(c);
    }


    private ArrayList<Map<String, String>> ConvertCursor2List(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put(Data.data.COLUMN_NAME_ID, String.valueOf(cursor.getInt(0)));
            map.put(Data.data.COLUMN_NAME_CONTEXT, cursor.getString(1));
            result.add(map);
        }
        return result;
    }
    //使用Sql语句插入单词
    private void InsertUserSql(String strID, String strContext){
        String sql="insert into  words(id,context) values(?,?)";
        Log.d("TAG","!!!");

        //Gets the data repository in write mode*/
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.execSQL(sql,new String[]{strID,strContext});
    }
}




