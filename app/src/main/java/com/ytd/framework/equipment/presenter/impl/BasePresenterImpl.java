package com.ytd.framework.equipment.presenter.impl;

import com.tlf.basic.utils.StringUtils;
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

    public String empty(String str) {
        if (StringUtils.isEmpty(str)) {
            return "暂无";
        } else if (null == str) {
            return "暂无";
        } else if ("null" == str) {
            return "暂无";
        } else {
            return str;
        }

    }

    public String empty(String str,String defult) {
        if (StringUtils.isEmpty(str)) {
            return defult;
        } else if (null == str) {
            return defult;
        } else if ("null" == str) {
            return defult;
        } else {
            return str;
        }

    }
}
