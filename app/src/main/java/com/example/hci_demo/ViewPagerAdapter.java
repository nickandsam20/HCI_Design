package com.example.hci_demo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hci_demo.fragment.menu_page_choose_mouse;
import com.example.hci_demo.fragment.menu_page_difficulity;
import com.example.hci_demo.fragment.menu_page_on;
import com.example.hci_demo.fragment.menu_page_password;
import com.example.hci_demo.fragment.menu_page_resttime;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(FragmentActivity fragmentActivity){super(fragmentActivity);}

    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new menu_page_on();
            case 1:
                return new menu_page_resttime();
            case 2:
                return new menu_page_difficulity();
            case 3:
                return new menu_page_password();
            default:
                 return new menu_page_choose_mouse();
        }
    }

    public int getItemCount(){return 5;}
}
