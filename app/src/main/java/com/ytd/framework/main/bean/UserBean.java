package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class UserBean extends DataSupport implements Parcelable {

    public static final String DB_LOGIN_NAME = "loginName";//登录名
    public static final String STORE_ID = "StoreId";//仓库ID
    private String loginName;//登录名
    private String Name;//用户名称
    private String pwd;
    private String DepartmentId;//用户所属科室ID
    private String DepartmentName;//用户所属科室名称
    private String StoreId;//仓库ID
    private String StoreName;//仓库名字
    private String EquId;//PDA设备ID
    private long lastDate;//最后一次在线登录时间记录，离线登录7天有效，7天过后必要在线登录



    public long getLastDate() {
        return lastDate;
    }

    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }

    public String getEquId() {
        return EquId;
    }

    public void setEquId(String equId) {
        EquId = equId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public UserBean() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loginName);
        dest.writeString(this.Name);
        dest.writeString(this.pwd);
        dest.writeString(this.DepartmentId);
        dest.writeString(this.DepartmentName);
        dest.writeString(this.StoreId);
        dest.writeString(this.StoreName);
        dest.writeString(this.EquId);
        dest.writeLong(this.lastDate);
    }

    protected UserBean(Parcel in) {
        this.loginName = in.readString();
        this.Name = in.readString();
        this.pwd = in.readString();
        this.DepartmentId = in.readString();
        this.DepartmentName = in.readString();
        this.StoreId = in.readString();
        this.StoreName = in.readString();
        this.EquId = in.readString();
        this.lastDate = in.readLong();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
