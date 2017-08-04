package com.tanlifei.framework.main.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tanlifei.common.bean.BaseJson;
import com.tanlifei.framework.main.bean.AppUpdateBean;
import com.tanlifei.framework.main.ui.activity.AppServiceActivity;
import com.tanlifei.framework.main.ui.activity.AppServiceActivity_;
import com.tanlifei.support.constants.fixed.UrlConstants;
import com.tanlifei.support.http.ResultCallback;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.utils.AppUtils;
import com.tlf.basic.utils.NetUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * app 版本升级
 * Created by tanlifei on 16/2/22.
 */
public class CheckAppUpdateService extends IntentService {


    public CheckAppUpdateService(){
        super("CheckAppUpdateService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        appUpdate();
    }

    /**
     * 查看可否升级
     */
    public void appUpdate() {
        if(NetUtils.isConnected(CheckAppUpdateService.this)) {
            OkHttpUtils.post().url(UrlConstants.APP_VERSION_UPDATE).paramsForJson(tagList()).build().execute(new ResultCallback(this) {
                @Override
                public void onCusResponse(BaseJson response) {
                    response = date();
                    checkAppUpdate(response);
                }
            });
        }
    }

    /**
     * 查检是否可以升级
     *
     * @param baseJson
     */
    private void checkAppUpdate(BaseJson baseJson) {
        try {
            AppUpdateBean appUpdateBean = new Gson().fromJson(new Gson().toJson(baseJson.getData()), AppUpdateBean.class);
            if (null != appUpdateBean && appUpdateBean.getVersion_code() > AppUtils.getVersionCode(this)) {//是否升级
                Intent intent = new Intent(getBaseContext(), AppServiceActivity_.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putParcelable("bean", appUpdateBean);
                bundle.putString(AppServiceActivity.INTENT_TAG, "appUpdate");
                intent.putExtras(bundle);
                getApplication().startActivity(intent);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
    }


    public Map<String, Object> tagList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", "ipeiban2016");
        return map;
    }

    public BaseJson date() {
        String str = "{\n" +
                "    \"code\": \"0000\",\n" +
                "    \"msg\": \"操作成功\",\n" +
                "    \"data\": {\n" +
                "        \"version_code\": \"12\",\n" +
                "        \"version_name\": \"1.0.1\",\n" +
                "        \"name\": \"test\",\n" +
                "        \"url\":\"http://apk.hiapk.com/appdown/cc.pacer.androidapp?webparams=sviptodoc291cmNlPTkz\",\n" +
                "        \"desc\":\"灵犀Android<br>\n" +
                "                3.1.2298版产品更新计划<br>\n" +
                "                一、版本信息<br>\n" +
                "                版本号：<br>\n" +
                "                Android 3.1.2298版<br>\n" +
                "                二、产品介绍<br>\n" +
                "                实现智能操控的语音现智能操控的语现智能操控的语现智能操控的语现智能操控的语助手<br>\n" +
                "                【重要机能档案】<br>\n" +
                "                官方百度贴吧：灵犀语音助手吧<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglglingingxi<br>\n" +
                "                官方微信：yidonglglingingxi<br>\n" +
                "                官方微信：yidonglglingglingglingingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidongliglingglingngxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidongliglingglingglingngxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglinglingglinggxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidongliglingglingglingngxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglinglingglinggxi<br>\n" +
                "                官方微信：yidonglinglinggxi<br>\n" +
                "                官方微信：yidonglinglinggxi<br>\n" +
                "                官方微信：yidonglinglinggxi<br>\n" +
                "                官方微信：yidonglingglingxi<br>\n" +
                "                官方微信：yidonglinglinggxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglingxglingi<br>\n" +
                "                官方微信：yidongliglingngxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglglingiglingngxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglinglingglinggxi<br>\n" +
                "                官方微信：yidonglglinginglinggxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglglingingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglglinginglinggxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidonglglingingxi<br>\n" +
                "                官方微信：yidonglglingingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微信：yidongglinglingxi<br>\n" +
                "                官方微信：yidonglingxi<br>\n" +
                "                官方微博：@灵犀官方微博\"\n" +

                "    }\n" +
                "}";
        BaseJson baseJson = new Gson().fromJson(str, BaseJson.class);
        return baseJson;

    }


}
