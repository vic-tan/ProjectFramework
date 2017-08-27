package com.ytd.support.utils;

import android.content.Context;

import com.tlf.basic.utils.coder.MD5Coder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanlifei on 2017/8/25.
 */

public class HttpParamsUtils {

    private static  String PAGESIZE = "5000";

    /**
     * 获取TOKEN参数
     *
     * @return
     */
    public static Map<String, String> getTokenParams(Context mContext, String pwd) {
        String rpwd = (MD5Coder.getMD5Code("PDA" + pwd) + "").toUpperCase();
        String mac = MacUtils.getWlanMac(mContext);
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "password");
        map.put("userName", mac);
        map.put("password", rpwd);
        return map;
    }

    /**
     * 仓库列表参数
     *
     * @return
     */
    public static Map<String, String> getStorelistParams() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "1");
        map.put("PageSize", PAGESIZE);
        return map;
    }


    /**
     * 登录参数
     *
     * @return
     */
    public static Map<String, String> getLoginParams(Context mContext, String name, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", name);
        map.put("Password", pwd);
        map.put("MACID", MacUtils.getWlanMac(mContext));
        return map;
    }


    /**
     * 获取盘点单列表
     *
     * @return
     */
    public static Map<String, String> getInventoryListParams(String Id, String StoreId) {
        Map<String, String> map = new HashMap<>();
        map.put("Id", Id);
        map.put("StoreId", StoreId);
        map.put("State", "0");
        map.put("PageIndex", "1");
        map.put("PageSize", PAGESIZE);
        return map;
    }

    /**
     * 检查盘点单状态
     *
     * @return
     */
    public static Map<String, String> getPDStateListParams(String PDDH,String EquId) {
        Map<String, String> map = new HashMap<>();
        map.put("EquId", EquId);
        map.put("PDDH", PDDH);
        return map;
    }

    /**
     * 获取盘点单明细
     *
     * @return
     */
    public static Map<String, String> getInventoryItemListParams(int PageIndex,String PDDH) {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", PageIndex+"");
        map.put("PageSize", PAGESIZE);
        map.put("Id", PDDH);
        return map;
    }

    /**
     * 绑定盘点单
     *
     * @return
     */
    public static Map<String, String> getPDABindParams(String PDDH,String EquId,String InputUserId) {
        Map<String, String> map = new HashMap<>();
        map.put("PDDH", PDDH);
        map.put("EquId", EquId);
        map.put("InputUserId", InputUserId);
        return map;
    }


}
