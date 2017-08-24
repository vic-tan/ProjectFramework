package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class EntrepotBean extends DataSupport implements Parcelable {

    private String Name;
    private String Id;
    private String OtherId;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Id);
        dest.writeString(this.OtherId);
    }

    public EntrepotBean() {
    }

    protected EntrepotBean(Parcel in) {
        this.Name = in.readString();
        this.Id = in.readString();
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
