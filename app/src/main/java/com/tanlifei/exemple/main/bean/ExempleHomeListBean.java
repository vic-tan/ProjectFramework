package com.tanlifei.exemple.main.bean;

/**
 * Created by tanlifei on 16/6/30.
 */
public class ExempleHomeListBean {
    private String title;
    private Class<?> clazz;
    private String desc;

    public String getTitle() {
        return title;
    }

    public ExempleHomeListBean(String title, Class<?> clazz, String desc) {
        this.title = title;
        this.clazz = clazz;
        this.desc = desc;
    }


    public void setTitle(String title) {

        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
