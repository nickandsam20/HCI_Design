package com.example.hci_demo;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

public class menu_item_source {
    ImageButton.OnClickListener listener;
    int drawable;
    menu_item_source(int d,ImageButton.OnClickListener l){
        listener=l;
        drawable=d;
    }
    int getDrawable(){
        return drawable;
    }
    ImageButton.OnClickListener getListener(){
        return  listener;
    }
}
