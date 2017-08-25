package com.ytd.support.exception;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanlifei on 2017/8/25.
 */

public class ErrorBean implements Parcelable {
    private String error;
    private String error_description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
        dest.writeString(this.error_description);
    }

    public ErrorBean() {
    }

    protected ErrorBean(Parcel in) {
        this.error = in.readString();
        this.error_description = in.readString();
    }

    public static final Parcelable.Creator<ErrorBean> CREATOR = new Parcelable.Creator<ErrorBean>() {
        @Override
        public ErrorBean createFromParcel(Parcel source) {
            return new ErrorBean(source);
        }

        @Override
        public ErrorBean[] newArray(int size) {
            return new ErrorBean[size];
        }
    };
}
