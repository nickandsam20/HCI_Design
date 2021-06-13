package com.example.hci_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menu_horizontal_scroll extends  AppCompatActivity {

    ImageButton []imgbtns;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("","menu created");
        setContentView(R.layout.activity_menu_horizontal_scroll);
        HorizontalScrollView hsl = (HorizontalScrollView) findViewById(R.id.scrollView);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.horizontalbar);


        imgbtns=new ImageButton[5];
        for(int i = 0; i < 5; i++)
        {
            ImageButton b = new ImageButton(this);
            imgbtns[i]=b;
            if(i==0){
                b.setBackgroundResource(R.drawable.on);
            }else if(i==1){
                b.setBackgroundResource(R.drawable.resttime);
                b.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 寫要做的事...
                        Log.d("","click");
                        Intent intent = new Intent();
                        intent.setClass(menu_horizontal_scroll.this ,setting_resttime.class);
                        startActivity(intent);
                    }
                });
            }else if(i==2){
                b.setBackgroundResource(R.drawable.difficulity);
                b.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 寫要做的事...
                        Log.d("","click");
                        Intent intent = new Intent();
                        intent.setClass(menu_horizontal_scroll.this ,setting_difficulity.class);
                        startActivity(intent);
                    }
                });
            }else if(i==3){
                b.setBackgroundResource(R.drawable.password);
                b.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 寫要做的事...
                        Log.d("","click");
                        Intent intent = new Intent();
                        intent.setClass(menu_horizontal_scroll.this ,setting_passwd.class);
                        startActivity(intent);
                    }
                });
            }else{
                b.setBackgroundResource(R.drawable.choose_mouse);
            }
            l1.addView(b);
        }
    }
}