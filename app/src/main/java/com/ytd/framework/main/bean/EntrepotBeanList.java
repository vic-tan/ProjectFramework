package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class EntrepotBeanList extends DataSupport implements Parcelable {

    private String PageIndex;
    private String PageSize;
    private String Total;
    private List<EntrepotBean> ItemList;

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

    public List<EntrepotBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<EntrepotBean> itemList) {
        ItemList = itemList;
    }

    public EntrepotBeanList() {
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

    protected EntrepotBeanList(Parcel in) {
        this.PageIndex = in.readString();
        this.PageSize = in.readString();
        this.Total = in.readString();
        this.ItemList = in.createTypedArrayList(EntrepotBean.CREATOR);
    }

    public static final Creator<EntrepotBeanList> CREATOR = new Creator<EntrepotBeanList>() {
        @Override
        public EntrepotBeanList createFromParcel(Parcel source) {
            return new EntrepotBeanList(source);
        }

        @Override
        public EntrepotBeanList[] newArray(int size) {
            return new EntrepotBeanList[size];
        }
    };
}
