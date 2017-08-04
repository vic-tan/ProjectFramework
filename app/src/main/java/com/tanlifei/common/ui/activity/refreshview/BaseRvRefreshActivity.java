package com.tanlifei.common.ui.activity.refreshview;


import com.tlf.basic.base.adapter.recycler.RvCommonAdapter;

/**
 * RecylerView 列表刷新基类，继承基本
 * Created by tanlifei on 16/8/18.
 */
public abstract class BaseRvRefreshActivity extends BaseRefreshActivity {

    private RvCommonAdapter mRefreshAdapter;

    protected void supperInit() {
        super.supperInit();
        getmRefreshAdapter();
    }

    @Override
    public void after() {
        mRefreshAdapter.notifyDataSetChanged();
    }

    public RvCommonAdapter getmRefreshAdapter() {
        if (null == mRefreshAdapter) {
            mRefreshAdapter = setRefreshAdapter();
        }
        return mRefreshAdapter;
    }

    public abstract RvCommonAdapter setRefreshAdapter();


}
