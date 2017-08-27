package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class EquipmentBean extends DataSupport implements Parcelable {
    public static final String LOOKSTATUS_TAG_TRUE = "1";//已盘点
    public static final String LOOKSTATUS_TAG_FALSE = "0";//未盘点

    private long id;



    private String SBMC;//名称单名称
    private String count;//数量
    private String SBBH;//设备型号
    private String SBTMBH;//资产条码号
    private String eqId;//资产编号
    private String KSMC;//使用科室
    private String QYRQ;//盘点开始时间
    private String propertyStatus;//资产状态
    private String DW;//单位
    private String eqStandard;//规格
    private String saveAddress;//存放地点
    private String YZ;//原值


    private String JZ;//净值
    private String ZJ;//折旧
    private String useStatus;//资产状态
    private String lookDate;//盘点时间
    private String lookStatus;//盘点状态

    private String remark;//备注
    private String loginName;
    private String PDDH;

    private String StoreId;//仓库ID

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }


    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public String getQYRQ() {
        return QYRQ;
    }

    public void setQYRQ(String QYRQ) {
        this.QYRQ = QYRQ;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPDDH() {
        return PDDH;
    }

    public void setPDDH(String PDDH) {
        this.PDDH = PDDH;
    }



    public String getSBMC() {
        return SBMC;
    }

    public void setSBMC(String SBMC) {
        this.SBMC = SBMC;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
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


    public String getKSMC() {
        return KSMC;
    }

    public void setKSMC(String KSMC) {
        this.KSMC = KSMC;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public String getSBTMBH() {
        return SBTMBH;
    }

    public void setSBTMBH(String SBTMBH) {
        this.SBTMBH = SBTMBH;
    }

    public String getYZ() {
        return YZ;
    }

    public void setYZ(String YZ) {
        this.YZ = YZ;
    }

    public String getJZ() {
        return JZ;
    }

    public void setJZ(String JZ) {
        this.JZ = JZ;
    }

    public String getZJ() {
        return ZJ;
    }

    public void setZJ(String ZJ) {
        this.ZJ = ZJ;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.SBMC);
        dest.writeString(this.count);
        dest.writeString(this.SBBH);
        dest.writeString(this.SBTMBH);
        dest.writeString(this.eqId);
        dest.writeString(this.KSMC);
        dest.writeString(this.QYRQ);
        dest.writeString(this.propertyStatus);
        dest.writeString(this.DW);
        dest.writeString(this.eqStandard);
        dest.writeString(this.saveAddress);
        dest.writeString(this.YZ);
        dest.writeString(this.JZ);
        dest.writeString(this.ZJ);
        dest.writeString(this.useStatus);
        dest.writeString(this.lookDate);
        dest.writeString(this.lookStatus);
        dest.writeString(this.remark);
        dest.writeString(this.loginName);
        dest.writeString(this.PDDH);
        dest.writeString(this.StoreId);
    }

    protected EquipmentBean(Parcel in) {
        this.id = in.readLong();
        this.SBMC = in.readString();
        this.count = in.readString();
        this.SBBH = in.readString();
        this.SBTMBH = in.readString();
        this.eqId = in.readString();
        this.KSMC = in.readString();
        this.QYRQ = in.readString();
        this.propertyStatus = in.readString();
        this.DW = in.readString();
        this.eqStandard = in.readString();
        this.saveAddress = in.readString();
        this.YZ = in.readString();
        this.JZ = in.readString();
        this.ZJ = in.readString();
        this.useStatus = in.readString();
        this.lookDate = in.readString();
        this.lookStatus = in.readString();
        this.remark = in.readString();
        this.loginName = in.readString();
        this.PDDH = in.readString();
        this.StoreId = in.readString();
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
