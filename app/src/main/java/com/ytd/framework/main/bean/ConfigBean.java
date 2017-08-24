package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by tanlifei on 2017/8/17.
 */

public class ConfigBean extends DataSupport implements Parcelable {

    private String Url;
    private String PDAKEY;
    private String access_token;
    private String token_type;
    private String expires_in;
    private boolean isFrist = true;


    public boolean isFrist() {
        return isFrist;
    }

    public void setFrist(boolean frist) {
        isFrist = frist;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getPDAKEY() {
        return PDAKEY;
    }

    public void setPDAKEY(String PDAKEY) {
        this.PDAKEY = PDAKEY;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public ConfigBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Url);
        dest.writeString(this.PDAKEY);
        dest.writeString(this.access_token);
        dest.writeString(this.token_type);
        dest.writeString(this.expires_in);
        dest.writeByte(this.isFrist ? (byte) 1 : (byte) 0);
    }

    protected ConfigBean(Parcel in) {
        this.Url = in.readString();
        this.PDAKEY = in.readString();
        this.access_token = in.readString();
        this.token_type = in.readString();
        this.expires_in = in.readString();
        this.isFrist = in.readByte() != 0;
    }

    public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
        @Override
        public ConfigBean createFromParcel(Parcel source) {
            return new ConfigBean(source);
        }

        @Override
        public ConfigBean[] newArray(int size) {
            return new ConfigBean[size];
        }
    };
}
