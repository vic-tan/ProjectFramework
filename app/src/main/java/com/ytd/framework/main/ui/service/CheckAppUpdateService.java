package com.ytd.framework.main.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ytd.common.bean.BaseJson;
import com.ytd.framework.main.bean.AppUpdateBean;
import com.ytd.framework.main.ui.activity.AppServiceActivity;
import com.ytd.framework.main.ui.activity.AppServiceActivity_;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.ResultCallback;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.utils.AppUtils;
import com.tlf.basic.utils.NetUtils;
import com.ytd.support.utils.DomainUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * app 版本升级
 * Created by ytd on 16/2/22.
 */
public class CheckAppUpdateService extends IntentService {


    public CheckAppUpdateService() {
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
        if (NetUtils.isConnected(CheckAppUpdateService.this)) {
            OkHttpUtils.post().url(DomainUtils.getInstance().domain() + UrlConstants.APP_VERSION_UPDATE).paramsForJson(tagList()).build().execute(new ResultCallback(this) {
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
                "    \"Code\": 100,\n" +
                "    \"Msg\": \"请求成功！\",\n" +
                "    \"IsSuccess\": true,\n" +
                "    \"Data\": {\n" +
                "        \"ID\": 1,\n" +
                "        \"Url\": \"www.baidu.com/01\",\n" +
                "        \"VersionID\": \"1.1.11\",\n" +
                "        \"Memo\": \"测试版0101\",\n" +
                "        \"AddDate\": \"2017-08-21T15:55:09\"\n" +
                "    }\n" +
                "}";
        BaseJson baseJson = new Gson().fromJson(str, BaseJson.class);
        return baseJson;

    }


}
