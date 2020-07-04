package com.skinexam.myapplication.ui.dashboard;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skinexam.myapplication.splah.Dash_all;
import com.skinexam.myapplication.splah.Dash_pend;
import com.skinexam.myapplication.splah.Dash_recent;

public class DashPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    int mNoOfTabs;
    public DashPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Dash_recent tab1 =new Dash_recent();
                return tab1;
            case 1:
                Dash_pend tab2 = new Dash_pend();
                return tab2;
            case 2:
                Dash_all tab3 = new Dash_all();
                return tab3;
            default:
                return  null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }

}
