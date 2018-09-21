package com.example.dell.helloword;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.util.Log.*;

public class HelloWorldActivity extends AppCompatActivity {

    private static final String TAG = "HelloWorldActivity";

    private int[] idNum = {
            R.id.button1Zero, R.id.buttonlOne, R.id.buttonlTwo, R.id.buttonlThree, R.id.buttonlFour,
            R.id.buttonLFive, R.id.buttonlSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine
    };
    private int[] idCal = {
            R.id.button1Point, R.id.buttonClear, R.id.buttonlAllClear,
            R.id.buttonChengL, R.id.buttonChu, R.id.buttonlPlus, R.id.buttonlJian
    };
    private Button[] buttonsCal = new Button[idCal.length];
    private Button[] buttonsNum = new Button[idNum.length];
    private EditText editText;
    private TextView textView;
    private Button buttonEqu;
    private Button buttonCle;
    private Button buttonDel;
    private static String Text;

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
                break;
            case R.id.remove_item:
                Toast.makeText(this, "科学计算器", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HelloWorldActivity.this, SecoundActivity.class);
                startActivity(intent);
                break;
            case R.id.changdu:
                Toast.makeText(this,"进制转换",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(HelloWorldActivity.this,ThirdActivity.class);
                startActivity(intent2);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        editText = (EditText) findViewById(R.id.textInput);
        editText.setText("");
        editText.setInputType(InputType.TYPE_NULL);
        textView = (TextView) findViewById(R.id.textOutput);
        textView.setText("");

        for (int i = 0; i < idNum.length; i++) {
            buttonsNum[i] = (Button) findViewById(idNum[i]);
            buttonsNum[i].setOnClickListener(new NumberOnClick(buttonsNum[i].getText().toString()));
        }
        for (int idcal = 0; idcal < idCal.length; idcal++) {
            buttonsCal[idcal] = (Button) findViewById(idCal[idcal]);
            buttonsCal[idcal].setOnClickListener(new CalOnClick(buttonsCal[idcal].getText().toString()));

        }

        buttonEqu = (Button) findViewById(R.id.buttonEqual);
        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("="+new Calculate(editText.getText().toString()).str);
            }
        });

        buttonCle = (Button) findViewById(R.id.buttonClear);
        buttonCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                textView.setText("");
            }
        });
        buttonDel = (Button) findViewById(R.id.buttonlAllClear);

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText.getText().toString().isEmpty() ) {
                    Text = editText.getText().toString();
                    Text = Text.substring(0, Text.length() - 1);
                    editText.setText(Text);
                }
            }
        });
    }

    class NumberOnClick implements View.OnClickListener {
        String Num;

        public NumberOnClick(String num) {
            Num = num;
        }

        @Override
        public void onClick(View v) {
            if (!textView.getText().toString().equals("")) {
                editText.setText("");
                textView.setText("");
            }
            editText.append(Num);
        }
    }

    class CalOnClick implements View.OnClickListener {
        String Mes;
        String[] calSymbol = {"+", "-", "*", "/", ".","%"};

        public CalOnClick(String mes) {
            Mes = mes;
        }

        @Override
        public void onClick(View v) {
            if (!textView.getText().toString().equals("")) {
                editText.setText("");
                textView.setText("");
            }
            // 检查是否运算符重复输入
            for (int i = 0; i < calSymbol.length; i++) {
                if (Mes.equals(calSymbol[i])) {
                    if (editText.getText().toString().split("")
                            [editText.getText().toString().split("").length - 1].equals(calSymbol[i])) {
                        Mes = "";
                    }

                }
            }
            editText.append(Mes);
        }
    }

    public class Calculate {
        public  String s1;
        StringBuilder str;

        public Calculate(String m) {
            this.s1 = m;
            try {
                eval();
            } catch (Exception e) {
                str.delete(0, str.length());
                str.append("错误");
            }
        }
        public List<String> midToAfter(List<String> midList)throws EmptyStackException {
            List<String> afterList=new ArrayList<String>();
            Stack<String> stack=new Stack<String>();
            for(String str:midList){
                int flag=this.matchWitch(str);
                switch (flag) {
                    case 7:
                        afterList.add(str);
                        break;
                    case 8:
                        break;
                    case 1:
                        stack.push(str);
                        break;
                    case 2:
                        String pop=stack.pop();
                        afterList.add(pop);
                        pop=stack.pop();
                        break;
                    default:
                        if(stack.isEmpty()){
                            stack.push(str);
                            break;
                        }
                        else{
                                {

                                int ji1=this.youxianji(str);
                                int ji2=this.youxianji(stack.peek());
                                if(ji1>ji2){
                                    stack.push(str);
                                }else{
                                    while(!stack.isEmpty()){
                                        String f=stack.peek();
                                        {
                                            if(this.youxianji(str)<=this.youxianji(f)){
                                                afterList.add(f);
                                                stack.pop();
                                            }else{
                                                stack.push(str);
                                                break;
                                            }
                                        }
                                    }
                                    if(stack.isEmpty()){
                                        stack.push(str);
                                    }
                                }
                                break;
                            }
                        }
                }
            }
            while(!stack.isEmpty()){
                afterList.add(stack.pop());
            }
            StringBuffer sb=new StringBuffer();
            for(String s:afterList){
                sb.append(s+" ");
            }
            return afterList;
        }
        /**
         判断优先级
         */
        public int youxianji(String str){
            int result=0;
            if(str.equals("+")||str.equals("-")){
                result=1;
            }else{
                result =2;
            }
            return result;
        }
        /**
         *判断字符串属于操作数、操作符
         */
        public int matchWitch(String s){
            if(s.equals("+")){
                return 3;
            }else if(s.equals("-")){
                return 4;
            }else if(s.equals("*")){
                return 5;
            }else if(s.equals("/")){
                return 6;
            }else if(s.equals("%")) {
                return 8;
            }else{
                return 7;
            }
        }
        /**
         *计算a@b
         */
        public Double singleEval(Double pop2,Double pop1,String str){
            Double value=0.0;
            if(str.equals("+")){
                value=pop2+pop1;
            }else if(str.equals("-")){
                value=pop2-pop1;
            }else if(str.equals("*")){
                value=pop2*pop1;
            }else if(str.equals("/")){
                value=pop2/pop1;
            }
            return value;
        }
        private double result;

        public double getResult() {
            return result;
        }
        public void setResult(double result) {
            this.result = result;
        }
        private int state;

        public int getState() {
            return state;
        }
        public void setState(int state) {
            this.state = state;
        }

        public void countHouzhui(List<String> list){
            str = new StringBuilder("");
            state=0;
            result=0;
            Stack<Double> stack=new Stack<Double>();
            for(String str:list){
                int flag=this.matchWitch(str);
                switch (flag) {
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        Double pop1=stack.pop();
                        Double pop2=stack.pop();
                        Double value=this.singleEval(pop2, pop1, str);
                        stack.push(value);
                        break;
                    default:
                        Double push=Double.parseDouble(str);
                        stack.push(push);
                        break;
                }
            }
            if(stack.isEmpty()){
                state=1;
            }else{
                result=stack.peek();
                str.append(stack.pop());
            }


        }

        public void eval()throws Exception{
            List<String> list=new ArrayList<String>();
            //匹配运算符
            Pattern p = Pattern.compile("[+\\-/\\*]|\\d+\\.?\\d*");
            Matcher m = p.matcher(s1);
            while(m.find()){
                list.add(m.group());
            }
            List<String> afterList=this.midToAfter(list);
            this.countHouzhui(afterList);
        }

    }
}
