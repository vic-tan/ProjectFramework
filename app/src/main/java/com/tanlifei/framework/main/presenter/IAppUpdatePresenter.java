package com.tanlifei.framework.main.presenter;

import com.tanlifei.framework.main.bean.AppUpdateBean;

/**
 *  app 版本升级
 * Created by tanlifei on 16/5/12.
 */
public interface IAppUpdatePresenter {
    AppUpdateBean checkAppUpdate();//检测应用升级
    void appDownload(AppUpdateBean updateBean);//app下载
}
