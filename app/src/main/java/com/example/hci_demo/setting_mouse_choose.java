package com.example.hci_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class setting_mouse_choose extends AppCompatActivity {
    private setting_state app_state=MainActivity.app_state;

    private ToggleButton[] TBArray=new ToggleButton[6];
    private Button buttonSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_mouse_choose);

        ImageButton returnBtn=findViewById(R.id.mouse_choose_returnbtn);
        returnBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(setting_mouse_choose.this , new_menu.class);
                startActivity(intent);
            }
        });

        addListenerOnButtonClick();
    }

    private void addListenerOnButtonClick() {
        TBArray[0]=(ToggleButton)findViewById(R.id.toggleButton0);
        TBArray[1]=(ToggleButton)findViewById(R.id.toggleButton1);
        TBArray[2]=(ToggleButton)findViewById(R.id.toggleButton2);
        TBArray[3]=(ToggleButton)findViewById(R.id.toggleButton3);
        TBArray[4]=(ToggleButton)findViewById(R.id.toggleButton4);
        TBArray[5]=(ToggleButton)findViewById(R.id.toggleButton5);
//        TBArray[6]=(ToggleButton)findViewById(R.id.toggleButton7);
//        TBArray[7]=(ToggleButton)findViewById(R.id.toggleButton8);
//        TBArray[8]=(ToggleButton)findViewById(R.id.toggleButton9);
//        TBArray[9]=(ToggleButton)findViewById(R.id.toggleButton10);
//        TBArray[10]=(ToggleButton)findViewById(R.id.toggleButton11);
//        TBArray[11]=(ToggleButton)findViewById(R.id.toggleButton12);
//        TBArray[12]=(ToggleButton)findViewById(R.id.toggleButton13);
//        TBArray[13]=(ToggleButton)findViewById(R.id.toggleButton14);
//        TBArray[14]=(ToggleButton)findViewById(R.id.toggleButton15);
//        TBArray[15]=(ToggleButton)findViewById(R.id.toggleButton16);


        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                StringBuilder result = new StringBuilder();
                for(int i=0;i<6;i++){
                    app_state.random_mouse[i]=TBArray[i].isChecked();
                    result.append("\nTB").append(i).append(app_state.random_mouse[i]);
                }

                //Displaying the message in toast
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
