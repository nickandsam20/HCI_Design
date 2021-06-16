package com.example.hci_demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;

public class setting_gesture_passwd extends AppCompatActivity {

    private setting_state app_state=MainActivity.app_state;
    int mode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_gesture_passwd);
        PatternLockView lock=findViewById(R.id.patternLockView);

        lock.disableSecureMode();

        TextView info=findViewById(R.id.gestureSettingInfo);
        if(!app_state.hasGesture){
            mode=0;
            info.setText("你還沒設定過圖形密碼，請在下面畫出你要的圖形密碼");
        }else {
            mode=1;
            info.setText("你已經設定過圖形密碼，若要修改，請先輸入原本的密碼");
        }
        lock.setOnPatternListener(new PatternLockView.OnPatternListener() {

            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(ArrayList<Integer> ids) {

            }

            @Override
            public boolean onComplete(ArrayList<Integer> ids) {
                /*
                 * A return value required
                 * if the pattern is not correct and you'd like change the pattern to error state, return false
                 * otherwise return true
                 */
                if(mode==0 || mode==2){
                    app_state.gesture_passwd.clear();
                    app_state.gesture_passwd.addAll(ids);
                    for(int i=0;i<ids.size();i++){
                        Log.d("",ids.get(i).toString());
                    }
                    app_state.hasGesture=true;

                    new AlertDialog.Builder(setting_gesture_passwd.this)
                            .setTitle("設定成功")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setClass(setting_gesture_passwd.this ,setting_passwd.class);
                                    startActivity(intent);
                                }
                            }).show();
                    return true;

                }else if(mode==1){
                    if(app_state.gesture_passwd.equals(ids)){
                        for(int i=0;i<ids.size();i++){
                            Log.d("",app_state.gesture_passwd.get(i).toString());
                        }
                        mode=2;
                        info.setText("密碼正確，請重新設定新的密碼");
                        return true;
                    }else{
                        for(int i=0;i<app_state.gesture_passwd.size();i++){
                            Log.d("",app_state.gesture_passwd.get(i).toString());
                        }

                        info.setText("密碼錯誤");
                        return false;
                    }
                }
                return isPatternCorrect();
            }

            private boolean isPatternCorrect() {
                return true;
            }
        });
    }
}