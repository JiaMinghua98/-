package com.example.dell.note;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.TableLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button Save;
    private DataBase dataBase;
    private SQLiteDatabase db;
    private static final String SETTINGS = "user_configurations";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 判断有无密码,如果有,检测输入的密码是否正确
        this.inputPsd();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBase=new DataBase(this);
        db=dataBase.getWritableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDialog();
            }
        });

    }
    //新增对话框
    private void InsertDialog(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("新建便签")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strID=((EditText)tableLayout.findViewById(R.id.txtID)).getText().toString();
                        String strContext=((EditText)tableLayout.findViewById(R.id.txtContext)).getText().toString();
                        String strTime=((EditText)tableLayout.findViewById(R.id.txtTime)).getText().toString();

                        //既可以使用Sql语句插入，也可以使用使用insert方法插入
                        //InsertUserSql(strID, strContext);
                        Insert(strID, strContext,strTime);
                        Log.d("TAG","777777777777777777777777777");
                        ArrayList<Map<String, String>> items=getAll();
                        Log.d("TAG","8888888888888888");
                        setWordsListView(items);
                        Log.d("TAG","99999999999999999999999999");

                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void psdDialog() {
        // 创建SharedPreferences对象,使用它保存用户的密码
        SharedPreferences settings = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        String psd = settings.getString("psd", "");
        if (psd.length() > 0) {
            // 有密码,点击设置密码后应该弹出有清除密码和修改密码两个选项的Dialog
            final CharSequence[] items = {getResources().getString(R.string.change_password), getResources().getString(R.string.clear_password) };
            // 使用AlertDialog来实现
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置AlertDialog要显示的item
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case 0:
                            // 选中修改密码,调用设置密码的函数
                            setPassword(R.string.change_password);
                            break;
                        case 1:
                            // 选中清除密码
                            SharedPreferences settings = getSharedPreferences(
                                    SETTINGS, MODE_PRIVATE);
                            Editor editor = settings.edit();
                            // 清空SharedPreferences中的密码
                            editor.putString("psd", "");
                            editor.commit();
                            Toast.makeText(
                                    MainActivity.this, "清除成功!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            // 没有密码,直接弹出设置密码对话框
            this.setPassword(R.string.set_password);
        }
    }

    // 设置密码函数,传递字符串在R.java中的int值
    private void setPassword(int resId) {
        final int name = resId;
        Context mContext = MainActivity.this;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 自定义AlertDialog的布局方式
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_layout_set_password,(ViewGroup) findViewById(R.id.dialog_layout_set_password_root));
        builder.setView(layout);
        builder.setTitle(name);
        builder.setPositiveButton(R.string.Ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_psw1 = (EditText) layout
                                .findViewById(R.id.et_password);
                        String psw1 = et_psw1.getText().toString();
                        EditText et_psw2 = (EditText) layout
                                .findViewById(R.id.et_confirm_Password);
                        String psw2 = et_psw2.getText().toString();
                        // 判断密码是否一致,且不为空
                        if (!TextUtils.isEmpty(psw1) && (psw1.equals(psw2))) {
                            // 密码一致,写入SharedPreference
                            SharedPreferences settings = getSharedPreferences(
                                    SETTINGS, MODE_PRIVATE);
                            Editor editor = settings.edit();
                            editor.putString("psd", psw1);
                            editor.commit();
                            switch (name) {
                                case R.string.change_password:
                                    Toast.makeText(
                                            MainActivity.this,
                                            getResources().getString(
                                                    R.string.change_password_succ),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case R.string.set_password:
                                    Toast.makeText(
                                            MainActivity.this,
                                            getResources().getString(
                                                    R.string.set_password_succ),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                        else {
                            switch (name) {
                                case R.string.change_password:
                                    Toast.makeText(
                                            MainActivity.this,
                                            getResources().getString( R.string.change_password_failed),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case R.string.set_password:
                                    Toast.makeText(
                                            MainActivity.this,
                                            getResources().getString(R.string.set_password_failde),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                        }
                        setPassword(name);
                    }
                });
        builder.setNegativeButton(R.string.Cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击取消按钮,撤销设置密码对话框
                        dialog.dismiss();
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }

    // 如果有密码,程序运行时先让用户输入密码
    private void inputPsd() {
        // 用于统计输入密码的次数
        count++;
        SharedPreferences settings = getSharedPreferences(SETTINGS,
                MODE_PRIVATE);
        final String psd = settings.getString("psd", "");
        // 判断是否有密码
        if (psd.length() > 0) {
            // 有密码
            Context mContext = MainActivity.this;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("输入密码");
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog_input_psd,
                    (ViewGroup) findViewById(R.id.dialog_input_psd_root));
            builder.setView(layout);
            builder.setPositiveButton(R.string.Ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et_psd = (EditText) layout
                                    .findViewById(R.id.et_input_password);
                            String psd_inputted = et_psd.getText().toString();
                            if (!psd.equals(psd_inputted)) {
                                Toast.makeText(MainActivity.this, "密码不正确!",
                                        Toast.LENGTH_SHORT).show();
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // 只允许填错密码3次
                                if (count < 3) {
                                    inputPsd();
                                } else {
                                    MainActivity.this.finish();
                                }
                            }
                        }
                    });
            builder.setNegativeButton(R.string.Cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();// 结束程序
                        }
                    });
            // 设置对话框不可取消.可以修正用户设置了密码,在弹出输入密码对话框时点返回键取消了输入密码对话框的BUG
            builder.setCancelable(false);
            AlertDialog ad = builder.create();
            ad.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        TextView textId=null;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            psdDialog();
        }
        if (id == R.id.action_searchAll){
            ArrayList<Map<String, String>> items=getAll();
            setWordsListView(items);
        }
        if (id ==R.id.action_search){
            SearchDialog();
        }
        if (id ==R.id.action_delete){
                DeleteDialog();
        }
        if (id ==R.id.action_photo){
            Intent intent=new Intent(MainActivity.this, PhotoActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //删除对话框
    private void DeleteDialog(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("删除便签")
                .setView(tableLayout)//设置视图
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String txtDelete=((EditText)tableLayout.findViewById(R.id.txtSearch)).getText().toString();
                DeleteUseSql(txtDelete);
                Log.d("TAG","2222@@");
                Toast.makeText(MainActivity.this,"删除成功!",Toast.LENGTH_LONG).show();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }
    //使用Sql语句删除单词
    private void DeleteUseSql(String strId) {
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Log.d("TAG","1111@@");
        String sql="delete from data where id='"+strId+"'";
        Log.d("TAG","3333@@");
        db.execSQL(sql);
    }
    //设置适配器，在列表中显示单词
    private void setWordsListView(ArrayList<Map<String, String>> items){
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Data.data.COLUMN_NAME_ID,Data.data.COLUMN_NAME_CONTEXT,Data.data.COLUMN_NAME_TIME},
                new int[]{R.id.noteID,R.id.textViewContext,R.id.textTime});
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
            map.put(Data.data.COLUMN_NAME_ID,String.valueOf(cursor.getInt(0)));
            map.put(Data.data.COLUMN_NAME_CONTEXT, cursor.getString(1));
            map.put(Data.data.COLUMN_NAME_TIME, cursor.getString(2));
            result.add(map);
        }
        return result;
    }
    //使用insert方法增加单词
    private void Insert(String strID,String strContext,String strTime) {
        //Gets the data repository in write mode*/
        SQLiteDatabase db = dataBase.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Data.data.COLUMN_NAME_ID, strID);
        values.put(Data.data.COLUMN_NAME_CONTEXT, strContext);
        //values.put(Data.data.COLUMN_NAME_TIME,strTime);
Log.d("TAG","66666666666666666666");
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                Data.data.TABLE_NAME,
                null,
                values);
        Log.d("TAG","问题！！！");
    }
    //使用Sql语句查找
    private ArrayList<Map<String, String>> SearchUseSql(String strWord) {
        SQLiteDatabase db = dataBase.getReadableDatabase();

        String sql="select * from data where id like ? order by id desc";
        Cursor c=db.rawQuery(sql,new String[]{"%"+strWord+"%"});

        return ConvertCursor2List(c);
    }
    //查找对话框
    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找便签")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearch=((EditText)tableLayout.findViewById(R.id.txtSearch)).getText().toString();

                        ArrayList<Map<String, String>> items=null;
                        items=SearchUseSql(txtSearch);
                        if(items.size()>0) {
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("result",items);
                            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else
                            Toast.makeText(MainActivity.this,"没有找到",Toast.LENGTH_LONG).show();


                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

}


