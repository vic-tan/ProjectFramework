package com.ytd.framework.main.presenter;

import com.ytd.framework.main.bean.PDStateBean;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IPDStatePresenter {

    void save(List<PDStateBean> list);//保存数据

    List<PDStateBean> findAll();

}
