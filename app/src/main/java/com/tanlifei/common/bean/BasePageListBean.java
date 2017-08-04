package com.tanlifei.common.bean;


public class BasePageListBean<T> extends PageBean {
    static final String LIST_KEY = "mRefreshList";
    private T list;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }


    public BasePageListBean() {

    }
}
