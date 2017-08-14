package com.ytd.framework.equipment.presenter;

import android.content.Context;

import com.ytd.framework.equipment.bean.PropertyBean;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IProperyPresenter {

    void save(Context mContext, List<PropertyBean> list);//保存数据

    List<PropertyBean> findAll(Context mContext);

    List<PropertyBean> findBySearch(Context mContext,String search);//搜索盘点单
}
