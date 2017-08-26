package com.ytd.framework.main.presenter;

import com.ytd.framework.main.bean.UserBean;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IUserPresenter {

    void save(UserBean bean);//保存数据

    UserBean findLoginUser(UserBean bean);

}
