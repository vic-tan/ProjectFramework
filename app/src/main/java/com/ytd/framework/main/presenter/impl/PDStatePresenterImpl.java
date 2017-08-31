package com.ytd.framework.main.presenter.impl;

import com.ytd.framework.equipment.presenter.impl.BasePresenterImpl;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.bean.PDStateBean;
import com.ytd.framework.main.presenter.IPDStatePresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class PDStatePresenterImpl extends BasePresenterImpl implements IPDStatePresenter {


    @Override
    public void save(List<PDStateBean> list) {
        DataSupport.deleteAll(EntrepotBean.class);
        for (PDStateBean forBean : list) {
            forBean.save();
        }

    }

    @Override
    public List<PDStateBean> findAll() {
        return DataSupport.findAll(PDStateBean.class);
    }


}
