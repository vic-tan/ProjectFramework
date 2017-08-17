package com.ytd.framework.equipment.presenter.impl;

import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class BasePresenterImpl {
    IUserPresenter userPresenter;

    public String getLoginName() {
        if (null == userPresenter) {
            userPresenter = new UserPresenterImpl();
        }
        UserBean user = userPresenter.findLoginUser();
        if (null == user) {
            return "";
        }
        return user.getLoginName();
    }

    public UserBean getUserBean() {
        if (null == userPresenter) {
            userPresenter = new UserPresenterImpl();
        }
        UserBean user = userPresenter.findLoginUser();
        if (null == user) {
            return null;
        }
        return user;
    }
}
