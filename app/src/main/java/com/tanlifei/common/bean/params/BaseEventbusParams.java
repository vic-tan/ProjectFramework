package com.tanlifei.common.bean.params;

/**
 * Created by tanlifei on 16/9/21.
 */

public class BaseEventbusParams {

    public int tag = -1;//用来过滤通知标识
    public Object objParam;
    public int intParam;
    public String strParam;
    public Object prepareParam;


    //一个参数
    public BaseEventbusParams(int tag) {
        this.tag = tag;
    }


    //二个参数
    public BaseEventbusParams(int tag, Object objParam) {
        this.tag = tag;
        this.objParam = objParam;
    }

    public BaseEventbusParams(int tag, int intParam) {
        this.tag = tag;
        this.intParam = intParam;

    }


    public BaseEventbusParams(int tag, String strParam) {
        this.tag = tag;
        this.strParam = strParam;
    }

    //三个参数

    public BaseEventbusParams(int tag, Object objParam, Object prepareParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.prepareParam = prepareParam;
    }

    public BaseEventbusParams(int tag, Object objParam, int intParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.intParam = intParam;
    }


    public BaseEventbusParams(int tag, Object objParam, String strParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.strParam = strParam;
    }


    public BaseEventbusParams(int tag, int intParam, String strParam) {
        this.tag = tag;
        this.intParam = intParam;
        this.strParam = strParam;
    }


    //四个参数
    public BaseEventbusParams(int tag, Object objParam, int intParam, String strParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.intParam = intParam;
        this.strParam = strParam;
    }

    public BaseEventbusParams(int tag, Object objParam, Object prepareParam, String strParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.prepareParam = prepareParam;
        this.strParam = strParam;
    }

    public BaseEventbusParams(int tag, Object objParam, Object prepareParam, int intParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.intParam = intParam;
        this.prepareParam = prepareParam;
    }

    //五个参数
    public BaseEventbusParams(int tag, Object objParam, Object prepareParam, int intParam, String strParam) {
        this.tag = tag;
        this.objParam = objParam;
        this.prepareParam = prepareParam;
        this.intParam = intParam;
        this.strParam = strParam;
    }

    public Object getPrepareParam() {
        return prepareParam;
    }

    public void setPrepareParam(Object prepareParam) {
        this.prepareParam = prepareParam;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObjParam() {
        return objParam;
    }

    public void setObjParam(Object objParam) {
        this.objParam = objParam;
    }

    public int getIntParam() {
        return intParam;
    }

    public void setIntParam(int intParam) {
        this.intParam = intParam;
    }

    public String getStrParam() {
        return strParam;
    }

    public void setStrParam(String strParam) {
        this.strParam = strParam;
    }
}
