package com.ytd.framework.main.presenter.impl;

import com.tlf.basic.utils.Logger;
import com.ytd.framework.equipment.presenter.impl.BasePresenterImpl;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.presenter.IEntrepotPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class EntrepotPresenterImpl extends BasePresenterImpl implements IEntrepotPresenter {


    @Override
    public void save(List<EntrepotBean> list) {
        DataSupport.deleteAll(EntrepotBean.class);
        for (EntrepotBean forBean : list) {
            boolean save = forBean.save();
            Logger.i("---------"+save);
        }

    }

    @Override
    public List<EntrepotBean> findAll() {
        return DataSupport.findAll(EntrepotBean.class);
    }


}
