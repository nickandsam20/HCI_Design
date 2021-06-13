package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class setting_difficulity extends AppCompatActivity {

    private setting_state app_state=MainActivity.app_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_difficulity);
        ImageButton returnBtn=findViewById(R.id.difficulity_returnbtn);
        returnBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(setting_difficulity.this , menu_horizontal_scroll.class);
                startActivity(intent);
            }
        });
        SwitchCompat switchEasy=findViewById(R.id.switchEasy);
        SwitchCompat switchHard=findViewById(R.id.switchHard);
        if(app_state.mode==0){
            switchEasy.setChecked(true);
            switchHard.setChecked(false);
        }else{
            switchEasy.setChecked(false);
            switchHard.setChecked(true);
        }

        switchEasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    switchHard.setChecked(false);
                    app_state.mode=0;
                }else{
                    switchHard.setChecked(true);
                    app_state.mode=1;
                }
            }
        });
        switchHard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    switchEasy.setChecked(false);
                    app_state.mode=1;
                }else{
                    switchEasy.setChecked(true);
                    app_state.mode=0;
                }
            }
        });

    }
}