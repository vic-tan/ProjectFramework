package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class EquipmentListBean  implements Parcelable {

    private int PageIndex;
    private int PageSize;
    private int Total;
    private List<EquipmentBean> ItemList;

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<EquipmentBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<EquipmentBean> itemList) {
        ItemList = itemList;
    }

    public EquipmentListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.PageIndex);
        dest.writeInt(this.PageSize);
        dest.writeInt(this.Total);
        dest.writeTypedList(this.ItemList);
    }

    protected EquipmentListBean(Parcel in) {
        this.PageIndex = in.readInt();
        this.PageSize = in.readInt();
        this.Total = in.readInt();
        this.ItemList = in.createTypedArrayList(EquipmentBean.CREATOR);
    }

    public static final Creator<EquipmentListBean> CREATOR = new Creator<EquipmentListBean>() {
        @Override
        public EquipmentListBean createFromParcel(Parcel source) {
            return new EquipmentListBean(source);
        }

        @Override
        public EquipmentListBean[] newArray(int size) {
            return new EquipmentListBean[size];
        }
    };
}
