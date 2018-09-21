package com.example.dell.learning10;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;

import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.dell.learning10.DataBaseHelper;
import android.content.Context;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dell.learning10.WordsPersonal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.media.MediaPlayer;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import java.io.IOException;


public class PersonalActivity extends AppCompatActivity {
    private static final String TAG = "myTag";
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        textView1 = (TextView) findViewById(R.id.text1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1();
            }
        });

        textView2 = (TextView) findViewById(R.id.text2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2();
            }
        });

        textView3 = (TextView) findViewById(R.id.text3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text3();
            }
        });
        textView4 = (TextView) findViewById(R.id.text4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text4();
            }
        });

        textView5 = (TextView) findViewById(R.id.text5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://zx.youdao.com/zx/page/1053"));
                startActivity(intent);
            }
        });

        mediaPlayer = new MediaPlayer();
        final MediaPlayer mp=MediaPlayer.create(this,R.raw.aaa);
        //mp.start();


        ImageButton music=(ImageButton)findViewById(R.id.action_music);
        //开始播放
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        ImageButton stop=(ImageButton)findViewById(R.id.action_stop);
        //暂停播放
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mp.pause();

            }
        });

    }

    //新增对话框
    private void text1(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.text, null);
        new AlertDialog.Builder(this)
                .setTitle("箴言哲理类")//标题
                .setView(tableLayout)//设置视图
                //确定返回及其动作
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }

    //新增对话框
    private void text2(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.text, null);
        new AlertDialog.Builder(this)
                .setTitle("环境生态类")//标题
                .setView(tableLayout)//设置视图
                //确定返回及其动作
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }

    //新增对话框
    private void text3(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.text, null);
        new AlertDialog.Builder(this)
                .setTitle("社会弊病类")//标题
                .setView(tableLayout)//设置视图
                //确定返回及其动作
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }

    //新增对话框
    private void text4(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.text, null);
        new AlertDialog.Builder(this)
                .setTitle("图表描述类")//标题
                .setView(tableLayout)//设置视图
                //确定返回及其动作
                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_return:
                Intent intent=new Intent(PersonalActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}




