package com.ytd.framework.main.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.framework.main.bean.AppUpdateBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.ui.activity.AppServiceActivity;
import com.ytd.framework.main.ui.activity.AppServiceActivity_;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.HttpRequestUtils;

import static com.ytd.support.constants.fixed.UrlConstants.APP_VERSION_UPDATE;


/**
 * app 版本升级
 * Created by ytd on 16/2/22.
 */
public class CheckAppUpdateService extends IntentService {


    public CheckAppUpdateService() {
        super("CheckAppUpdateService");
    }

    IConfigPresenter configPresenter;

    @Override
    protected void onHandleIntent(Intent intent) {
        configPresenter = new ConfigPresenterImpl();
        appUpdate();
    }

    /**
     * 查看可否升级
     */
    public void appUpdate() {
        if (NetUtils.isConnected(CheckAppUpdateService.this)) {
           HttpRequestUtils.getInstance().postFormBuilder(APP_VERSION_UPDATE).build().execute(new ResultCallback(this) {
                @Override
                public void onCusResponse(BaseJson response) {
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
            if (!StringUtils.isEquals(getVersionName(this), appUpdateBean.getVersionID())) {//版本跟服务器配置不同
                String versionName[] = getVersionName(this).split("\\.");
                String updateVersion[] = appUpdateBean.getVersionID().split("\\.");
                appUpdateBean.setName(System.currentTimeMillis()+"app");
                for (int i = 0; i < versionName.length; i++) {
                    if (Integer.parseInt(versionName[i]) < Integer.parseInt(updateVersion[i])) {
                        Intent intent = new Intent(getBaseContext(), AppServiceActivity_.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bean", appUpdateBean);
                        bundle.putString(AppServiceActivity.INTENT_TAG, "appUpdate");
                        intent.putExtras(bundle);
                        getApplication().startActivity(intent);
                        return;
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

        }
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的升级号
     */
    public String getVersionName(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

}
