package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/29.
 */

public class PDStateBean extends DataSupport implements Parcelable {

    /**
     * rownumber : 1
     * ID : 1
     * Name : 在用
     */

    private int rownumber;
    private String my_id;
    private String Name;

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rownumber);
        dest.writeString(this.my_id);
        dest.writeString(this.Name);
    }

    public PDStateBean() {
    }

    protected PDStateBean(Parcel in) {
        this.rownumber = in.readInt();
        this.my_id = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<PDStateBean> CREATOR = new Parcelable.Creator<PDStateBean>() {
        @Override
        public PDStateBean createFromParcel(Parcel source) {
            return new PDStateBean(source);
        }

        @Override
        public PDStateBean[] newArray(int size) {
            return new PDStateBean[size];
        }
    };
}
