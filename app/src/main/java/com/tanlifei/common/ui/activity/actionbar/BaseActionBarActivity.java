package com.tanlifei.common.ui.activity.actionbar;


import android.os.Bundle;
import android.view.View;

import com.tanlifei.common.ui.activity.BaseActivity;
import com.tanlifei.framework.R;
import com.tanlifei.uikit.actionbar.ActionBarView;
import com.tanlifei.uikit.actionbar.OnBackClickListener;
import com.tlf.basic.utils.ViewFindUtils;

/**
 * Created by tanlifei on 15/12/17.
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
