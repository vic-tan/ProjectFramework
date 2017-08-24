package com.ytd.framework.main.presenter;

import com.ytd.framework.main.bean.ConfigBean;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IConfigPresenter {

    void save(ConfigBean bean);//保存数据

    ConfigBean find();

}
