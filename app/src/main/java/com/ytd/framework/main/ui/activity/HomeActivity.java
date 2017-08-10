package com.ytd.framework.main.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.ui.activity.EquipmentScanResultActivity_;
import com.ytd.framework.main.adapter.HomeNavigatorFragmentAdapter;
import com.ytd.framework.main.ui.navigator.FragmentNavigator;
import com.ytd.framework.main.ui.view.HomeNavigatorView;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.main_activity_home)
public class HomeActivity extends BaseActionBarActivity implements HomeNavigatorView.OnBottomNavigatorViewItemClickListener {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private static final int DEFAULT_POSITION = 0;
    @ViewById
    HomeNavigatorView bottomNavigatorView;
    private FragmentNavigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNavigator = new FragmentNavigator(getFragmentManager(), new HomeNavigatorFragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
//        startService(new Intent(this, CheckAppUpdateService.class));
        initActionBar();
        actionBarView.setActionbarTitle("深圳市源泰达医院");
        actionBarView.setActionbarBackDimiss(true);
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                StartActUtils.start(mContext,
                        EquipmentScanResultActivity_.class);
            }
        });
        if (bottomNavigatorView != null) {
            bottomNavigatorView.setOnBottomNavigatorViewItemClickListener(this);
        }
        setCurrentTab(mNavigator.getCurrentPosition());

    }


    @Override
    public void onBottomNavigatorViewItemClick(int position, View view) {
        setCurrentTab(position);
    }

    public void setCurrentTab(int position) {
        mNavigator.showFragment(position);
        bottomNavigatorView.select(position);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }


}
