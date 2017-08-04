package com.ytd.common.base.refreshview.ui;

import android.view.View;
import android.widget.FrameLayout;

import com.tlf.basic.refreshview.header.PtrClassicFrameLayout;


/**
 * Created by ytd on 16/5/12.
 */
public interface RefreshView {

    View getDataView();
    PtrClassicFrameLayout getRefreshPtrLayoutView();
    FrameLayout getRefreshEmptyView();


}
