package com.ytd.common.base.refreshview.presenter;


/**
 * Created by ytd on 16/7/28.
 */
public interface ILocalRefreshInPresenter {


    /**
     * 分页加载数据
     * @param fromStart true 下拉或自动刷新标识
     */
    void requestPageData(boolean fromStart);


}
