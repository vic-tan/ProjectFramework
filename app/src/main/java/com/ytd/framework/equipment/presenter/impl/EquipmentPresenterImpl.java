package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.ytd.framework.main.bean.UserBean.DB_LOGIN_NAME;
import static com.ytd.framework.main.bean.UserBean.STORE_ID;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class EquipmentPresenterImpl extends BasePresenterImpl implements IEquipmentPresenter {


    @Override
    public void asySave(Context mContext, List<EquipmentBean> list, String PDDH) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        for (EquipmentBean forBean : list) {
            forBean.setLoginName(getLoginName());
            forBean.setStoreId(getUserBean().getStoreId());
            forBean.setPDDH(PDDH);
            forBean.saveAsync();
        }
    }

    @Override
    public void save(Context mContext, List<EquipmentBean> list, String PDDH) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        for (EquipmentBean forBean : list) {
            forBean.setLoginName(getLoginName());
            forBean.setStoreId(getUserBean().getStoreId());
            forBean.setPDDH(PDDH);
            forBean.save();
        }
    }

    @Override
    public List<EquipmentBean> findLimit(Context mContext, String PDDH, String state, int offset, int limit) {
        if (StringUtils.isEmpty(state)) {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), PDDH, getUserBean().getStoreId()).offset(offset).limit(limit).find(EquipmentBean.class);
        } else {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ? and lookStatus = ? and " + STORE_ID + " = ? ", getLoginName(), PDDH, state, getUserBean().getStoreId()).offset(offset).limit(limit).find(EquipmentBean.class);
        }

    }


    //TODO  字段名修改
    @Override
    public List<EquipmentBean> findAll(Context mContext, String propertyId) {
        return where(DB_LOGIN_NAME + "  = ? and PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, getUserBean().getStoreId()).find(EquipmentBean.class);
    }

    //TODO  字段名修改
    @Override
    public List<EquipmentBean> findByState(Context mContext, String propertyId, String state) {
        return where(DB_LOGIN_NAME + "  = ? and PDDH = ? and lookStatus = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, state, getUserBean().getStoreId()).find(EquipmentBean.class);

    }

    @Override
    public List<EquipmentBean> findScanCode(Context mContext, String eqId) {
        return where(DB_LOGIN_NAME + "  = ?  and my_id = ? and " + STORE_ID + " = ? ", getLoginName(), eqId, getUserBean().getStoreId()).find(EquipmentBean.class);
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
        } else {
            return false;
        }
    }

    @Override
    public int findTotalcount(Context mContext, String propertyId, String state) {
        if (StringUtils.isEmpty(state)) {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ?  and " + STORE_ID + " = ? ", getLoginName(), propertyId, getUserBean().getStoreId()).find(EquipmentBean.class).size();
        }
        return where(DB_LOGIN_NAME + " = ?  and PDDH = ? and lookStatus = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, state, getUserBean().getStoreId()).find(EquipmentBean.class).size();
    }

    @Override
    public List<EquipmentBean> findBySearch(Context mContext, String search) {
        return where(DB_LOGIN_NAME + " = ? and  (SBMC like ? or SBBH like ?  or KSMC like ? ) and " + STORE_ID + " = ? ", getLoginName(), "%" + search + "%", "%" + search + "%", "%" + search + "%", getUserBean().getStoreId()).find(EquipmentBean.class);
    }


    @Override
    public int deleteById(String id) {
        return  DataSupport.deleteAll(EquipmentBean.class, DB_LOGIN_NAME + "  = ? and  PDDH = ?  and " + STORE_ID + " = ? ", getLoginName(), id,getUserBean().getStoreId());
    }

}
