package com.example.dell.helloword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {

    EditText e2,e10,e16;
    String s2,s10,s16;
    Button b2,b10,b16,re2,clear22;
    int i10;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "普通计算器", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(ThirdActivity.this, HelloWorldActivity.class);
                startActivity(intent1);
                break;
            case R.id.remove_item:
                Toast.makeText(this, "科学计算器", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ThirdActivity.this, SecoundActivity.class);
                startActivity(intent);
                break;
            case R.id.changdu:
                Toast.makeText(this, "进制转换", Toast.LENGTH_SHORT).show();
            default:
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        b2 = (Button) findViewById(R.id.btwo);
        b10 = (Button) findViewById(R.id.bten);
        b16 = (Button) findViewById(R.id.bsixt);
        clear22 = (Button) findViewById(R.id.clear22);
        e2 = (EditText) findViewById(R.id.editwo);
        e10 = (EditText) findViewById(R.id.ten);
        e16 = (EditText) findViewById(R.id.sixt);


        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s2 = e2.getText().toString();

                s10 = Integer.valueOf(s2, 2).toString();
                s16 = Integer.toHexString(Integer.parseInt(s2, 2));

                e10.setText(s10);
                e16.setText(s16);
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s10 = e10.getText().toString();
                i10 = Integer.parseInt(s10);

                s2 = Integer.toBinaryString(i10);

                s16 = Integer.toHexString(i10);

                e2.setText(s2);
                e16.setText(s16);
            }
        });
        b16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s16 = e16.getText().toString();

                s2 = Integer.toBinaryString(Integer.valueOf(s16, 16));
                s10 = Integer.valueOf(s16, 16).toString();

                e2.setText(s2);
                e10.setText(s10);
            }
        });

        clear22.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                e2.setText("");
                e10.setText("");
                e16.setText("");
            }
        }));
    }
}
