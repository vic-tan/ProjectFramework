package com.tanlifei.common.base.refreshview.presenter;


import java.util.Map;

/**
 * Created by tanlifei on 16/7/28.
 */
public interface IRefreshInPresenter {


    /**
     * 分页加载数据
     * @param url
     * @param map
     * @param fromStart true 下拉或自动刷新标识
     */
    void requestPageData(String url, Map<String, String> map,boolean fromStart);

    /**
     * 不分显示数据
     * @param url
     * @param map
     */
    void requestData(String url,Map<String, String> map);
}
