package com.ytd.framework.equipment.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ytd.support.utils.ResUtils;

/**
 * Created by tanlifei on 16/5/20.
 */
public abstract class SlidingTabStripPagerAdapter extends FragmentPagerAdapter {


    private String[] tabTitles;

    public SlidingTabStripPagerAdapter(FragmentManager fm, int arrayResId) {
        super(fm);
        this.tabTitles = ResUtils.getStringArray(arrayResId);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }


}
