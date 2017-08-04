package com.tanlifei.common.ui.activity.refreshview;


import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;

/**
 * AbsListView 列表刷新基类，继承基本
 * Created by tanlifei on 16/8/18.
 */
public abstract class BaseAbsRefreshActivity extends BaseRefreshActivity {


    private AbsCommonAdapter mRefreshAdapter;

    protected void supperInit() {
        super.supperInit();
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
