package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.ListUtils;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.support.constants.fixed.GlobalConstants;

import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class ProperyPresenterImpl implements IProperyPresenter {
    IEquipmentPresenter equipmentPresenter;

    public static String getLoginName(Context mContext) {
        return AppCacheUtils.getInstance(mContext).getString(GlobalConstants.APP_LOGIN_NAME);
    }

    @Override
    public void save(Context mContext, List<PropertyBean> list) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        if (null == equipmentPresenter) {
            equipmentPresenter = new EquipmentPresenterImpl();
        }
        for (PropertyBean forBean : list) {
            forBean.setLoginName(getLoginName(mContext));
            forBean.save();
            equipmentPresenter.save(mContext, forBean.getEqList(), forBean.getMy_id());
        }
    }

    @Override
    public List<PropertyBean> findAll(Context mContext) {
        return DataSupport.findAll(PropertyBean.class);
    }

    @Override
    public List<PropertyBean> findLimit(Context mContext,int offset,int limit) {
        return where("loginName = ?  ", getLoginName(mContext)).offset(offset).limit(limit).find(PropertyBean.class);
    }

    @Override
    public List<PropertyBean> findBySearch(Context mContext, String search) {
        return where("loginName = ? and  (title like ? or area like ?  or address like ?)", getLoginName(mContext),"%"+search+"%","%"+search+"%","%"+search+"%").find(PropertyBean.class);
    }

    @Override
    public int deleteAll(Context mContext) {
        return DataSupport.deleteAll(PropertyBean.class,"loginName = ?  ", getLoginName(mContext));
    }


}
