package com.ytd.support.constants.fixed;


/**
 * 网络请求地址常量
 *
 * @author ytd
 * @date 2015年8月13日 上午11:30:51
 */
public class UrlConstants {
    //域名
    public static final String DOMAIN_NAME = "http://1811o171v6.iask.in";

    //TOKEN
    public static final String TOKEN = "/token";
    //仓库列表
    public static final String GETSTORELIST = "/api/Store/GetStoreList";
    //登录
    public static final String LOGIN = "/api/User/UserLogin";
    //app版本升级
    public static final String APP_VERSION_UPDATE = "/api/AppVersion/GetLastVersion";
    //获取盘点单列表
    public static final String GETINVENTORYLIST = "/api/Inventory/GetInventoryList";
    //检查盘点单状态
    public static final String GET_PDABIND = "/api/PDABind/GetPDABind";
    //获取盘点单明细
    public static final String GETINVENTORYITEMLIST = "/api/Inventory/GetInventoryItemList";
    //绑定盘点单
    public static final String PDABIND = "/api/PDABind/PDABind";
    //获取盘点单状态值
    public static final String GETPDSTATELIST = "/api/PDState/GetPDStateList";

    //回传
    public static final String UPLOADINVENTORYITEMLIST = "/api/Inventory/UploadInventoryItemList";

    //分页测试列表
    public static final String LIST_URL = DOMAIN_NAME + "/train/class/publicList";

    //分页测试列表
//    public static final String LIST_URL = DOMAIN_NAME + "/news/newsList";


}

