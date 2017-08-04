package com.ytd.framework.main.presenter;

import com.ytd.framework.main.bean.AppUpdateBean;

/**
 *  app 版本升级
 * Created by ytd on 16/5/12.
 */
public interface IAppUpdatePresenter {
    AppUpdateBean checkAppUpdate();//检测应用升级
    void appDownload(AppUpdateBean updateBean);//app下载
}
