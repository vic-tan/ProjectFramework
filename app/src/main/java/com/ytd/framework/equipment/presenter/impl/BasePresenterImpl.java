package com.ytd.framework.equipment.presenter.impl;

import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.ui.BaseApplication;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class BasePresenterImpl {

    public String getLoginName() {
        if (null != BaseApplication.userBean) {
            return BaseApplication.userBean.getLoginName();
        } else {
            return "";
        }
    }

    public UserBean getUserBean() {
        if (null != BaseApplication.userBean) {
            return BaseApplication.userBean;
        }
        return null;
    }
}
