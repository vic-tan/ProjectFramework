package com.ytd.framework.equipment.presenter;

import android.content.Context;

import com.ytd.framework.equipment.bean.PropertyBean;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IProperyPresenter {

    void asySave(Context mContext, List<PropertyBean> list);//保存数据
    void save(Context mContext, List<PropertyBean> list);//保存数据
    void update(Context mContext, PropertyBean bean);

    List<PropertyBean> findAll(Context mContext);

    List<PropertyBean> findLimit(Context mContext, int offset, int limit);//分布查询


    PropertyBean findById(Context mContext, String id);

    List<PropertyBean> findBySearch(Context mContext, String search);//搜索盘点单

    int deleteAll(Context mContext);

    int deleteById(Context mContext,String id);

    int findTotalcount(Context mContext);
}
