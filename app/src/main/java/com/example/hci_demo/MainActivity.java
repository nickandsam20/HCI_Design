package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startBtn1=findViewById(R.id.startBtn1);
        ImageButton startBtn2=findViewById(R.id.startBtn2);



        startBtn1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , menu.class);
                startActivity(intent);
            }
        });
        startBtn2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Intent intent = new Intent();
                intent.setClass(MainActivity.this , menu.class);
                startActivity(intent);
            }
        });

    }
}