package com.ytd.framework.equipment.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.ytd.support.utils.GsonJsonUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.tlf.basic.http.okhttp.callback.Callback.replaceId;

/**
 * Created by tanlifei on 2017/8/8.
 */

public class PropertyBean extends DataSupport implements Parcelable {


    private String my_id;//资产ID;
    private String title;//名称单名称
    private String name;//盘点人
    private String phone;//手机号
    private String price;//价格区间
    private String area;//盘点区域
    private String address;//盘点地址
    private String start_data;//盘点开始时间
    private String end_data;//盘点结束时间
    private String totalNum;//总设备数据
    private String finshNum;//完成数据
    private String status;//盘点状态
    private String start_property;//资产原值；
    private String end_property;//资产净值；
    private String updateload;//盘点单是否上传
    private String loginName;

    private List<EquipmentBean> eqList;

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

    public List<EquipmentBean> getEqList() {
        return eqList;
    }

    public void setEqList(List<EquipmentBean> eqList) {
        this.eqList = eqList;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_property() {
        return start_property;
    }

    public void setStart_property(String start_property) {
        this.start_property = start_property;
    }

    public String getEnd_property() {
        return end_property;
    }

    public void setEnd_property(String end_property) {
        this.end_property = end_property;
    }

    public String getUpdateload() {
        return updateload;
    }

    public void setUpdateload(String updateload) {
        this.updateload = updateload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_data() {
        return start_data;
    }

    public void setStart_data(String start_data) {
        this.start_data = start_data;
    }

    public String getEnd_data() {
        return end_data;
    }

    public void setEnd_data(String end_data) {
        this.end_data = end_data;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getFinshNum() {
        return finshNum;
    }

    public void setFinshNum(String finshNum) {
        this.finshNum = finshNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PropertyBean() {
    }


    public static PropertyBean addTestBean() {
        String jsonStr ="{\n" +
                "    \"id\": \"8a9a975b5dbcbe9c015dbd05f8700085\",\n" +
                "    \"title\": \"报告显示：去年中国互联网百强企业收入破万亿元\",\n" +
                "    \"name\": \"汪科长\",\n" +
                "    \"phone\": \"13823297564\",\n" +
                "    \"price\": \"一万元以下\",\n" +
                "    \"area\": \"中心仓库\",\n" +
                "    \"address\": \"中心实现室\",\n" +
                "    \"start_data\": \"2017年05月12日\",\n" +
                "    \"end_data\": \"2017年05月10日\",\n" +
                "    \"totalNum\": \"300\",\n" +
                "    \"finshNum\": \"200\",\n" +
                "    \"status\": \"1\",\n" +
                "    \"start_property\": \"1002323.123\",\n" +
                "    \"end_property\": \"983673.343\",\n" +
                "    \"updateload\": \"1\"\n" +
                "}";
        return new Gson().fromJson(replaceId(jsonStr), PropertyBean.class);
    }

    public static List<PropertyBean> addTestListBean(){
        String jsonStr ="[\n" +
                "    {\n" +
                "        \"id\": \"8a9a975b5dbcbe9c015dbd05f8700085\",\n" +
                "        \"title\": \"去年中国互联网百强企业收入破万亿元\",\n" +
                "        \"name\": \"汪科长盘点\",\n" +
                "        \"phone\": \"13823297564\",\n" +
                "        \"price\": \"一万元以下\",\n" +
                "        \"area\": \"中心仓库\",\n" +
                "        \"address\": \"中心实现室\",\n" +
                "        \"start_data\": \"2017年05月12日\",\n" +
                "        \"end_data\": \"2017年05月10日\",\n" +
                "        \"totalNum\": \"300\",\n" +
                "        \"finshNum\": \"200\",\n" +
                "        \"status\": \"1\",\n" +
                "        \"start_property\": \"1002323.123\",\n" +
                "        \"end_property\": \"983673.343\",\n" +
                "        \"updateload\": \"1\",\n" +
                "        \"eqList\": [\n" +
                "            {\n" +
                "                \"id\": \"324EWa975b5dbcbefdd9c015WDdbd05f898w\",\n" +
                "                \"title\": \"血管仿真模型1\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"eqType\": \"HT-EW-DAFALFSDS\",\n" +
                "                \"eqId\": \"983673q23489341\",\n" +
                "                \"useAddress\": \"中心实现室\",\n" +
                "                \"start_data\": \"2017年05月10日\",\n" +
                "                \"propertyStatus\": \"在用\",\n" +
                "                \"unitName\": \"台\",\n" +
                "                \"eqStandard\": \"GD/DEFD\",\n" +
                "                \"saveAddress\": \"深圳市\",\n" +
                "                \"start_property\": \"1002323.123\",\n" +
                "                \"end_property\": \"983673.343\",\n" +
                "                \"old_property\": \"83673.343\",\n" +
                "                \"barCode\": \"da12321se321\",\n" +
                "                \"lookStatus\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DFSAFa975b5dewebrewtcbe9c015Ddbd05f898w\",\n" +
                "                \"title\": \"血管仿真模型2\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"eqType\": \"HT-EW-DAFALFS\",\n" +
                "                \"eqId\": \"983673q23489341\",\n" +
                "                \"useAddress\": \"中心实现室\",\n" +
                "                \"start_data\": \"2017年06月10日\",\n" +
                "                \"propertyStatus\": \"在用\",\n" +
                "                \"unitName\": \"台\",\n" +
                "                \"eqStandard\": \"GD/DEFD\",\n" +
                "                \"saveAddress\": \"深圳市\",\n" +
                "                \"start_property\": \"1002323.123\",\n" +
                "                \"end_property\": \"983673.343\",\n" +
                "                \"old_property\": \"83673.343\",\n" +
                "                \"barCode\": \"da12321se321\",\n" +
                "                \"lookStatus\": \"1\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"DFADF75b5dbcbe9DSDFSc015dbd0FSS0085\",\n" +
                "        \"title\": \"互联网百强企业收入破万亿元\",\n" +
                "        \"name\": \"李科长盘点\",\n" +
                "        \"phone\": \"13823297572\",\n" +
                "        \"price\": \"十万元以下\",\n" +
                "        \"area\": \"中心仓库\",\n" +
                "        \"address\": \"中心实现室\",\n" +
                "        \"start_data\": \"2017年03月12日\",\n" +
                "        \"end_data\": \"2017年05月10日\",\n" +
                "        \"totalNum\": \"440\",\n" +
                "        \"finshNum\": \"230\",\n" +
                "        \"status\": \"0\",\n" +
                "        \"start_property\": \"1002323.123\",\n" +
                "        \"end_property\": \"983673.343\",\n" +
                "        \"updateload\": \"0\",\n" +
                "        \"eqList\": [\n" +
                "            {\n" +
                "                \"id\": \"23a975b5dbcfdDFAFASDFfbe9c015WDdbdFASD\",\n" +
                "                \"title\": \"血管仿真模型3\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"eqType\": \"HT-EW-DAFALFSDS\",\n" +
                "                \"eqId\": \"983673q23489341\",\n" +
                "                \"useAddress\": \"中心实现室\",\n" +
                "                \"start_data\": \"2017年05月10日\",\n" +
                "                \"propertyStatus\": \"在用\",\n" +
                "                \"unitName\": \"台\",\n" +
                "                \"eqStandard\": \"GD/DEFD\",\n" +
                "                \"saveAddress\": \"深圳市\",\n" +
                "                \"start_property\": \"1002323.123\",\n" +
                "                \"end_property\": \"983673.343\",\n" +
                "                \"old_property\": \"83673.343\",\n" +
                "                \"barCode\": \"da12321se321\",\n" +
                "                \"lookStatus\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"124a975b5dbcbe9DAFASDFc0ds15DdDFASFD\",\n" +
                "                \"title\": \"血管仿真模型4\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"eqType\": \"HT-EW-DAFALFS\",\n" +
                "                \"eqId\": \"983673q23489341\",\n" +
                "                \"useAddress\": \"中心实现室\",\n" +
                "                \"start_data\": \"2017年06月10日\",\n" +
                "                \"propertyStatus\": \"在用\",\n" +
                "                \"unitName\": \"台\",\n" +
                "                \"eqStandard\": \"GD/DEFD\",\n" +
                "                \"saveAddress\": \"深圳市\",\n" +
                "                \"start_property\": \"1002323.123\",\n" +
                "                \"end_property\": \"983673.343\",\n" +
                "                \"old_property\": \"83673.343\",\n" +
                "                \"barCode\": \"da12321se321\",\n" +
                "                \"lookStatus\": \"1\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        return GsonJsonUtils.fromJsonArray(replaceId(jsonStr), PropertyBean.class);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.my_id);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.price);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.start_data);
        dest.writeString(this.end_data);
        dest.writeString(this.totalNum);
        dest.writeString(this.finshNum);
        dest.writeString(this.status);
        dest.writeString(this.start_property);
        dest.writeString(this.end_property);
        dest.writeString(this.updateload);
        dest.writeString(this.loginName);
        dest.writeTypedList(this.eqList);
    }

    protected PropertyBean(Parcel in) {
        this.my_id = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.price = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.start_data = in.readString();
        this.end_data = in.readString();
        this.totalNum = in.readString();
        this.finshNum = in.readString();
        this.status = in.readString();
        this.start_property = in.readString();
        this.end_property = in.readString();
        this.updateload = in.readString();
        this.loginName = in.readString();
        this.eqList = in.createTypedArrayList(EquipmentBean.CREATOR);
    }

    public static final Creator<PropertyBean> CREATOR = new Creator<PropertyBean>() {
        @Override
        public PropertyBean createFromParcel(Parcel source) {
            return new PropertyBean(source);
        }

        @Override
        public PropertyBean[] newArray(int size) {
            return new PropertyBean[size];
        }
    };
}
