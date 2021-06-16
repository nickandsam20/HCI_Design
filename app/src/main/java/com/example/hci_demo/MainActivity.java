package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    public static setting_state app_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Python.start(new AndroidPlatform(MainActivity.this));
        ImageButton startBtn1=findViewById(R.id.startBtn1);
        ImageButton startBtn2=findViewById(R.id.startBtn2);

        app_state=new setting_state();

        startBtn1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this ,menu_horizontal_scroll.class);
                startActivity(intent);
            }
        });
        startBtn2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , menu_horizontal_scroll.class);
                startActivity(intent);
            }
        });

    }
}