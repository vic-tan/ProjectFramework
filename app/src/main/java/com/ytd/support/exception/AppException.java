package com.ytd.support.exception;

import android.content.Context;

import com.ytd.framework.R;
import com.ytd.support.constants.fixed.ExceptionConstants;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;

import java.net.SocketTimeoutException;

/**
 * 本app自己定义的异常
 * Created by ytd on 15/12/14.
 */
public class AppException extends Exception {

    public static final String TAG = "AppException";

    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }


    /**
     * 请求过程正常完成,自己的后服务器返回异常处理
     *
     * @param mContext
     * @param msgCode
     */
    public AppException(Context mContext, String msgCode, String msg) {
        super(msg);
        Logger.i(TAG, "[msgCode=" + msgCode + "," + "msg=" + msg + "]");
        if (StringUtils.isEmpty(msgCode)) {
            if (StringUtils.isEquals(msgCode, ExceptionConstants.CODE_DATA_ERROR)) {
                ToastUtils.show(mContext, "数据异常");
            } else if (StringUtils.isEquals(msgCode, ExceptionConstants.CODE_VALUE_0014)) {
                ToastUtils.show(mContext, "在另一台设备登录");
            }
        } else {
            ToastUtils.show(mContext, msg);
            Logger.e(TAG, msg);
        }

    }

    /**
     * 请求过程未知异常处理,如超时,网络,数据等异常时处理
     *
     * @param mContext
     * @param e
     */
    public AppException(Context mContext, Exception e) {
        super(e.getMessage());
        if (e instanceof SocketTimeoutException) {//超时
            ToastUtils.show(mContext, "请求超时");
        } else {
            if (!NetUtils.isConnected(mContext)) {
                ToastUtils.show(mContext, mContext.getResources().getString(R.string.common_net_error));
                return;
            }else if((""+e.toString()).contains("unexpected end of stream on okhttp3.Address")){
                ToastUtils.show(mContext, "服务器忙碌.请稍候再试！！");
            }
            Logger.e(TAG, e.toString());
        }

    }

}
