package com.ytd.support.http;

import android.content.Context;

import com.google.gson.Gson;
import com.tlf.basic.http.okhttp.callback.Callback;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.utils.Logger;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.ConfigBean;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 提示框加载基类，
 * 所有的提示框都得继承本类，
 * Created by ytd on 15/12/14.
 */
public abstract class TokenCallback extends Callback<ConfigBean> {

    protected KProgressHUD hud;
    protected Context mContext;

    public TokenCallback(Context mContext) {
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
    public ConfigBean parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
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
        if(null!=hud)
            hud.show();
    }

    @Override
    public void onError(Call call, Exception e) {
        super.onError(call, e);
        if(null!=hud && hud.isShowing())
            hud.dismiss();
    }

    public abstract void onCusResponse(ConfigBean response);


}
