package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ViewPager2 viewPager2=findViewById(R.id.view_pager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}