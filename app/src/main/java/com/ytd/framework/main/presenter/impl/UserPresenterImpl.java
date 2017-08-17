package com.ytd.framework.main.presenter.impl;

import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IUserPresenter;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class UserPresenterImpl implements IUserPresenter {


    @Override
    public void save(UserBean bean) {
        bean.save();
    }

    @Override
    public UserBean findLoginUser() {
        return DataSupport.findLast(UserBean.class);
    }
}
