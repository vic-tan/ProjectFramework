package com.ytd.framework.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ytd on 16/8/19.
 */
public class AppUpdateBean implements Parcelable {
    /**
     * version_code : 12
     * version_name : 1.0.1
     * name : 灵犀语音助手
     * url : http://gh-game.oss-cn-hangzhou.aliyuncs.com/1435814701749842.apk
     * desc : 灵犀Android
     */

    private  int my_ID;
    private int version_code;
    private String VersionID;
    private String name;
    private String Url;
    private String Memo;
    private String AddDate;

    public int getMy_ID() {
        return my_ID;
    }

    public void setMy_ID(int my_ID) {
        this.my_ID = my_ID;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersionID() {
        return VersionID;
    }

    public void setVersionID(String versionID) {
        VersionID = versionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.my_ID);
        dest.writeInt(this.version_code);
        dest.writeString(this.VersionID);
        dest.writeString(this.name);
        dest.writeString(this.Url);
        dest.writeString(this.Memo);
        dest.writeString(this.AddDate);
    }

    public AppUpdateBean() {
    }

    protected AppUpdateBean(Parcel in) {
        this.my_ID = in.readInt();
        this.version_code = in.readInt();
        this.VersionID = in.readString();
        this.name = in.readString();
        this.Url = in.readString();
        this.Memo = in.readString();
        this.AddDate = in.readString();
    }

    public static final Creator<AppUpdateBean> CREATOR = new Creator<AppUpdateBean>() {
        @Override
        public AppUpdateBean createFromParcel(Parcel source) {
            return new AppUpdateBean(source);
        }

        @Override
        public AppUpdateBean[] newArray(int size) {
            return new AppUpdateBean[size];
        }
    };
}
