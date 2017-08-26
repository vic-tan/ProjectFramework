package com.ytd.framework.main.presenter;

import com.ytd.framework.main.bean.EntrepotBean;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/13.
 */

public interface IEntrepotPresenter {

    void save(List<EntrepotBean> list);//保存数据

    List<EntrepotBean> findAll();

}
