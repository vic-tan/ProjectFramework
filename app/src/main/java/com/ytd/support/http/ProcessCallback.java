package com.ytd.support.http;

import android.content.Context;

import com.google.gson.Gson;
import com.tlf.basic.http.okhttp.callback.Callback;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.support.exception.AppException;
import com.ytd.support.utils.ConsoleUtils;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.ytd.support.constants.fixed.ExceptionConstants.CODE_DATA_ERROR;

/**
 * 每个方法都返回给调用者
 * Created by ytd on 15/12/14.
 */
public class ProcessCallback extends Callback<BaseJson> {
    private Context mContext;
    private HttpListener httpListener;

    public ProcessCallback(Context mContext, HttpListener httpListener) {
        this.mContext = mContext;
        Logger.d(mContext.getClass().getName());
        this.httpListener = httpListener;
    }


    @Override
    public void onAfter() {
        super.onAfter();
        httpListener.onAfter();
    }

    @Override
    public BaseJson parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        //TODO
       /* Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        BaseJson jsonBean = gson.fromJson(gson.toJson(new String(string)), BaseJson.class);*/
        BaseJson jsonBean = new Gson().fromJson(replaceId(new String(string)), BaseJson.class);
        return jsonBean;
    }

    @Override
    public void onResponse(BaseJson response) {
        try {
            if (null == response) {
                throw new AppException(mContext, CODE_DATA_ERROR, CODE_DATA_ERROR);
            }
            if (StringUtils.isEquals(response.getCode(), ConsoleUtils.randomRequest())) {
                httpListener.onCusResponse(response);
            } else {
                throw new AppException(mContext,response.getCode(), response.getMsg());
            }
        } catch (AppException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        httpListener.onBefore(request);
    }

    @Override
    public void onError(Call call, Exception e) {
        super.onError(call, e);
        httpListener.onError(call, e);
        try {
            throw new AppException(mContext, e);
        } catch (AppException e1) {
            e1.printStackTrace();
        }
    }
}
