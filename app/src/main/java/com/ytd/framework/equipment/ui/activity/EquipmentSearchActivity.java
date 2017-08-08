package com.ytd.framework.equipment.ui.activity;

import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_search_activity)
public class EquipmentSearchActivity extends BaseActionBarActivity {

    public static final String TAG = EquipmentSearchActivity.class.getSimpleName();


    @AfterViews
    void init() {
        initActionBar();
    }


}
