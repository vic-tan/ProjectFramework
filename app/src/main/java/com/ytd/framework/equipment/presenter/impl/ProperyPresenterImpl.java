package com.ytd.framework.equipment.presenter.impl;

import android.content.Context;

import com.tlf.basic.utils.ListUtils;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_FALSE;
import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;
import static com.ytd.framework.main.bean.UserBean.DB_LOGIN_NAME;
import static com.ytd.framework.main.bean.UserBean.STORE_ID;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by tanlifei on 2017/8/13.
 */

public class ProperyPresenterImpl extends BasePresenterImpl implements IProperyPresenter {
    IEquipmentPresenter equipmentPresenter;

    @Override
    public void asySave(Context mContext, List<PropertyBean> list) {
        if (ListUtils.isEmpty(list)) {
            return;
        }
        if (null == equipmentPresenter) {
            equipmentPresenter = new EquipmentPresenterImpl();
        }
        for (PropertyBean forBean : list) {
            forBean.setLoginName(getLoginName());
            forBean.setStoreId(getUserBean().getStoreId());
            forBean.saveAsync();
            equipmentPresenter.save(mContext, forBean.getEqList(), forBean.getPDDH());
        }
    }


    public PropertyBean setEmpty(PropertyBean bean) {
        if (null != bean) {
            bean.setXM(empty(bean.getXM()));
            bean.setPhone(empty(bean.getPhone()));
            bean.setPrice(empty(bean.getPrice()));
            bean.setSTATUS(empty(bean.getSTATUS(), UPDATELOAD_TAG_FALSE));
            bean.setSTORE(empty(bean.getSTORE()));
            bean.setAddress(empty(bean.getAddress()));
            bean.setArea(empty(bean.getAddress()));
            bean.setStart_property(empty(bean.getStart_property()));
            bean.setEnd_property(empty(bean.getEnd_property()));
            bean.setTitle(empty(bean.getTitle()));
            bean.setEnd_data(empty(bean.getEnd_data()));
        }
        return bean;

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
            forBean.setLoginName(getLoginName());
            forBean.setStoreId(getUserBean().getStoreId());
            setEmpty(forBean).save();
            equipmentPresenter.save(mContext, forBean.getEqList(), forBean.getPDDH());
        }
    }

    @Override
    public void update(Context mContext, PropertyBean bean) {
        PropertyBean db = findById(mContext, bean.getPDDH());
        db.setPDABind(bean.isPDABind());
        db.update(db.getId());
    }

    @Override
    public void updateFinishNum(Context mContext, String PDDH, String num) {
        PropertyBean bean = findById(mContext, PDDH);
        if (null != bean) {
            bean.setSTATUS(UPDATELOAD_TAG_TRUE);
            bean.setFinshNum((Integer.parseInt(bean.getFinshNum()) + Integer.parseInt(num)) + "");
            bean.update(bean.getId());
        }
    }

    @Override
    public void addFinishNum(Context mContext, String PDDH, String num) {
        PropertyBean bean = findById(mContext, PDDH);
        if (null != bean) {
            bean.setFinshNum((Integer.parseInt(bean.getFinshNum()) + Integer.parseInt(num)) + "");
            bean.update(bean.getId());
        }
    }

    @Override
    public void initFinishNum(Context mContext, String PDDH, String num) {
        PropertyBean bean = findById(mContext, PDDH);
        if (null != bean) {
            bean.setFinshNum(num + "");
            bean.update(bean.getId());
        }
    }

    @Override
    public void updateFinish(Context mContext, String PDDH, List<EquipmentBean> updateList) {
        PropertyBean bean = findById(mContext, PDDH);
        if (null != bean) {
            bean.setSTATUS(UPDATELOAD_TAG_TRUE);
            bean.setFinshNum((Integer.parseInt(bean.getFinshNum()) + updateList.size()) + "");
            bean.update(bean.getId());
            if (null == equipmentPresenter) {
                equipmentPresenter = new EquipmentPresenterImpl();
            }
            equipmentPresenter.updateFinsh(mContext, updateList, null);
        }
    }

    @Override
    public List<PropertyBean> findAll(Context mContext) {
        return DataSupport.findAll(PropertyBean.class);
    }

    @Override
    public List<PropertyBean> findLimit(Context mContext, int offset, int limit) {
        return where(DB_LOGIN_NAME + " = ? and " + STORE_ID + " = ? ", getLoginName(), getUserBean().getStoreId()).order("STATUS asc ,RQ desc ").offset(offset).limit(limit).find(PropertyBean.class);
    }

    @Override
    public PropertyBean findById(Context mContext, String id) {
        List<PropertyBean> list = where(DB_LOGIN_NAME + " = ? and  PDDH = ? and " + STORE_ID + " = ? ", getLoginName(), id, getUserBean().getStoreId()).find(PropertyBean.class);
        if (ListUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<PropertyBean> findBySearch(Context mContext, String search) {
        return where(DB_LOGIN_NAME + " = ? and  (title like ? or area like ?  or address like ?) and " + STORE_ID + " = ? ", getLoginName(), "%" + search + "%", "%" + search + "%", "%" + search + "%", getUserBean().getStoreId()).find(PropertyBean.class);
    }

    @Override
    public int deleteAll(Context mContext) {
        return DataSupport.deleteAll(PropertyBean.class, DB_LOGIN_NAME + "  = ?  and " + STORE_ID + " = ? ", getLoginName(), getUserBean().getStoreId());
    }

    @Override
    public int deleteById(Context mContext, String id) {
        if (null == equipmentPresenter) {
            equipmentPresenter = new EquipmentPresenterImpl();
        }
        int count = equipmentPresenter.deleteById(id);
        if (count == 0) {
            List<EquipmentBean> equipmentList = equipmentPresenter.findAll(mContext, id);
            if (ListUtils.isEmpty(equipmentList)) {
                return startDelete(id);
            } else {
                equipmentPresenter.deleteById(id);
                return startDelete(id);
            }
        }
        return startDelete(id);
    }

    public int startDelete(String id) {
        return DataSupport.deleteAll(PropertyBean.class, DB_LOGIN_NAME + "  = ? and PDDH = ?  and " + STORE_ID + " = ? ", getLoginName(), id, getUserBean().getStoreId());
    }


    @Override
    public int findTotalcount(Context mContext) {
        return where(DB_LOGIN_NAME + "= ?  and " + STORE_ID + " = ? ", getLoginName(), getUserBean().getStoreId()).find(PropertyBean.class).size();
    }


}
