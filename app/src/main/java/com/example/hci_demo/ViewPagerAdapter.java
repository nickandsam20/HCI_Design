package com.example.hci_demo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(FragmentActivity fragmentActivity){super(fragmentActivity);}

    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return new page1();
            case 1:
                return new page2();
            default:
                return new page3();
        }
    }

    public int getItemCount(){return 3;}
}
