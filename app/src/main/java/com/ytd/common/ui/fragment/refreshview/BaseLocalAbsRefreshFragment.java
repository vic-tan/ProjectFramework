package com.ytd.common.ui.fragment.refreshview;


import android.content.Context;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;

/**
 * AbsListView 列表刷新基类，继承基本
 * Created by ytd on 16/8/18.
 */
public abstract class BaseLocalAbsRefreshFragment extends BaseLocalRefreshFragment {


    protected AbsCommonAdapter mRefreshAdapter;

    protected void supperInit(Context mContext) {
        super.supperInit(mContext);
        setRefreshAdapter();
    }

    @Override
    public void after() {
        mRefreshAdapter.notifyDataSetChanged();
    }

    public AbsCommonAdapter getmRefreshAdapter() {
        if (null == mRefreshAdapter) {
            mRefreshAdapter = setRefreshAdapter();
        }
        return mRefreshAdapter;
    }

    public abstract AbsCommonAdapter setRefreshAdapter();


}
