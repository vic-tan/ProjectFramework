package com.ytd.framework.main.adapter;

import android.app.Fragment;

import com.ytd.framework.main.ui.fragment.HomeFragment;
import com.ytd.framework.main.ui.fragment.HomeFragment_;
import com.ytd.framework.main.ui.fragment.OwnFragment;
import com.ytd.framework.main.ui.fragment.OwnFragment_;
import com.ytd.framework.main.ui.navigator.FragmentNavigatorAdapter;


/**
 * 首页底部适配器
 * Created by aspsine on 16/3/31.
 */
public class HomeNavigatorFragmentAdapter implements FragmentNavigatorAdapter {


    @Override
    public Fragment onCreateFragment(int position) {
        if (position == 0) {
            return new HomeFragment_();
        } else if (position == 1) {
            return new OwnFragment_();
        }
        return new HomeFragment_();
    }


    @Override
    public String getTag(int position) {
        if (position == 0) {
            return HomeFragment.TAG;
        } else if (position == 1) {
            return OwnFragment.TAG;
        }
        return HomeFragment.TAG;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
