package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class PropertyListBean implements Parcelable {

    private String PageIndex;
    private String PageSize;
    private String Total;
    private List<PropertyBean> ItemList;

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<PropertyBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<PropertyBean> itemList) {
        ItemList = itemList;
    }

    public PropertyListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PageIndex);
        dest.writeString(this.PageSize);
        dest.writeString(this.Total);
        dest.writeTypedList(this.ItemList);
    }

    protected PropertyListBean(Parcel in) {
        this.PageIndex = in.readString();
        this.PageSize = in.readString();
        this.Total = in.readString();
        this.ItemList = in.createTypedArrayList(PropertyBean.CREATOR);
    }

    public static final Creator<PropertyListBean> CREATOR = new Creator<PropertyListBean>() {
        @Override
        public PropertyListBean createFromParcel(Parcel source) {
            return new PropertyListBean(source);
        }

        @Override
        public PropertyListBean[] newArray(int size) {
            return new PropertyListBean[size];
        }
    };
}
