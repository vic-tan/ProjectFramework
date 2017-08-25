package com.ytd.support.http;

import android.content.Context;

import com.google.gson.Gson;
import com.tlf.basic.http.okhttp.callback.Callback;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.framework.R;
import com.ytd.support.exception.AppException;
import com.ytd.support.utils.ConsoleUtils;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.ytd.support.constants.fixed.ExceptionConstants.CODE_DATA_ERROR;


/**
 * 提示框加载基类，
 * 所有的提示框都得继承本类，
 * Created by ytd on 15/12/14.
 */
public abstract class DialogCallback extends Callback<BaseJson> {

    protected KProgressHUD hud;
    protected Context mContext;

    public DialogCallback(Context mContext) {
        this.mContext = mContext;
        Logger.d(mContext.getClass().getName());
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(mContext.getResources().getString(R.string.common_dialog_loading))
                .setCancellable(true);
    }


    @Override
    public void onAfter() {
        super.onAfter();
        hud.dismiss();
    }

    @Override
    public BaseJson parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        BaseJson jsonBean = new Gson().fromJson(replaceId(new String(string)), BaseJson.class);
        return jsonBean;
    }

    @Override
    public void onResponse(BaseJson response) {
        try {
            if (null == response) {
                throw new AppException(mContext,CODE_DATA_ERROR, CODE_DATA_ERROR);
            }
            if (StringUtils.isEquals(response.getCode(), ConsoleUtils.randomRequest())){
                onCusResponse(response);
            }else{
                throw new AppException(mContext,response.getCode(), response.getMsg());
            }
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        if(null!=hud)
            hud.show();
    }

    @Override
    public void onError(Call call, Exception e) {
        super.onError(call, e);
        if(null!=hud && hud.isShowing())
            hud.dismiss();
        try {
            throw new AppException(mContext, e);
        } catch (AppException e1) {
            e1.printStackTrace();
        }
    }

    public abstract void onCusResponse(BaseJson response);


}
