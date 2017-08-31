package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanlifei on 2017/8/28.
 */

public class EquipmentUpdateResultsBean implements Parcelable {


    /**
     * SBBH : 0202845000478911
     * Code : 2
     * Msg : 盘点明细不存在！
     */

    private String SBBH;
    private int Code;
    private String Msg;

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SBBH);
        dest.writeInt(this.Code);
        dest.writeString(this.Msg);
    }

    public EquipmentUpdateResultsBean() {
    }

    protected EquipmentUpdateResultsBean(Parcel in) {
        this.SBBH = in.readString();
        this.Code = in.readInt();
        this.Msg = in.readString();
    }

    public static final Parcelable.Creator<EquipmentUpdateResultsBean> CREATOR = new Parcelable.Creator<EquipmentUpdateResultsBean>() {
        @Override
        public EquipmentUpdateResultsBean createFromParcel(Parcel source) {
            return new EquipmentUpdateResultsBean(source);
        }

        @Override
        public EquipmentUpdateResultsBean[] newArray(int size) {
            return new EquipmentUpdateResultsBean[size];
        }
    };
}
