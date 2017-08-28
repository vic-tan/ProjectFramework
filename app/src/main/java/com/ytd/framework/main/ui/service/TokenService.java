package com.ytd.framework.main.ui.service;

import android.app.IntentService;
import android.content.Intent;

import com.tlf.basic.utils.NetUtils;
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.TokenServiceCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;

import okhttp3.Call;


/**
 * Created by ytd on 16/2/22.
 */
public class TokenService extends IntentService {


    public TokenService() {
        super("CheckAppUpdateService");
    }

    IConfigPresenter configPresenter;
    ConfigBean bean;

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            configPresenter = new ConfigPresenterImpl();
            getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void getToken() {
        try {
            if (NetUtils.isConnected(TokenService.this)) {
                bean = configPresenter.find();
                if (bean == null) {
                    return;
                }
                HttpRequestUtils.getInstance().postTokenFormBuilder(bean.getUrl() + UrlConstants.TOKEN, HttpParamsUtils.getTokenParams(TokenService.this, bean.getPDAKEY()), 1)
                        .build().execute(new TokenServiceCallback(TokenService.this) {
                    @Override
                    public void onCusResponse(ConfigBean response) {
                        saveUserInfo(response);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUserInfo(ConfigBean response) {
        try {
            response.setUrl(bean.getUrl());
            response.setPDAKEY(bean.getPDAKEY());
            response.setLastDate(System.currentTimeMillis());
            configPresenter.save(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
