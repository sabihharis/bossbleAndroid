package com.bossble.bossble.Adapter;

/*
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
*/

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Fawad on 5/8/2020.
 */


public class CustomViewpager extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;

    public CustomViewpager(FragmentManager fm, ArrayList<Fragment>fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
