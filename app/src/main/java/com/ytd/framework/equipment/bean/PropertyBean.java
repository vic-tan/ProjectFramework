package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class PropertyBean extends DataSupport implements Parcelable {


    public static final String UPDATELOAD_TAG_TRUE = "1";//已上传
    public static final String UPDATELOAD_TAG_FALSE = "0";//未上传

    private String PDDH;//盘点单号;
    private String title;//名称单名称
    private String PDR;//盘点人编号
    private String ZDR;//经手人编号
    private String SHRQ;//审核日期
    private String PDLX;//盘点类型
    private String BZ;//备注
    private String STORE;//盘点仓库
    private String XM1;//未知
    private String XM;//盘点人
    private String phone;//手机号
    private String price;//价格区间
    private String area;//盘点区域
    private String address;//盘点地址
    private String RQ;//盘点开始时间
    private String end_data;//盘点结束时间
    private String totalNum;//总设备数据
    private String finshNum;//完成数据
    private String STATUS;//盘点状态
    private String start_property;//资产原值；
    private String end_property;//资产净值；
    private String updateload;//盘点单是否上传//0,未上传，1,已上传
    private String loginName;
    private String StoreId;//仓库ID

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getPDR() {
        return PDR;
    }

    public void setPDR(String PDR) {
        this.PDR = PDR;
    }

    public String getZDR() {
        return ZDR;
    }

    public void setZDR(String ZDR) {
        this.ZDR = ZDR;
    }

    public String getSHRQ() {
        return SHRQ;
    }

    public void setSHRQ(String SHRQ) {
        this.SHRQ = SHRQ;
    }

    public String getPDLX() {
        return PDLX;
    }

    public void setPDLX(String PDLX) {
        this.PDLX = PDLX;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getSTORE() {
        return STORE;
    }

    public void setSTORE(String STORE) {
        this.STORE = STORE;
    }

    public String getXM1() {
        return XM1;
    }

    public void setXM1(String XM1) {
        this.XM1 = XM1;
    }

    private List<EquipmentBean> eqList;

    public String getPDDH() {
        return PDDH;
    }

    public void setPDDH(String PDDH) {
        this.PDDH = PDDH;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRQ() {
        return RQ;
    }

    public void setRQ(String RQ) {
        this.RQ = RQ;
    }

    public String getEnd_data() {
        return end_data;
    }

    public void setEnd_data(String end_data) {
        this.end_data = end_data;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getFinshNum() {
        return finshNum;
    }

    public void setFinshNum(String finshNum) {
        this.finshNum = finshNum;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
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

    public String getUpdateload() {
        return updateload;
    }

    public void setUpdateload(String updateload) {
        this.updateload = updateload;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<EquipmentBean> getEqList() {
        return eqList;
    }

    public void setEqList(List<EquipmentBean> eqList) {
        this.eqList = eqList;
    }


    public PropertyBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PDDH);
        dest.writeString(this.title);
        dest.writeString(this.PDR);
        dest.writeString(this.ZDR);
        dest.writeString(this.SHRQ);
        dest.writeString(this.PDLX);
        dest.writeString(this.BZ);
        dest.writeString(this.STORE);
        dest.writeString(this.XM1);
        dest.writeString(this.XM);
        dest.writeString(this.phone);
        dest.writeString(this.price);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.RQ);
        dest.writeString(this.end_data);
        dest.writeString(this.totalNum);
        dest.writeString(this.finshNum);
        dest.writeString(this.STATUS);
        dest.writeString(this.start_property);
        dest.writeString(this.end_property);
        dest.writeString(this.updateload);
        dest.writeString(this.loginName);
        dest.writeString(this.StoreId);
        dest.writeTypedList(this.eqList);
    }

    protected PropertyBean(Parcel in) {
        this.PDDH = in.readString();
        this.title = in.readString();
        this.PDR = in.readString();
        this.ZDR = in.readString();
        this.SHRQ = in.readString();
        this.PDLX = in.readString();
        this.BZ = in.readString();
        this.STORE = in.readString();
        this.XM1 = in.readString();
        this.XM = in.readString();
        this.phone = in.readString();
        this.price = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.RQ = in.readString();
        this.end_data = in.readString();
        this.totalNum = in.readString();
        this.finshNum = in.readString();
        this.STATUS = in.readString();
        this.start_property = in.readString();
        this.end_property = in.readString();
        this.updateload = in.readString();
        this.loginName = in.readString();
        this.StoreId = in.readString();
        this.eqList = in.createTypedArrayList(EquipmentBean.CREATOR);
    }

    public static final Creator<PropertyBean> CREATOR = new Creator<PropertyBean>() {
        @Override
        public PropertyBean createFromParcel(Parcel source) {
            return new PropertyBean(source);
        }

        @Override
        public PropertyBean[] newArray(int size) {
            return new PropertyBean[size];
        }
    };
}
