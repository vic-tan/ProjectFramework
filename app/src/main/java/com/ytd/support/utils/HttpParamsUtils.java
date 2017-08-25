package com.ytd.support.utils;

import android.content.Context;

import com.tlf.basic.utils.coder.MD5Coder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanlifei on 2017/8/25.
 */

public class HttpParamsUtils {


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
        map.put("PageSize", "1000");
        return map;
    }


    /**
     * 登录参数
     *
     * @return
     */
    public static Map<String, String> getLoginParams(Context mContext,String name,String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", name);
        map.put("Password", pwd);
        map.put("MACID", MacUtils.getWlanMac(mContext));
        return map;
    }


}
