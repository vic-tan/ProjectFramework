package com.ytd.framework.main.presenter.impl;

import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.framework.equipment.presenter.impl.BasePresenterImpl;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IUserPresenter;

import java.util.List;

import static com.ytd.framework.main.bean.UserBean.DB_LOGIN_NAME;
import static com.ytd.framework.main.bean.UserBean.STORE_ID;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class UserPresenterImpl extends BasePresenterImpl implements IUserPresenter {


    @Override
    public void save(UserBean bean) {
        UserBean dbUser = findLoginUser(bean);
        if (null != dbUser) {
            dbUser.delete();
        }
        bean.save();
    }

    @Override
    public void replaceUser(UserBean replaceUser) {
        UserBean dbUser = findLoginUser(getUserBean());
        if (null != dbUser) {
            dbUser.delete();
        }
        replaceUser.save();
    }


    @Override
    public UserBean findLoginUser(UserBean bean) {
        if (StringUtils.isEmpty(bean.getStoreId())) {
            List<UserBean> list = where(DB_LOGIN_NAME + " = ? and  pwd = ? ", bean.getLoginName(), bean.getPwd()).find(UserBean.class);
            if (ListUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0);
        } else {
            List<UserBean> list = where(DB_LOGIN_NAME + " = ? and  pwd = ? and " + STORE_ID + " = ? ", bean.getLoginName(), bean.getPwd(), bean.getStoreId()).find(UserBean.class);
            if (ListUtils.isEmpty(list)) {
                return null;
            }
            return list.get(0);
        }
    }


}
