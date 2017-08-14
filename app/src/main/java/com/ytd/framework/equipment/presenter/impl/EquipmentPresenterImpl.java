package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.ListUtils;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.support.constants.fixed.GlobalConstants;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class EquipmentPresenterImpl implements IEquipmentPresenter {


    public static String getLoginName(Context mContext) {
        return AppCacheUtils.getInstance(mContext).getString(GlobalConstants.APP_LOGIN_NAME);
    }

    @Override
    public void save(Context mContext, List<EquipmentBean> list, String propertyId) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        for (EquipmentBean forBean : list) {
            forBean.setLoginName(getLoginName(mContext));
            forBean.setPropertyId(propertyId);
            forBean.save();
        }
    }

    //TODO  字段名修改
    @Override
    public List<EquipmentBean> findAll(Context mContext, String propertyId) {
        return DataSupport.where("loginName = ? and propertyId = ?", getLoginName(mContext), propertyId).find(EquipmentBean.class);
    }

    //TODO  字段名修改
    @Override
    public List<EquipmentBean> findByState(Context mContext, String propertyId, String state) {
        return DataSupport.where("loginName = ? and propertyId = ? and lookStatus = ?", getLoginName(mContext), propertyId, state).find(EquipmentBean.class);

    }

    @Override
    public List<EquipmentBean> findScanCode(Context mContext, String eqId) {
        return DataSupport.where("loginName = ?  and my_id = ?", getLoginName(mContext), eqId).find(EquipmentBean.class);
    }

    //TODO  字段名修改
    @Override
    public boolean update(Context mContext, EquipmentBean equipmentBean) {
        List<EquipmentBean> list = findScanCode(mContext, equipmentBean.getMy_id());
        EquipmentBean albumToUpdate;
        if (!ListUtils.isEmpty(list)) {
            albumToUpdate = list.get(0);
            albumToUpdate.setUseStatus(equipmentBean.getUseStatus());
            albumToUpdate.setLookDate(equipmentBean.getLookDate());
            albumToUpdate.setRemark(equipmentBean.getRemark());
            albumToUpdate.setLookStatus("1");
            albumToUpdate.save();
            return true;
        }else{
            return false;
        }
    }
}
