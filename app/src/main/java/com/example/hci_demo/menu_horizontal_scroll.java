package com.example.hci_demo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
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
            }else if(i==2){
                b.setBackgroundResource(R.drawable.difficulity);
            }else if(i==3){
                b.setBackgroundResource(R.drawable.password);
            }else{
                b.setBackgroundResource(R.drawable.choose_mouse);
            }
            l1.addView(b);
        }
    }
}