package com.example.huanghanwei.license;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // 1. 宣告物件

    EditText NumInput;
    Button Confirm, Reset;
    TextView Notice, Result;

    //讀取txt檔
    StringBuilder text = new StringBuilder();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream;
            inputStream = assetManager.open("car.txt");
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append(",");
            }
            /*String line = br.readLine();
            text.append(line);
            text.append("\n");*/

            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        final String s = text.toString().replaceAll(" ", "");
        final String[] RawData = s.split(",");
        final String[] license1 = new String[RawData.length];
        final String[] license2 = new String[RawData.length];
        //final int length = RawData.length;

        for(int i = 0; i < RawData.length; i++){
            try{
                license1[i] = RawData[i].substring(0, RawData[i].indexOf("-"));
                license2[i] = RawData[i].substring(RawData[i].indexOf("-") + 1, RawData[i].length());
            }
            catch(StringIndexOutOfBoundsException e){

            }
        }

        // 2. 連結元件

        NumInput = this.findViewById(R.id.NumInput);
        Confirm = this.findViewById(R.id.Confirm);
        Reset = this.findViewById(R.id.Reset);
        Notice = this.findViewById(R.id.Notice);
        Result = this.findViewById(R.id.Result);
        //讓結果可以可以滾動
        Result.setMovementMethod(ScrollingMovementMethod.getInstance());


        // 3. 建立事件
        Confirm.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                String Number = NumInput.getText().toString();
                Result.setText("您輸入的車牌數字為: " + Number);
                int count = 0;
                String illegal = "";
                for(int i = 0; i < RawData.length; i++){
                    if(Number.equals(license1[i]) || Number.equals(license2[i])){
                        illegal = illegal.concat(RawData[i]);
                        illegal = illegal.concat("\n");
                        //Result.setText("您輸入的車牌數字為: " + Number + "\n" + "註銷車牌如下: " + "\n");
                        count++;
                    }
                }
                Notice.setText("您輸入的車牌數字為: " + Number + "\n" +"註銷車牌如下: ");
                Result.setText(illegal);
                if(count == 0){
                    Notice.setText("您輸入的車牌數字為: " + Number);
                    Result.setText("     此車牌為合格車牌！！！");
                }
            }
        });

        Reset.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View view) {
                NumInput.setText("");
                Notice.setText("");
                Result.setText("");
            }
        });
    }
}