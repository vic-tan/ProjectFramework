package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class EquipmentBean extends DataSupport implements Parcelable {

    private String my_id;

    private String title;//名称单名称
    private String count;//数量
    private String eqType;//设备型号
    private String barCode;//资产条码号
    private String eqId;//资产编号
    private String useAddress;//使用科室
    private String start_data;//盘点开始时间
    private String propertyStatus;//资产状态
    private String unitName;//单位
    private String eqStandard;//规格
    private String saveAddress;//存放地点
    private String start_property;//原值
    private String end_property;//净值
    private String old_property;//折旧
    private String useStatus;//资产状态
    private String lookDate;//盘点时间
    private String lookStatus;//盘点状态


    private String remark;//备注


    private String loginName;
    private String propertyId;


    public String getBarCode() {
        return barCode;
    }



    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getLookDate() {
        return lookDate;
    }

    public void setLookDate(String lookDate) {
        this.lookDate = lookDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getEqStandard() {
        return eqStandard;
    }

    public void setEqStandard(String eqStandard) {
        this.eqStandard = eqStandard;
    }

    public String getSaveAddress() {
        return saveAddress;
    }

    public void setSaveAddress(String saveAddress) {
        this.saveAddress = saveAddress;
    }

    public String getStart_property() {
        return start_property;
    }

    public void setStart_property(String start_property) {
        this.start_property = start_property;
    }

    public String getEnd_property() {
        return end_property;
    }

    public void setEnd_property(String end_property) {
        this.end_property = end_property;
    }

    public String getOld_property() {
        return old_property;
    }

    public void setOld_property(String old_property) {
        this.old_property = old_property;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

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

    public EquipmentBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.my_id);
        dest.writeString(this.title);
        dest.writeString(this.count);
        dest.writeString(this.eqType);
        dest.writeString(this.barCode);
        dest.writeString(this.eqId);
        dest.writeString(this.useAddress);
        dest.writeString(this.start_data);
        dest.writeString(this.propertyStatus);
        dest.writeString(this.unitName);
        dest.writeString(this.eqStandard);
        dest.writeString(this.saveAddress);
        dest.writeString(this.start_property);
        dest.writeString(this.end_property);
        dest.writeString(this.old_property);
        dest.writeString(this.useStatus);
        dest.writeString(this.lookDate);
        dest.writeString(this.lookStatus);
        dest.writeString(this.remark);
        dest.writeString(this.loginName);
        dest.writeString(this.propertyId);
    }

    protected EquipmentBean(Parcel in) {
        this.my_id = in.readString();
        this.title = in.readString();
        this.count = in.readString();
        this.eqType = in.readString();
        this.barCode = in.readString();
        this.eqId = in.readString();
        this.useAddress = in.readString();
        this.start_data = in.readString();
        this.propertyStatus = in.readString();
        this.unitName = in.readString();
        this.eqStandard = in.readString();
        this.saveAddress = in.readString();
        this.start_property = in.readString();
        this.end_property = in.readString();
        this.old_property = in.readString();
        this.useStatus = in.readString();
        this.lookDate = in.readString();
        this.lookStatus = in.readString();
        this.remark = in.readString();
        this.loginName = in.readString();
        this.propertyId = in.readString();
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
