package com.ytd.framework.main.ui.navigator;

import android.app.Fragment;

/**
 * Created by aspsine on 16/3/30.
 */
public interface FragmentNavigatorAdapter {

    public Fragment onCreateFragment(int position);

    public String getTag(int position);

    public int getCount();

}
