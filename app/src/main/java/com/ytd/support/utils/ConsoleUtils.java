package com.ytd.support.utils;

import android.content.Context;

import com.ytd.framework.main.bean.Console;
import com.ytd.framework.main.ui.BaseApplication;
import com.ytd.support.constants.fixed.ExceptionConstants;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.http.okhttp.bean.OkHttpConsole;
import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.AppUtils;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.RandomUtils;

import java.util.Date;

import static com.tlf.basic.http.okhttp.OkHttpUtils.okHttpConsole;


/**
 * Created by ytd on 16/11/24.
 */

public class ConsoleUtils {

    private static final String CONSOLE_OBJCIT_ID = "a838127232";
    public static final String CONSOLE_KEY = "console_key";

    public static void consoleConfigRequest(final Context mContext) {//远程后台控制器
        try {
            OkHttpConsole consoleBean = (OkHttpConsole) AppCacheUtils.getInstance(mContext).getAsObject(CONSOLE_KEY);
            if (null == consoleBean) {//初始化控制
                consoleBean = new OkHttpConsole(AppUtils.getAppName(mContext), false, 3, false, new Date().toString(), new Date().toString(), false);
                AppCacheUtils.getInstance(mContext).put(CONSOLE_KEY,consoleBean);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (NetUtils.isConnected(mContext)) {
                   /* new BmobQuery<Console>().getObject(CONSOLE_OBJCIT_ID, new QueryListener<Console>() {
                        @Override
                        public void done(Console console, BmobException e) {
                            changeToOkHttpConsoleBean(mContext, console);
                            saveConsoleCache(mContext);
                        }
                    });*/
                }
            } catch (Exception e) {
            }
        }
    }

    public static OkHttpConsole saveConsoleCache(Context mContext) {
        OkHttpConsole consoleBean = (OkHttpConsole) AppCacheUtils.getInstance(mContext).getAsObject(CONSOLE_KEY);
        if (null != consoleBean) {//初始化控制
            OkHttpUtils.getInstance().console(consoleBean);
        } else {
            consoleBean = new OkHttpConsole(AppUtils.getAppName(mContext), false, 3, false, new Date().toString(), new Date().toString(), false);
            AppCacheUtils.getInstance(mContext).put(CONSOLE_KEY, consoleBean);
        }
        return consoleBean;
    }

    public static OkHttpConsole changeToOkHttpConsoleBean(Context mContext, Console console) {
        if (null != console) {//初始化控制
            OkHttpConsole okHttpConsole = new OkHttpConsole();
            okHttpConsole.setAppName(console.getAppName());
            okHttpConsole.setIs_random(console.is_random());
            okHttpConsole.setIs_start_timer(console.is_start_timer());
            okHttpConsole.setOn_of_level(console.isOn_of_level());
            okHttpConsole.setRandom_max(console.getRandom_max());
            okHttpConsole.setTimer_end(console.getTimer_end());
            okHttpConsole.setTimer_start(console.getTimer_start());
            AppCacheUtils.getInstance(mContext).put(CONSOLE_KEY, okHttpConsole);
        }
        return okHttpConsole;

    }

    public static String randomRequest() {
        try {
            Console console = (Console) AppCacheUtils.getInstance(BaseApplication.appContext).getAsObject(CONSOLE_KEY);
            if (null == console) {
                return ExceptionConstants.CODE_SUCCEE;
            }
            if (console.isOn_of_level() && console.is_random()) {//开始
                int random = RandomUtils.getRandom(console.getRandom_max());
                if (random == 0) {
                    return "";
                } else {
                    return ExceptionConstants.CODE_SUCCEE;
                }
            }
            if (console.isOn_of_level()) {
                return "";
            }
            return ExceptionConstants.CODE_SUCCEE;
        } catch (Exception e) {
            return ExceptionConstants.CODE_SUCCEE;
        }
    }


}
