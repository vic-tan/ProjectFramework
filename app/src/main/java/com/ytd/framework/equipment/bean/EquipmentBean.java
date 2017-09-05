package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tlf.basic.utils.StringUtils;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class EquipmentBean extends DataSupport implements Parcelable {
    public static final String LOOKSTATUS_TAG_TRUE = "1";//已盘点
    public static final String LOOKSTATUS_TAG_FALSE = "0";//未盘点
    public static final String UPDATE_TAG = "1";//本地扫描修改

    private long id;


    private String SBMC;//名称单名称
    private String count;//数量
    private String SBBH;//设备编号
    private String SBTMBH;//资产条码号
    private String eqId;//资产编号
    private String KSMC;//使用科室
    private String QYRQ;//盘点开始时间
    private String SBXH;//设备型号
    private String SBZT;//资产状态
    private String DW;//单位
    private String SBGG ;//规格
    private String CFDD;//存放地点
    private String YZ;//原值
    private String MC;//资产分类


    private String JZ;//净值
    private String ZJ;//折旧
    private String useStatus;//资产状态
    private String lookDate;//盘点时间
    private String State;//盘点状态 //
    private String updateTag;//0表示默认的，1，表示本扫描修改的

    private String Memo;//备注
    private String loginName;
    private String PDDH;

    private String StoreId;//仓库ID

    private String Code;


    private String Msg;

    public String getSBXH() {
        return SBXH;
    }

    public void setSBXH(String SBXH) {
        this.SBXH = SBXH;
    }

    public String getSBZT() {
        return SBZT;
    }

    public void setSBZT(String SBZT) {
        this.SBZT = SBZT;
    }

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

    public String getUpdateTag() {
        return updateTag;
    }

    public void setUpdateTag(String updateTag) {
        this.updateTag = updateTag;
    }

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

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getSBGG() {
        return SBGG;
    }

    public void setSBGG(String SBGG) {
        this.SBGG = SBGG;
    }

    public String getCFDD() {
        return CFDD;
    }

    public void setCFDD(String CFDD) {
        this.CFDD = CFDD;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
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


    public String getState() {
        return StringUtils.isEmpty(State) ? "0" : State;
    }

    public void setState(String state) {
        State = state;
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
        dest.writeString(this.SBXH);
        dest.writeString(this.SBZT);
        dest.writeString(this.DW);
        dest.writeString(this.SBGG);
        dest.writeString(this.CFDD);
        dest.writeString(this.YZ);
        dest.writeString(this.MC);
        dest.writeString(this.JZ);
        dest.writeString(this.ZJ);
        dest.writeString(this.useStatus);
        dest.writeString(this.lookDate);
        dest.writeString(this.State);
        dest.writeString(this.updateTag);
        dest.writeString(this.Memo);
        dest.writeString(this.loginName);
        dest.writeString(this.PDDH);
        dest.writeString(this.StoreId);
        dest.writeString(this.Code);
        dest.writeString(this.Msg);
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
        this.SBXH = in.readString();
        this.SBZT = in.readString();
        this.DW = in.readString();
        this.SBGG = in.readString();
        this.CFDD = in.readString();
        this.YZ = in.readString();
        this.MC = in.readString();
        this.JZ = in.readString();
        this.ZJ = in.readString();
        this.useStatus = in.readString();
        this.lookDate = in.readString();
        this.State = in.readString();
        this.updateTag = in.readString();
        this.Memo = in.readString();
        this.loginName = in.readString();
        this.PDDH = in.readString();
        this.StoreId = in.readString();
        this.Code = in.readString();
        this.Msg = in.readString();
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
