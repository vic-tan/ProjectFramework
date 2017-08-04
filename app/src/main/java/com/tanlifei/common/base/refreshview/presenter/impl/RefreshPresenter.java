package com.tanlifei.common.base.refreshview.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.tanlifei.common.base.refreshview.presenter.IRefreshInConfiguration;
import com.tanlifei.common.base.refreshview.presenter.IRefreshInPresenter;
import com.tanlifei.common.base.refreshview.ui.EmptyView;
import com.tanlifei.common.base.refreshview.ui.RefreshView;
import com.tanlifei.common.bean.BaseJson;
import com.tanlifei.common.bean.BasePageListBean;
import com.tanlifei.framework.R;
import com.tanlifei.support.constants.fixed.JsonConstants;
import com.tanlifei.support.http.HttpListener;
import com.tanlifei.support.http.ProcessCallback;
import com.tanlifei.support.utils.GsonJsonUtils;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.refreshview.more.GridViewFinal;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.RecyclerViewFinal;
import com.tlf.basic.refreshview.more.ScrollViewFinal;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by tanlifei on 16/7/28.
 */
public class RefreshPresenter implements IRefreshInPresenter {

    protected Context mContext;
    protected RefreshView refreshView;
    protected int page = 1;
    private IRefreshInConfiguration configuration;


    public RefreshPresenter(Context mContext, RefreshView refreshView, IRefreshInConfiguration configuration) {
        this.mContext = mContext;
        this.refreshView = refreshView;
        this.configuration = configuration;
        page = 1;
    }


    @Override
    public void requestPageData(String url, Map<String, String> map, final boolean fromStart) {
        if (fromStart) {
            page = 1;
        }
        //TODO 要做处理
        map.put("json", "{\n" +
                "    \"sid\": \"ipeiban2016\",\n" +
                "    \"pageNumber\": " + page + ",\n" +
                "    \"pageSize\": 10\n" +
                "}");
        startRequest(url, map, true, fromStart);
    }

    @Override
    public void requestData(String url, Map<String, String> map) {
        startRequest(url, map, false, true);
    }


    /**
     * 开始加载
     *
     * @param url
     * @param map
     * @param isPage    是否分页，ture 分页， false 不分页
     * @param fromStart 上拉还是下拉， true 为下拉 false 为加载更多， 不分页时为true
     */
    public void startRequest(String url, Map<String, String> map, final boolean isPage, final boolean fromStart) {
        OkHttpUtils.post().url(url).params(map).build().execute(new ProcessCallback(mContext, new HttpListener() {

            @Override
            public void onBefore(Request request) {
                doBefore();
            }

            @Override
            public void onCusResponse(BaseJson response) {
                doResponse(response, isPage);
            }

            @Override
            public void onError(Call call, Exception e) {
                doError(call, e, isPage, fromStart);
            }

            @Override
            public void onAfter() {
                doAfter(isPage);
            }
        }));
    }


    public void doBefore() {
        if (ListUtils.isEmpty(configuration.getmRefreshList())) {
            EmptyView.showLoading(refreshView.getRefreshEmptyView());
        }
    }


    public void doResponse(BaseJson baseJson, boolean isPage) {
        List newGameResponse = null;
        if (isPage) {//分页处理
            if (page == 1) {
                configuration.getmRefreshList().clear();
            }
            page = page + 1;
            BasePageListBean basePageListBean = new Gson().fromJson(new Gson().toJson(baseJson.getData()), BasePageListBean.class);
            try {
                newGameResponse = GsonJsonUtils.fromJsonArray(new Gson().toJson(basePageListBean.getList()), configuration.parseClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            hasLoadMore(newGameResponse);
        } else {
            configuration.getmRefreshList().clear();
            try {
                newGameResponse = GsonJsonUtils.fromJsonArray(new Gson().toJson(baseJson.getData()), configuration.parseClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (!ListUtils.isEmpty(newGameResponse)) {
            configuration.getmRefreshList().addAll(newGameResponse);
        }
    }


    public void doError(Call call, Exception e, boolean isPage, boolean fromStart) {
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


    public void doAfter(boolean isPage) {
        if (isPage) {//分页处理
            if ((page - 1) <= 1) {//下拉刷新时
                refreshView.getRefreshPtrLayoutView().onRefreshComplete();
            } else {//加载更多时
                loadMoreComplete();
            }
        } else {//不分页
            refreshView.getRefreshPtrLayoutView().onRefreshComplete();
        }
        if (ListUtils.isEmpty(configuration.getmRefreshList())) {//没有数据
            EmptyView.showNoDataEmpty(refreshView.getRefreshEmptyView());
        }
        configuration.after();
    }


    /**
     * 异常提示
     */
    private void errorPrompt() {
        if (ListUtils.isEmpty(configuration.getmRefreshList()) && !NetUtils.isConnected(mContext)) {//没有数据,且没有网络 提示布局
            EmptyView.showNetErrorEmpty(refreshView.getRefreshEmptyView());
        } else if (ListUtils.isEmpty(configuration.getmRefreshList()) && NetUtils.isConnected(mContext)) {//没有数据,有网络
            EmptyView.showNoDataEmpty(refreshView.getRefreshEmptyView());
        } else if (!ListUtils.isEmpty(configuration.getmRefreshList()) && !NetUtils.isConnected(mContext)) {//有数据,且没有网络
            ToastUtils.show(mContext, R.string.common_net_error);
        } else {//有数据,有网络
            ToastUtils.show(mContext, R.string.common_data_error);
        }
    }

    /**
     * 加载更多完成
     */
    private void loadMoreComplete() {
        if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
            ((ListViewFinal) refreshView.getDataView()).onLoadMoreComplete();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
            ((GridViewFinal) refreshView.getDataView()).onLoadMoreComplete();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
            ((RecyclerViewFinal) refreshView.getDataView()).onLoadMoreComplete();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
            ((ScrollViewFinal) refreshView.getDataView()).onLoadMoreComplete();
        }
    }

    /**
     * 加载更多时是否最后一页，显示隐藏加载更多view
     *
     * @param newGameResponse
     */
    private void hasLoadMore(List newGameResponse) {
        if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
            ((ListViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
            ((GridViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
            ((RecyclerViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
            ((ScrollViewFinal) refreshView.getDataView()).setHasLoadMore(newGameResponse.size() < JsonConstants.PAGE_SIZE ? false : true);
        }
    }


    /**
     * 加载更多时，加载失败要显示的view
     */
    private void showFailUI() {
        if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ListViewFinal) {
            ((ListViewFinal) refreshView.getDataView()).showFailUI();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof GridViewFinal) {
            ((GridViewFinal) refreshView.getDataView()).showFailUI();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof RecyclerViewFinal) {
            ((RecyclerViewFinal) refreshView.getDataView()).showFailUI();
        } else if (null != refreshView.getDataView() && refreshView.getDataView() instanceof ScrollViewFinal) {
            ((ScrollViewFinal) refreshView.getDataView()).showFailUI();
        }
    }

}
