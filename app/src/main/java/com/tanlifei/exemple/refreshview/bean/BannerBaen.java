package com.tanlifei.exemple.refreshview.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tanlifei on 16/8/16.
 */
public class BannerBaen implements Parcelable {

    /**
     * my_id : 8a987d5155b47f520155be577d260179
     * name : 2016《培训》直达号--唯品大学（广州站）
     * image : http://www.ipeiban.com.cn/mstatic/M00/00/0A/Chj90Vd84M2EPAtdAAAAAL8WRus720.jpg
     */

    private String my_id;
    private String name;
    private String image;

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.my_id);
        dest.writeString(this.name);
        dest.writeString(this.image);
    }

    public BannerBaen() {
    }

    protected BannerBaen(Parcel in) {
        this.my_id = in.readString();
        this.name = in.readString();
        this.image = in.readString();
    }

    public static final Creator<BannerBaen> CREATOR = new Creator<BannerBaen>() {
        @Override
        public BannerBaen createFromParcel(Parcel source) {
            return new BannerBaen(source);
        }

        @Override
        public BannerBaen[] newArray(int size) {
            return new BannerBaen[size];
        }
    };
}
