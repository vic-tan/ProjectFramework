package com.ytd.support.utils;

import android.content.Context;

import com.tlf.basic.utils.ToastUtils;
import com.ytd.framework.R;

/**
 * Created by tanlifei on 2017/8/5.
 */

public class UnFinshUtils {
    public static void unFinshToast(Context context){
        ToastUtils.show(context, R.string.wait);
    }
}
