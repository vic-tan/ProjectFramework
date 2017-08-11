package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class EquipmentBean extends DataSupport implements Parcelable  {

    private String title;//名称单名称
    private String count;//数量
    private String eqType;//型号
    private String eqId;//资产编号
    private String useAddress;//使用科室
    private String start_data;//盘点开始时间
    private String propertyStatus;//资产状态
    private String lookStatus;//盘点状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getEqType() {
        return eqType;
    }

    public void setEqType(String eqType) {
        this.eqType = eqType;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }

    public String getUseAddress() {
        return useAddress;
    }

    public void setUseAddress(String useAddress) {
        this.useAddress = useAddress;
    }

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
    }

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getLookStatus() {
        return lookStatus;
    }

    public void setLookStatus(String lookStatus) {
        this.lookStatus = lookStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.count);
        dest.writeString(this.eqType);
        dest.writeString(this.eqId);
        dest.writeString(this.useAddress);
        dest.writeString(this.start_data);
        dest.writeString(this.propertyStatus);
        dest.writeString(this.lookStatus);
    }

    public EquipmentBean() {
    }

    protected EquipmentBean(Parcel in) {
        this.title = in.readString();
        this.count = in.readString();
        this.eqType = in.readString();
        this.eqId = in.readString();
        this.useAddress = in.readString();
        this.start_data = in.readString();
        this.propertyStatus = in.readString();
        this.lookStatus = in.readString();
    }

    public static final Creator<EquipmentBean> CREATOR = new Creator<EquipmentBean>() {
        @Override
        public EquipmentBean createFromParcel(Parcel source) {
            return new EquipmentBean(source);
        }

        @Override
        public EquipmentBean[] newArray(int size) {
            return new EquipmentBean[size];
        }
    };
}
