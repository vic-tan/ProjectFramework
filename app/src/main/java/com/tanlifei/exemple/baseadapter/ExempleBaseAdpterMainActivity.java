package com.tanlifei.exemple.baseadapter;

import android.os.Bundle;
import android.view.View;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tlf.basic.utils.StartActUtils;

public class ExempleBaseAdpterMainActivity extends BaseActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemple_activity_base_adapter_main);
        initActionBar();
        actionBarView.setActionbarTitle("公用Adpter");
    }


    public void A(View v) {
        StartActUtils.start(mContext, ExempleBaseAdpterMutliItemTypeActivity.class);
    }

    public void B(View v) {
        StartActUtils.start(mContext, ExempleBaseAdapterSingleItemTypeActivity.class);
    }


}
