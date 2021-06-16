package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class new_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);
        DiscreteScrollView scrollView = findViewById(R.id.picker);
        ArrayList<menu_item_source> sourceList=new ArrayList<menu_item_source>();

        scrollView.setSlideOnFling(true);
        sourceList.add(new menu_item_source(R.drawable.on,new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(new_menu.this ,MainActivity.class);
                startActivity(intent);
            }
        }));
        sourceList.add(new menu_item_source(R.drawable.resttime,new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(new_menu.this ,setting_resttime.class);
                startActivity(intent);
            }
        }));
        sourceList.add(new menu_item_source(R.drawable.difficulity,new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(new_menu.this ,setting_difficulity.class);
                startActivity(intent);
            }
        }));
        sourceList.add(new menu_item_source(R.drawable.password,new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(new_menu.this ,setting_passwd.class);
                startActivity(intent);
            }
        }));
        sourceList.add(new menu_item_source(R.drawable.choose_mouse,new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 寫要做的事...
                Log.d("","click");
                Intent intent = new Intent();
                intent.setClass(new_menu.this ,setting_mouse_choose.class);
                startActivity(intent);
            }
        }));

        scrollView.setAdapter(new myDiscreteAdapter(sourceList));
    }
}