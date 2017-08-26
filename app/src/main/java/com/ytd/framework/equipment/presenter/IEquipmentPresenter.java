package com.ytd.framework.equipment.presenter;

import android.content.Context;

import com.ytd.framework.equipment.bean.EquipmentBean;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IEquipmentPresenter {
    void save(Context mContext, List<EquipmentBean> list,String propertyId);//保存数据

    List<EquipmentBean> findAll(Context mContext,String propertyId);//查询资产下面所有的设备

    List<EquipmentBean> findLimit(Context mContext,String propertyId,String state, int offset, int limit);//分布查询

    List<EquipmentBean> findByState(Context mContext,String propertyId,String state);//盘点状态查询

    List<EquipmentBean> findScanCode(Context mContext,String eqId);//扫码查询


    boolean update(Context mContext, EquipmentBean equipmentBean);

    int findTotalcount(Context mContext,String propertyId,String state);

    int deleteById(String id);

    List<EquipmentBean> findBySearch(Context mContext, String search);//搜索盘点单
}
