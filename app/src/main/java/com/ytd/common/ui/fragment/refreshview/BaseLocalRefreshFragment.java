package com.ytd.common.ui.fragment.refreshview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.tlf.basic.refreshview.header.OnDefaultRefreshListener;
import com.tlf.basic.refreshview.header.PtrClassicFrameLayout;
import com.tlf.basic.refreshview.header.PtrFrameLayout;
import com.tlf.basic.utils.ViewFindUtils;
import com.ytd.common.base.refreshview.presenter.ILocalRefreshInConfiguration;
import com.ytd.common.base.refreshview.presenter.ILocalRefreshInPresenter;
import com.ytd.common.base.refreshview.presenter.impl.LocalRefreshPresenter;
import com.ytd.common.base.refreshview.ui.RefreshView;
import com.ytd.framework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecylerView 和 AbsListView 列表刷新基类，继承基本
 * Created by ytd on 16/8/18.
 */
public abstract class BaseLocalRefreshFragment extends Fragment implements RefreshView, ILocalRefreshInConfiguration {

    protected PtrClassicFrameLayout mPtrLayout;//在布局里id名字必为R.id.ptr_layout 建议引用ids.xml里定义的id
    protected FrameLayout mFlEmptyView;//在布局里id名字必为fl_empty_view 建议引用ids.xml里定义的id
    protected ILocalRefreshInPresenter presenter;
    protected List mRefreshList;


    protected void supperInit(Context mContext) {
        presenter = new LocalRefreshPresenter(mContext, this, this);
        mRefreshList = new ArrayList<>();
        mFlEmptyView = ViewFindUtils.find(setPtrRootLayout(), R.id.fl_empty_view);
        mPtrLayout = ViewFindUtils.find(setPtrRootLayout(), R.id.ptr_layout);
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.requestPageData(true);
            }
        });
        mPtrLayout.autoRefresh();
    }




    public List getmRefreshList() {
        return mRefreshList;
    }

    /**
     * 上拉刷新
     */
    protected void requestLoadMore() {
        presenter.requestPageData(false);
    }

    @Override
    public PtrClassicFrameLayout getRefreshPtrLayoutView() {
        return mPtrLayout;
    }

    @Override
    public FrameLayout getRefreshEmptyView() {
        return mFlEmptyView;
    }


    public abstract View setPtrRootLayout();

}
