package com.ytd.framework.equipment.ui.activity;

import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.property_search_activity)
public class PropertySearchActivity extends BaseActionBarActivity {

    public static final String TAG = PropertySearchActivity.class.getSimpleName();


    @AfterViews
    void init() {
        initActionBar();
    }


}
