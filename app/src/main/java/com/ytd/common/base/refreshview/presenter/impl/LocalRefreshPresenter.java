package com.ytd.common.base.refreshview.presenter.impl;

import android.content.Context;

import com.tlf.basic.refreshview.more.GridViewFinal;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.RecyclerViewFinal;
import com.tlf.basic.refreshview.more.ScrollViewFinal;
import com.tlf.basic.utils.CountDownTimer;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.base.refreshview.presenter.ILocalRefreshInConfiguration;
import com.ytd.common.base.refreshview.presenter.ILocalRefreshInPresenter;
import com.ytd.common.base.refreshview.ui.EmptyView;
import com.ytd.common.base.refreshview.ui.RefreshView;
import com.ytd.framework.R;
import com.ytd.support.constants.fixed.JsonConstants;

import java.util.List;

/**
 * Created by ytd on 16/7/28.
 */
public class LocalRefreshPresenter implements ILocalRefreshInPresenter {

    protected Context mContext;
    protected RefreshView refreshView;
    protected int page = 1;
    private ILocalRefreshInConfiguration configuration;


    public LocalRefreshPresenter(Context mContext, RefreshView refreshView, ILocalRefreshInConfiguration configuration) {
        this.mContext = mContext;
        this.refreshView = refreshView;
        this.configuration = configuration;
        page = 1;
    }


    @Override
    public void requestPageData(boolean fromStart) {
        if (fromStart) {
            page = 1;
        }
        startRequest(true, fromStart);
    }


    /**
     * 开始加载
     *
     * @param isPage    是否分页，ture 分页， false 不分页
     * @param fromStart 上拉还是下拉， true 为下拉 false 为加载更多， 不分页时为true
     */
    public final long DELAYED = 500;

    public void startRequest(final boolean isPage, final boolean fromStart) {
        List newGameResponse = null;
        try {
            doBefore();
            newGameResponse = doResponse(isPage);
        } catch (Exception e) {
            doError(isPage, fromStart);
        } finally {
            final List finalNewGameResponse = newGameResponse;
            new CountDownTimer(DELAYED, DELAYED) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    doAfter(finalNewGameResponse, isPage);
                }
            }.start();

        }
    }


    public void doBefore() {
        if (ListUtils.isEmpty(configuration.getmRefreshList())) {
            EmptyView.showLoading(refreshView.getRefreshEmptyView());
        }
    }


    public List doResponse(boolean isPage) {
        List newGameResponse = null;
        if (isPage) {//分页处理
            if (page == 1) {
                configuration.getmRefreshList().clear();
            }
            newGameResponse = configuration.localSQLFindLimit(isPage, page);
            page = page + 1;
            hasLoadMore(newGameResponse);
        } else {
            configuration.getmRefreshList().clear();
            try {
                newGameResponse = configuration.localSQLFindLimit(isPage, page);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!ListUtils.isEmpty(newGameResponse)) {
            configuration.getmRefreshList().addAll(newGameResponse);
        }
        return newGameResponse;
    }


    public void doError(boolean isPage, boolean fromStart) {
        if (isPage) {//分页处理
            if (fromStart) {//下拉
                errorPrompt();
            } else {//更多
                showFailUI();
            }
        } else {//不分页
            errorPrompt();
        }
    }


    public void doAfter(List newGameResponse, boolean isPage) {
        try {
            if (isPage) {//分页处理
                if ((page - 1) <= 1) {//下拉刷新时
                    refreshView.getRefreshPtrLayoutView().onRefreshComplete();
                } else {//加载更多时
                    hasLoadMore(newGameResponse);
                    loadMoreComplete();
                }
            } else {//不分页
                refreshView.getRefreshPtrLayoutView().onRefreshComplete();
            }
            if (ListUtils.isEmpty(configuration.getmRefreshList())) {//没有数据
                EmptyView.showNoDataEmpty(refreshView.getRefreshEmptyView());
            }
            configuration.after();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 异常提示
     */
    private void errorPrompt() {
        try {
            if (ListUtils.isEmpty(configuration.getmRefreshList()) && !NetUtils.isConnected(mContext)) {//没有数据,且没有网络 提示布局
                EmptyView.showNetErrorEmpty(refreshView.getRefreshEmptyView());
            } else if (ListUtils.isEmpty(configuration.getmRefreshList()) && NetUtils.isConnected(mContext)) {//没有数据,有网络
                EmptyView.showNoDataEmpty(refreshView.getRefreshEmptyView());
            } else if (!ListUtils.isEmpty(configuration.getmRefreshList()) && !NetUtils.isConnected(mContext)) {//有数据,且没有网络
                ToastUtils.show(mContext, R.string.common_net_error);
            } else {//有数据,有网络
                ToastUtils.show(mContext, R.string.common_data_error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载更多完成
     */
    private void loadMoreComplete() {
        try {
            if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
                ((ListViewFinal) refreshView.getDataView()).onLoadMoreComplete();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
                ((GridViewFinal) refreshView.getDataView()).onLoadMoreComplete();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
                ((RecyclerViewFinal) refreshView.getDataView()).onLoadMoreComplete();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
                ((ScrollViewFinal) refreshView.getDataView()).onLoadMoreComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载更多时是否最后一页，显示隐藏加载更多view
     *
     * @param newGameResponse
     */
    private void hasLoadMore(List newGameResponse) {
        try {
            if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
                ((ListViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
                ((GridViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
                ((RecyclerViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
                ((ScrollViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载更多时，加载失败要显示的view
     */
    private void showFailUI() {
        try {
            if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
                ((ListViewFinal) refreshView.getDataView()).showFailUI();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
                ((GridViewFinal) refreshView.getDataView()).showFailUI();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
                ((RecyclerViewFinal) refreshView.getDataView()).showFailUI();
            } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
                ((ScrollViewFinal) refreshView.getDataView()).showFailUI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
