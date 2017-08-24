package com.ytd.framework.main.presenter.impl;

import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IConfigPresenter;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class ConfigPresenterImpl implements IConfigPresenter {


    @Override
    public void save(ConfigBean bean) {
        bean.save();
    }

    @Override
    public ConfigBean find() {
        return DataSupport.findLast(ConfigBean.class);
    }
}
