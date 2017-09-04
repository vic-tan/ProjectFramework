package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_FALSE;
import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_TRUE;
import static com.ytd.framework.equipment.bean.EquipmentBean.UPDATE_TAG;
import static com.ytd.framework.main.bean.UserBean.DB_LOGIN_NAME;
import static com.ytd.framework.main.bean.UserBean.STORE_ID;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class EquipmentPresenterImpl extends BasePresenterImpl implements IEquipmentPresenter {
    IProperyPresenter properyPresenter;

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
        int finishCount = 0;
        for (EquipmentBean forBean : list) {
            forBean.setLoginName(getLoginName());
            forBean.setStoreId(getUserBean().getStoreId());
            forBean.setPDDH(PDDH);
            forBean.setState(empty(forBean.getState(), LOOKSTATUS_TAG_FALSE));
            if (StringUtils.isEquals(forBean.getState(), LOOKSTATUS_TAG_TRUE)) {
                finishCount++;
            }
            setEmpty(forBean).save();
        }
        if (properyPresenter == null) {
            properyPresenter = new ProperyPresenterImpl();
        }
        properyPresenter.initFinishNum(mContext, PDDH, finishCount + "");
    }


    public EquipmentBean setEmpty(EquipmentBean bean) {
        if (null != bean) {
            bean.setSBMC(empty(bean.getSBMC()));
//            bean.setSBBH(empty(bean.getSBBH()));
            bean.setSBTMBH(empty(bean.getSBTMBH()));
            bean.setEqId(empty(bean.getEqId()));
            bean.setKSMC(empty(bean.getKSMC()));
            bean.setQYRQ(empty(bean.getQYRQ()));
            bean.setYZ(empty(bean.getYZ()));
            bean.setJZ(empty(bean.getJZ()));
            bean.setZJ(empty(bean.getZJ()));
            bean.setSaveAddress(empty(bean.getSaveAddress()));
            bean.setEqStandard(empty(bean.getEqStandard()));
            bean.setCount(empty(bean.getCount(), "1"));
            bean.setUseStatus(empty(bean.getUseStatus()));
            bean.setLookDate(empty(bean.getLookDate()));
            bean.setUpdateTag(empty(bean.getUpdateTag(), "0"));
            bean.setPropertyStatus(empty(bean.getPropertyStatus()));
        }
        return bean;

    }


    @Override
    public void updateFinsh(Context mContext, List<EquipmentBean> updateList, List<EquipmentBean> failList) {
        if (!ListUtils.isEmpty(updateList)) {
            for (EquipmentBean forBean : updateList) {
                update(mContext, forBean);
            }
        }
        if (!ListUtils.isEmpty(failList)) {
            for (EquipmentBean forBean : failList) {
                loUpdate(mContext, forBean, LOOKSTATUS_TAG_FALSE);
            }
        }
    }

    @Override
    public List<EquipmentBean> findLimit(Context mContext, String PDDH, String state, int offset, int limit) {
        if (StringUtils.isEmpty(state)) {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), PDDH, getUserBean().getStoreId()).offset(offset).limit(limit).find(EquipmentBean.class);
        } else {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ? and State = ? and " + STORE_ID + " = ? ", getLoginName(), PDDH, state, getUserBean().getStoreId()).offset(offset).limit(limit).find(EquipmentBean.class);
        }

    }   @Override
    public List<EquipmentBean> updateFindLimit(Context mContext, String PDDH, int offset, int limit) {
        return where(DB_LOGIN_NAME + "  = ?  and PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), PDDH, getUserBean().getStoreId()).offset(offset).limit(limit).select("SBBH", "State").find(EquipmentBean.class);

    }




    @Override
    public List<EquipmentBean> findAll(Context mContext, String propertyId) {
        return where(DB_LOGIN_NAME + "  = ? and PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, getUserBean().getStoreId()).find(EquipmentBean.class);
    }

    @Override
    public List<EquipmentBean> findByState(Context mContext, String propertyId, String state) {
        return where(DB_LOGIN_NAME + "  = ? and PDDH = ? and State = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, state, getUserBean().getStoreId()).find(EquipmentBean.class);

    }

    @Override
    public List<EquipmentBean> findScanCode(Context mContext, String eqId) {
        return where(DB_LOGIN_NAME + "  = ?  and SBBH  = ? and " + STORE_ID + " = ? ", getLoginName(), eqId, getUserBean().getStoreId()).find(EquipmentBean.class);
    }

    @Override
    public boolean update(Context mContext, EquipmentBean equipmentBean) {
        return loUpdate(mContext, equipmentBean, LOOKSTATUS_TAG_TRUE);
    }


    @Override
    public boolean scanUpdate(Context mContext, EquipmentBean equipmentBean) {
        if (properyPresenter == null) {
            properyPresenter = new ProperyPresenterImpl();
        }
        properyPresenter.addFinishNum(mContext, equipmentBean.getPDDH(),  "1",equipmentBean.getLookDate());
        return loUpdate(mContext, equipmentBean, LOOKSTATUS_TAG_TRUE);
    }


    private boolean loUpdate(Context mContext, EquipmentBean equipmentBean, String state) {
        List<EquipmentBean> list = findScanCode(mContext, equipmentBean.getSBBH());
        EquipmentBean albumToUpdate;
        if (!ListUtils.isEmpty(list)) {
            albumToUpdate = list.get(0);
            albumToUpdate.setUseStatus(equipmentBean.getUseStatus());
            albumToUpdate.setLookDate(equipmentBean.getLookDate());
            albumToUpdate.setRemark(equipmentBean.getRemark());
            albumToUpdate.setState(state);
            albumToUpdate.setUpdateTag(UPDATE_TAG);
            return albumToUpdate.save();
        } else {
            return false;
        }
    }

    @Override
    public int findTotalcount(Context mContext, String propertyId, String state) {
        if (StringUtils.isEmpty(state)) {
            return where(DB_LOGIN_NAME + "  = ?  and PDDH = ?  and " + STORE_ID + " = ? ", getLoginName(), propertyId, getUserBean().getStoreId()).find(EquipmentBean.class).size();
        }
        return where(DB_LOGIN_NAME + " = ?  and PDDH = ? and State = ? and " + STORE_ID + " = ? ", getLoginName(), propertyId, state, getUserBean().getStoreId()).find(EquipmentBean.class).size();
    }

    @Override
    public List<EquipmentBean> findBySearch(Context mContext, String search) {
        return where(DB_LOGIN_NAME + " = ? and  (SBMC like ? or SBBH like ?  or KSMC like ? ) and " + STORE_ID + " = ? ", getLoginName(), "%" + search + "%", "%" + search + "%", "%" + search + "%", getUserBean().getStoreId()).find(EquipmentBean.class);
    }


    @Override
    public int deleteById(String id) {
        return DataSupport.deleteAll(EquipmentBean.class, DB_LOGIN_NAME + "  = ? and  PDDH = ?  and " + STORE_ID + " = ? ", getLoginName(), id, getUserBean().getStoreId());
    }

    @Override
    public List<EquipmentBean> findByUpdateTag(Context mContext, String PDDH, String updateTag) {
        return DataSupport.where(DB_LOGIN_NAME + "  = ?  and PDDH = ?  and " + STORE_ID + " = ?  and updateTag = ? ", getLoginName(), PDDH, getUserBean().getStoreId(), updateTag).select("SBBH", "State").find(EquipmentBean.class);
    }


}
