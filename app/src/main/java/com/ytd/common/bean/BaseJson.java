package com.ytd.common.bean;

/**
 * json 最外层数据基本结构实体
 *
 * @author ytd
 * @date 2015年8月13日 上午11:30:51
 */
public class BaseJson<T> {

    private String Code;
    private String Msg;
    private T Data;
    private String IsSuccess;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "Code='" + Code + '\'' +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                ", IsSuccess='" + IsSuccess + '\'' +
                '}';
    }
}
