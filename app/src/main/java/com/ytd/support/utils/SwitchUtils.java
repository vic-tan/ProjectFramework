package com.ytd.support.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/9/4.
 */

public class SwitchUtils extends DataSupport implements Parcelable {
    private String name;
    private boolean isOpen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.isOpen ? (byte) 1 : (byte) 0);
    }

    public SwitchUtils() {
    }

    protected SwitchUtils(Parcel in) {
        this.name = in.readString();
        this.isOpen = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SwitchUtils> CREATOR = new Parcelable.Creator<SwitchUtils>() {
        @Override
        public SwitchUtils createFromParcel(Parcel source) {
            return new SwitchUtils(source);
        }

        @Override
        public SwitchUtils[] newArray(int size) {
            return new SwitchUtils[size];
        }
    };
}
