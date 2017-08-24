package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.ListUtils;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.ytd.framework.equipment.bean.PropertyBean.DB_LOGIN_NAME;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class ProperyPresenterImpl extends BasePresenterImpl implements IProperyPresenter {
    IEquipmentPresenter equipmentPresenter;


    @Override
    public void save(Context mContext, List<PropertyBean> list) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        if (null == equipmentPresenter) {
            equipmentPresenter = new EquipmentPresenterImpl();
        }
        for (PropertyBean forBean : list) {
            forBean.setLoginName(getLoginName());
            forBean.save();
            equipmentPresenter.save(mContext, forBean.getEqList(), forBean.getPDDH());
        }
    }

    @Override
    public List<PropertyBean> findAll(Context mContext) {
        return DataSupport.findAll(PropertyBean.class);
    }

    @Override
    public List<PropertyBean> findLimit(Context mContext, int offset, int limit) {
        return where(DB_LOGIN_NAME + " = ?  ", getLoginName()).offset(offset).limit(limit).find(PropertyBean.class);
    }

    @Override
    public PropertyBean findById(Context mContext, String id) {
        List<PropertyBean> list = where(DB_LOGIN_NAME + " = ? and  PDDH = ?", getLoginName(), id).find(PropertyBean.class);
        if (ListUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<PropertyBean> findBySearch(Context mContext, String search) {
        return where(DB_LOGIN_NAME + " = ? and  (title like ? or area like ?  or address like ?)", getLoginName(), "%" + search + "%", "%" + search + "%", "%" + search + "%").find(PropertyBean.class);
    }

    @Override
    public int deleteAll(Context mContext) {
        return DataSupport.deleteAll(PropertyBean.class, DB_LOGIN_NAME + "  = ?  ", getLoginName());
    }

    @Override
    public int findTotalcount(Context mContext) {
        return where("loginName = ?  ", getLoginName()).find(PropertyBean.class).size();
    }


}
