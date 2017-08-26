package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class EntrepotBean extends DataSupport implements Parcelable {

    private String Name;
    @Column(ignore = true)
    private String Id;
    private String StoreId;
    private String OtherId;

    public String getStoreId() {
        return StoreId;
    }


    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOtherId() {
        return OtherId;
    }

    public void setOtherId(String otherId) {
        OtherId = otherId;
    }

    public EntrepotBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Id);
        dest.writeString(this.StoreId);
        dest.writeString(this.OtherId);
    }

    protected EntrepotBean(Parcel in) {
        this.Name = in.readString();
        this.Id = in.readString();
        this.StoreId = in.readString();
        this.OtherId = in.readString();
    }

    public static final Creator<EntrepotBean> CREATOR = new Creator<EntrepotBean>() {
        @Override
        public EntrepotBean createFromParcel(Parcel source) {
            return new EntrepotBean(source);
        }

        @Override
        public EntrepotBean[] newArray(int size) {
            return new EntrepotBean[size];
        }
    };
}
