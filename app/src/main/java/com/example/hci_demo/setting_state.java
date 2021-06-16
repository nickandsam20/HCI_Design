package com.example.hci_demo;

import java.util.ArrayList;

public class setting_state {
    int rest_time,mode,backup_passwd;
    boolean hasBackup,hasGesture;
    boolean[] random_mouse;
    ArrayList<Integer> gesture_passwd;
    setting_state(){
        rest_time=0;
        mode=0;
        backup_passwd=0;
        hasBackup=false;
        hasGesture=false;
        random_mouse=new boolean[16];
        gesture_passwd=new ArrayList<Integer>();
    }
}
