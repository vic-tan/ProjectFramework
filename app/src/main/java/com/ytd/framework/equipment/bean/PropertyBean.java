package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class PropertyBean implements Parcelable  {

    private String title;//名称单名称
    private String name;//盘点人
    private String phone;//手机号
    private String price;//价格区间
    private String area;//盘点区域
    private String address;//盘点地址
    private String start_data;//盘点开始时间
    private String end_data;//盘点结束时间
    private String totalNum;//总设备数据
    private String finshNum;//完成数据
    private String status;//盘点状态
    private String start_property;//资产原值；
    private String end_property;//资产净值；
    private String updateload;//盘点单是否上传

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PropertyBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.price);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.start_data);
        dest.writeString(this.end_data);
        dest.writeString(this.totalNum);
        dest.writeString(this.finshNum);
        dest.writeString(this.status);
        dest.writeString(this.start_property);
        dest.writeString(this.end_property);
        dest.writeString(this.updateload);
    }

    protected PropertyBean(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.price = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.start_data = in.readString();
        this.end_data = in.readString();
        this.totalNum = in.readString();
        this.finshNum = in.readString();
        this.status = in.readString();
        this.start_property = in.readString();
        this.end_property = in.readString();
        this.updateload = in.readString();
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
