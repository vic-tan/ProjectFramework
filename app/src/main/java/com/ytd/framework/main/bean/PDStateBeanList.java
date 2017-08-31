package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/29.
 */

public class PDStateBeanList implements Parcelable {

    private String PageIndex;
    private String PageSize;
    private String Total;
    private List<PDStateBean> ItemList;

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

    public List<PDStateBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<PDStateBean> itemList) {
        ItemList = itemList;
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

    public PDStateBeanList() {
    }

    protected PDStateBeanList(Parcel in) {
        this.PageIndex = in.readString();
        this.PageSize = in.readString();
        this.Total = in.readString();
        this.ItemList = in.createTypedArrayList(PDStateBean.CREATOR);
    }

    public static final Creator<PDStateBeanList> CREATOR = new Creator<PDStateBeanList>() {
        @Override
        public PDStateBeanList createFromParcel(Parcel source) {
            return new PDStateBeanList(source);
        }

        @Override
        public PDStateBeanList[] newArray(int size) {
            return new PDStateBeanList[size];
        }
    };
}
