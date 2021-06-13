package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class setting_resttime extends AppCompatActivity {
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_resttime);

        seekBar=findViewById(R.id.seek_bar);
        int barH=seekBar.getWidth();


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
    }
}