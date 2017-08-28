package com.ytd.support.http;

import android.content.Context;

import com.google.gson.Gson;
import com.tlf.basic.http.okhttp.callback.Callback;
import com.tlf.basic.utils.Logger;
import com.ytd.framework.main.bean.ConfigBean;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 提示框加载基类，
 * 所有的提示框都得继承本类，
 * Created by ytd on 15/12/14.
 */
public abstract class TokenServiceCallback extends Callback<ConfigBean> {

    protected Context mContext;

    public TokenServiceCallback(Context mContext) {
        this.mContext = mContext;
        Logger.d(mContext.getClass().getName());

    }


    @Override
    public void onAfter() {
        super.onAfter();
    }

    @Override
    public ConfigBean parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
//        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
//        ConfigBean jsonBean = gson.fromJson(gson.toJson(new String(string)), ConfigBean.class);
        ConfigBean jsonBean = new Gson().fromJson(replaceId(new String(string)), ConfigBean.class);
        return jsonBean;
    }

    @Override
    public void onResponse(ConfigBean response) {
        try {
            onCusResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);

    }

    @Override
    public void onError(Call call, Exception e) {
        super.onError(call, e);
    }

    public abstract void onCusResponse(ConfigBean response);


}
