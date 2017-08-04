package com.tanlifei.common.ui.activity.refreshview;

import android.view.View;
import android.widget.FrameLayout;

import com.tanlifei.common.base.refreshview.presenter.IRefreshInConfiguration;
import com.tanlifei.common.base.refreshview.presenter.IRefreshInPresenter;
import com.tanlifei.common.base.refreshview.presenter.impl.RefreshPresenter;
import com.tanlifei.common.base.refreshview.ui.RefreshView;
import com.tanlifei.common.bean.BaseJson;
import com.tanlifei.common.bean.PageBean;
import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tlf.basic.refreshview.header.OnDefaultRefreshListener;
import com.tlf.basic.refreshview.header.PtrClassicFrameLayout;
import com.tlf.basic.refreshview.header.PtrFrameLayout;
import com.tlf.basic.utils.ViewFindUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RecylerView 和 AbsListView 列表刷新基类，继承基本
 * Created by tanlifei on 16/8/18.
 */
public abstract class BaseRefreshActivity extends BaseActionBarActivity implements RefreshView, IRefreshInConfiguration {

    protected PtrClassicFrameLayout mPtrLayout;//在布局里id名字必为R.id.ptr_layout 建议引用ids.xml里定义的id
    protected FrameLayout mFlEmptyView;//在布局里id名字必为fl_empty_view 建议引用ids.xml里定义的id
    protected IRefreshInPresenter presenter;
    protected List<?> mRefreshList;


    protected void supperInit() {
        initActionBar();
        presenter = new RefreshPresenter(mContext, this, this);
        mRefreshList = new ArrayList<>();
        mFlEmptyView = ViewFindUtils.find(setPtrRootLayout(), R.id.fl_empty_view);
        mPtrLayout = ViewFindUtils.find(setPtrRootLayout(), R.id.ptr_layout);
        mPtrLayout.setLastUpdateTimeRelateObject(this);
        mPtrLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.requestPageData(requestUrl(), requestParams(), true);
            }
        });
        mPtrLayout.autoRefresh();
    }

    @Override
    public void customParseJson(BaseJson baseJson, PageBean pageBean) {

    }


    public List getmRefreshList() {
        return mRefreshList;
    }

    /**
     * 上拉刷新
     */
    protected void requestLoadMore() {
        presenter.requestPageData(requestUrl(), requestParams(), false);
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

    public abstract String requestUrl();

    public abstract Map<String, String> requestParams();

}
