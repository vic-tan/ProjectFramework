package com.tanlifei.framework.main.ui.service;

import android.app.IntentService;
import android.content.Intent;

import com.tanlifei.framework.R;
import com.tanlifei.framework.main.bean.AppUpdateBean;
import com.tanlifei.support.constants.fixed.GlobalConstants;
import com.tanlifei.support.http.FileCallBack;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.ToastUtils;

import java.io.File;

import okhttp3.Call;


/**
 * app 版本升级
 * Created by tanlifei on 16/2/22.
 */
public class AppDownloadService extends IntentService {

    private final String TAG = AppDownloadService.this.getClass().getSimpleName();
    boolean isStart = true;//是否为开始下载标识
    private int count = 1;//当前请求下载接失败数据
    private int requestCount = 5;//请求下载接口失败总次将不在请求

    public AppDownloadService() {
        super("AppDownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intentReceiver = new Intent(AppDownloadService.this, AppUpdateBroadcastReceiver.class);//注册下载通知栏广播监听者
        intent.setAction(AppUpdateBroadcastReceiver.APP_UPDATE_ACTION);//广播action 标识
        appDownload((AppUpdateBean) intent.getParcelableExtra("bean"), intentReceiver);
    }


    /**
     * 下载app
     *
     * @param updateBean
     */
    public void appDownload(final AppUpdateBean updateBean, final Intent intentReceiver) {
        if (NetUtils.isConnected(AppDownloadService.this)) {
            OkHttpUtils.get().url(updateBean.getUrl()).build().execute(new FileCallBack(GlobalConstants.DOWNLOAD_PATH, updateBean.getName() + ".apk") {
                @Override
                public void inProgress(float progress, long total) {
                    if (isStart) {//准备下载
                        Logger.d(TAG, "---start download 准备下载 ---");
                        intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG, AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG_START);
                        isStart = false;
                    } else {//正在下载
                        intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG, AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG_PROGRESS);
                    }
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_TOTAL, total);
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_PROGRESS, progress);
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_NAME, updateBean.getName());
                    sendBroadcast(intentReceiver);//发送下载信息广播
                }

                @Override
                public void onResponse(File response) {
                    //下载完成
                    Logger.d(TAG, "---start download 下载成功 ---");
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG, AppUpdateBroadcastReceiver.APP_DOWNLOAD_TAG_DONE);
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_INSTALL_URL, GlobalConstants.DOWNLOAD_PATH + "/" + updateBean.getName() + ".apk");
                    intentReceiver.putExtra(AppUpdateBroadcastReceiver.APP_DOWNLOAD_NAME, updateBean.getName());
                    sendBroadcast(intentReceiver);
                }

                @Override
                public void onError(Call call, Exception e) {
                    super.onError(call, e);
                    e.printStackTrace();
                    if (count <= requestCount) {//少于规定次数将继续请求下载
                        appDownload(updateBean, intentReceiver);//第一次升级失败后在次请求下载
                        count = count + 1;
                    } else {
                        ToastUtils.show(AppDownloadService.this, AppDownloadService.this.getResources().getString(R.string.app_update_socket_timeout));
                    }
                    Logger.d(TAG, "--- download onError ---" + count + "---");
                }
            });
        } else {
            ToastUtils.show(AppDownloadService.this, AppDownloadService.this.getResources().getString(R.string.common_net_error));
        }
    }
}
