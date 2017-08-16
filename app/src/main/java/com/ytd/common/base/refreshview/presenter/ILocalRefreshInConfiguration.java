package com.ytd.common.base.refreshview.presenter;

import java.util.List;

/**
 * Created by ytd on 16/7/28.
 */
public interface ILocalRefreshInConfiguration {


    /**
     * @param isPage   是否分页
     * @param currPage /当前请求页数
     * @return
     */
    List localSQLFindLimit(boolean isPage, int currPage);


    /**
     * 所有请求完成成最的调用的方法
     */
    void after();


    /**
     * 设置装载容器list
     */
    List getmRefreshList();
}
