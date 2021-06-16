package com.example.hci_demo;

public class setting_state {
    int rest_time,mode,backup_passwd;
    boolean hasBackup;
    boolean[] random_mouse;
    setting_state(){
        rest_time=0;
        mode=0;
        backup_passwd=0;
        hasBackup=false;
        random_mouse=new boolean[16];
    }
}
