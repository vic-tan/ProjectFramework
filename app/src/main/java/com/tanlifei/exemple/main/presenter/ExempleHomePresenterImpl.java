package com.tanlifei.exemple.main.presenter;

import com.tanlifei.exemple.main.bean.ExempleHomeListBean;
import com.tanlifei.exemple.main.interactor.ExempleHomeInteractor;
import com.tanlifei.exemple.main.interactor.ExempleHomeInteractorImpl;

import java.util.List;


/**
 * Created by tanlifei on 16/5/12.
 */
public class ExempleHomePresenterImpl implements ExempleHomePresenter {


    private ExempleHomeInteractor interactor;

    public ExempleHomePresenterImpl() {
        this.interactor = new ExempleHomeInteractorImpl();
    }


    @Override
    public List<ExempleHomeListBean> addList() {
        return interactor.addList();
    }
}
