package com.example.hci_demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class setting_backup_passwd extends AppCompatActivity {

    private setting_state app_state=MainActivity.app_state;
    private int mode;//0代表沒設定過密碼 1代表曾設定過密碼，要先輸入舊密碼 2.代表書如正確的舊密碼，進入修改密碼階段

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_backup_passwd);

        TextView info=findViewById(R.id.backup_setting_info);
        if(!app_state.hasBackup){
            info.setText("你還沒設定過備用密碼，請先設定備用密碼!");
            mode=0;
        }else if(mode==2){
            mode=1;
            info.setText("你已經設定過備用密碼了，若要修改備用密碼，請先輸入原密碼");
            mode=1;
        } else{
            info.setText("你已經設定過備用密碼了，若要修改備用密碼，請先輸入原密碼");
            mode=1;
        }

        EditText inputPasswd=findViewById(R.id.passwd_input);

        inputPasswd.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            // the user is done typing.
                            if(mode==0||mode==2){
                                int input=Integer.parseInt(inputPasswd.getText().toString());
                                if(input<10000 || input>99999){
                                    Log.d("","密碼須為5位數");
                                    inputPasswd.setText("");
                                    new AlertDialog.Builder(setting_backup_passwd.this)
                                            .setTitle("設定失敗!密碼須為5位數!")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {}
                                            }).show();
                                }else{
                                    mode=1;
                                    app_state.backup_passwd=input;
                                    app_state.hasBackup=true;
                                    Log.d("","設定成功");

//                                    info.setText("你已經設定過備用密碼了，若要修改備用密碼，請先輸入原密碼");
                                    new AlertDialog.Builder(setting_backup_passwd.this)
                                            .setTitle("設定成功")
                                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setClass(setting_backup_passwd.this ,setting_passwd.class);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                }
                            }else if(mode==1){
                                int input=Integer.parseInt(inputPasswd.getText().toString());
                                if(input==app_state.backup_passwd){
                                    Log.d("","正確密碼");
                                    info.setText("密碼正確，請輸入新的密碼");
                                    inputPasswd.setText("");
                                    mode=2;
                                }else{
                                    Log.d("","wrong");
                                    info.setText("密碼錯誤");
                                    inputPasswd.setText("");
                                }
                            }
                            return true; // consume.
                        }
                    }

                    return false; // pass on to other listeners.
                }
        );

    }
}