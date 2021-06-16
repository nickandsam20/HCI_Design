package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class setting_resttime extends AppCompatActivity {
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_resttime);

        seekBar=findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
                if(progress<45)seekBar.setProgress(30);
                else if(progress>=45 && progress<75)seekBar.setProgress(60);
                else if(progress>=75)seekBar.setProgress(390);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });

        ImageButton returnBtn=findViewById(R.id.resttime_returnBtn);
        returnBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(setting_resttime.this , new_menu.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("","start");
//
//        RelativeLayout rl = findViewById(R.id.mylayout);
//        int barH=seekBar.getWidth();
//        ImageView img30,img60,img90;
//        int imgw=80,imgh=80;
//        int screen_width=rl.getWidth();
//        img30=new ImageView(getApplicationContext());
//        img60=new ImageView(getApplicationContext());
//        img90=new ImageView(getApplicationContext());
//        img30.setImageResource(R.drawable.resttime30);
//        img60.setImageResource(R.drawable.resttime60);
//        img90.setImageResource(R.drawable.resttime90);
//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(imgw, imgh);
//        params1.bottomMargin = ((getScreenHeight()-400)/2)+400/3-(imgh/2);
//        params1.leftMargin = getScreenWidth()/2-imgw/2;
//        Log.d("screen_width",Integer.toString(screen_width));
//        Log.d("barh",Integer.toString(barH));
//        rl.addView(img30,params1);
//    }
//    public static int getScreenWidth() {
//        return Resources.getSystem().getDisplayMetrics().widthPixels;
//    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}