package com.ytd.framework.equipment.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tlf.basic.utils.StartActUtils;
import com.ytd.framework.R;
import com.ytd.framework.equipment.ui.fragment.AddPropertyFragment;
import com.ytd.framework.equipment.ui.fragment.AddPropertyFragment_;
import com.ytd.framework.equipment.ui.fragment.ListPropertyFragment;
import com.ytd.framework.equipment.ui.fragment.ListPropertyFragment_;
import com.ytd.framework.equipment.ui.navigator.BaseSlidingTabStripActivity;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;
import com.ytd.uikit.slider.IndicatorWrapPagerSlider;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.property_activity)
public class PropertyActivity extends BaseSlidingTabStripActivity {

    public static final String TAG = PropertyActivity.class.getSimpleName();

    @ViewById(R.id.tabs)
    IndicatorWrapPagerSlider mTabs;
    @ViewById(R.id.pager)
    ViewPager mPager;

    private ListPropertyFragment listFragment;
    private AddPropertyFragment addFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @AfterViews
    void init() {
        initActionBar();
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                if (viewTag == ActionBarOptViewTagLevel.LEFT_ICON_DRAWABLE) {
                    StartActUtils.start(mContext, PropertySearchActivity_.class);
                } else if (viewTag == ActionBarOptViewTagLevel.RIGHT_ICON_DRAWABLE) {
                    StartActUtils.start(mContext,
                            CameraScanActivity.class);
                }
            }
        });
        initPager(mTabs,mPager);
    }


    @Override
    public Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
                if (listFragment == null) {
                    listFragment =  new ListPropertyFragment_();

                }
                return listFragment;
            case 1:
                if (addFragment == null) {
                    addFragment =new AddPropertyFragment_();

                }
                return addFragment;

            default:
                if (listFragment == null) {
                    listFragment =new ListPropertyFragment_();

                }
                return listFragment;

        }
    }

    public IndicatorWrapPagerSlider getmTabs() {
        return mTabs;
    }

    @Override
    public int getStringArrayResId() {
        return R.array.sliding_tab_strip_pager;
    }


}
