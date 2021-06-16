package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class setting_passwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_passwd);

        ImageButton backupBtn=findViewById(R.id.backupPasswdBtn);
        ImageButton gestureBtn=findViewById(R.id.gesturePasswdBtn);
        backupBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(setting_passwd.this ,setting_backup_passwd.class);
                startActivity(intent);
            }
        });

        gestureBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(setting_passwd.this ,setting_gesture_passwd.class);
                startActivity(intent);
            }
        });
    }
}