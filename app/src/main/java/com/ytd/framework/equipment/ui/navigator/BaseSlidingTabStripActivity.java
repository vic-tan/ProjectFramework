package com.ytd.framework.equipment.ui.navigator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.equipment.adapter.SlidingTabStripPagerAdapter;
import com.ytd.uikit.slider.IndicatorWrapPagerSlider;

/**
 * Created by tanlifei on 16/5/20.
 */
public abstract class BaseSlidingTabStripActivity extends BaseScannerReceiverActivity {
    protected int pageMargin;
    protected SlidingTabStripPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pagerAdapter = new SlidingTabStripPagerAdapter(getSupportFragmentManager(), getStringArrayResId()) {
            @Override
            public Fragment getItem(int position) {
                return getFragmentItem(position);
            }
        };
        super.onCreate(savedInstanceState);
    }

    protected void initPager(IndicatorWrapPagerSlider mTabs, ViewPager mPager) {
        mPager.setAdapter(pagerAdapter);
        mPager.setPageMargin(pageMargin);
        mTabs.setViewPager(mPager);
        mPager.setOffscreenPageLimit(4);
//        mPager.setOnTouchListener(new View.OnTouchListener() {//禁止viewpage滑动
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }

    public abstract int getStringArrayResId();

    public abstract Fragment getFragmentItem(int position);

}
