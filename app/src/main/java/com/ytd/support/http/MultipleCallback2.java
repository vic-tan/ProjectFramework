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
 * 多次请求只显示同一个提示框加载基类，
 * 所有的提示框都得继承本类，
 * Created by ytd on 15/12/14.
 */
public abstract class MultipleCallback2 extends Callback<BaseJson> {

    protected Context mContext;


    /**
     * 第一个接口调用 这个方法
     *
     * @param mContext
     */
    public MultipleCallback2(Context mContext) {
        this.mContext = mContext;
        Logger.d(mContext.getClass().getName());

    }




    @Override
    public void onAfter() {
        super.onAfter();

    }


    @Override
    public BaseJson parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
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
                onCusResponse(response);
            } else {
                throw new AppException(mContext, response.getCode(), response.getMsg());
            }
        } catch (AppException e) {
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
        try {
            throw new AppException(mContext, e);
        } catch (AppException e1) {
            e1.printStackTrace();
        }
    }

    public abstract void onCusResponse(BaseJson response);


}
