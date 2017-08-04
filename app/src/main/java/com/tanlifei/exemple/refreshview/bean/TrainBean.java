package com.tanlifei.exemple.refreshview.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tlf.basic.utils.StringUtils;


/**
 * Created by tanlifei on 16/5/17.
 */
public class TrainBean implements Parcelable {


    /**
     * my_id : 8a2b49135480dc6a01549ebb6f0c0396
     * cover : http://www.ipeiban.com.cn/mstatic/M00/00/0C/CqIywlczDFKEJZLBAAAAAHML7_w430.JPG
     * applied_count : 0
     * begin_time : 2016-05-26 00:00:00
     * end_time : 2016-05-26 00:00:00
     * name : 2016《培训》直达号--宝钢人才开发院(上海）
     * standard_fee : 1800
     * image_path : M00/00/0B/CqIywlcy3rCEMd7vAAAAANMh_k0550.jpg
     */

    private String my_id;
    private String cover;
    private int applied_count;
    private String begin_time;
    private String end_time;
    private Double standard_fee;
    private String image_path;
    private String status;//班级状态0-待确认 1-待审核 2-审核通过
    private String address;
    private String training_count;//培训人数，班级中确定的人数
    private String name;//班级名称

    public static Creator<TrainBean> getCREATOR() {
        return CREATOR;
    }

    public String getStatus() {
        return StringUtils.isEmpty(status) ? "-1" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraining_count() {
        return training_count;
    }

    public void setTraining_count(String training_count) {
        this.training_count = training_count;
    }

    public TrainBean(String my_id) {
        this.my_id = my_id;
    }

    public TrainBean() {
    }


    @Override
    public String toString() {
        return "TrainBean{" +
                "address='" + address + '\'' +
                ", my_id='" + my_id + '\'' +
                ", cover='" + cover + '\'' +
                ", applied_count=" + applied_count +
                ", begin_time='" + begin_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", name='" + name + '\'' +
                ", standard_fee=" + standard_fee +
                ", image_path='" + image_path + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getApplied_count() {
        return applied_count;
    }

    public void setApplied_count(int applied_count) {
        this.applied_count = applied_count;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStandard_fee() {
        return standard_fee;
    }

    public void setStandard_fee(Double standard_fee) {
        this.standard_fee = standard_fee;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.my_id);
        dest.writeString(this.cover);
        dest.writeInt(this.applied_count);
        dest.writeString(this.begin_time);
        dest.writeString(this.end_time);
        dest.writeValue(this.standard_fee);
        dest.writeString(this.image_path);
        dest.writeString(this.status);
        dest.writeString(this.address);
        dest.writeString(this.training_count);
        dest.writeString(this.name);
    }

    protected TrainBean(Parcel in) {
        this.my_id = in.readString();
        this.cover = in.readString();
        this.applied_count = in.readInt();
        this.begin_time = in.readString();
        this.end_time = in.readString();
        this.standard_fee = (Double) in.readValue(Double.class.getClassLoader());
        this.image_path = in.readString();
        this.status = in.readString();
        this.address = in.readString();
        this.training_count = in.readString();
        this.name = in.readString();
    }

    public static final Creator<TrainBean> CREATOR = new Creator<TrainBean>() {
        @Override
        public TrainBean createFromParcel(Parcel source) {
            return new TrainBean(source);
        }

        @Override
        public TrainBean[] newArray(int size) {
            return new TrainBean[size];
        }
    };
}
