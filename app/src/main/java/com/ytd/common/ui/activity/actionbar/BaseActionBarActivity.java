package com.ytd.common.ui.activity.actionbar;


import android.os.Bundle;
import android.view.View;

import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.uikit.actionbar.ActionBarView;
import com.ytd.uikit.actionbar.OnBackClickListener;
import com.tlf.basic.utils.ViewFindUtils;

/**
 * Created by ytd on 15/12/17.
 */
public abstract class BaseActionBarActivity extends BaseActivity {

    protected ActionBarView actionBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void initActionBar() {
        actionBarView = ViewFindUtils.find(this, R.id.actionbar);
        actionBarView.setOnBackClickListener(new OnBackClickListener() {
            @Override
            public void onClick(View v) {
                actionBack();
            }
        });
    }

}
