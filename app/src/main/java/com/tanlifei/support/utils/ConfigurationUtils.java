package com.tanlifei.support.utils;

import android.content.Context;
import android.graphics.Color;

import com.tanlifei.framework.R;
import com.tanlifei.support.constants.fixed.GlobalConstants;
import com.tanlifei.support.constants.fixed.OnOffConstants;
import com.tanlifei.support.constants.level.OnOffLevel;
import com.tanlifei.support.exception.CrashHandler;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.photoview.galleryfinal.CoreConfig;
import com.tlf.basic.photoview.galleryfinal.FunctionConfig;
import com.tlf.basic.photoview.galleryfinal.GalleryFinal;
import com.tlf.basic.photoview.galleryfinal.ThemeConfig;
import com.tlf.basic.photoview.galleryfinal.UILImageLoader;
import com.tlf.basic.photoview.galleryfinal.UILPauseOnScrollListener;
import com.tlf.basic.utils.io.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by tanlifei on 16/8/29.
 */
public class ConfigurationUtils {


    /**
     * ImageLoader初始化配置
     *
     * @param context
     * @return
     */
    public static void initImageLoader(Context context) {
        ImageLoadUtils.getInstance().init(context);
    }

    /**
     * 初始化上传选择图片器
     *
     * @param context
     */
    public static void initGalleryFinal(Context context) {
        String THEME_COLOR = "#00C15C";
        ThemeConfig GREEN = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor(THEME_COLOR))
                .setTitleBarStatusColor(THEME_COLOR)
                .setCheckNornalSelectedDrawable(context.getResources().getDrawable(R.drawable.common_gf_check_normal))
                .setCheckPressedSelectedDrawable(context.getResources().getDrawable(R.drawable.common_gf_check_pressed))
                .setFabNornalColor(Color.parseColor(THEME_COLOR))
                .setFabPressedColor(Color.parseColor("#e000C15C"))
                .setCheckSelectedColor(Color.parseColor(THEME_COLOR))
                .setCropControlColor(Color.parseColor(THEME_COLOR))
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(context, new UILImageLoader(), GREEN)
                .setFunctionConfig(new FunctionConfig.Builder().build())
                .setEditPhotoCacheFolder(new File(GlobalConstants.IMAGES_EDIT_PHOTO_PATH))////配置编辑（裁剪和旋转）功能产生的cache文件保存目录
                .setTakePhotoFolder(new File(GlobalConstants.IMAGES_TAKE_PHOTO_PATH))//设置拍照保存目录
                .setNoAnimcation(true)//关闭动画
                .setPauseOnScrollListener(new UILPauseOnScrollListener(false, true))//滑动是否加载图片设置
                .build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 初始化Okhttp
     */
    public static void initOKhttp(Context context) {
        OkHttpUtils.getInstance().debug("OkHttpUtils", true).console(ConsoleUtils.saveConsoleCache(context)).setConnectTimeout(15000, TimeUnit.MILLISECONDS);
        OkHttpUtils.getInstance().setCertificates();
    }


    /**
     * 创建文件夹
     */
    public static void initCreateFolders(Context context) {
        FileUtils.makeFolders(GlobalConstants.CRASH_PATH);//针对全局未捕获异常，保存到本志文件路径
        FileUtils.makeFolders(GlobalConstants.DOWNLOAD_PATH);//文件下载保存路径
        FileUtils.makeFolders(GlobalConstants.IMAGES_CACHE_PATH);//针对全局图片缓存路径
        FileUtils.makeFolders(GlobalConstants.IMAGES_EDIT_PHOTO_PATH);//针对全局图片缓存路径
        FileUtils.makeFolders(GlobalConstants.IMAGES_TAKE_PHOTO_PATH);//针对全局拍照编辑图片路径
    }

    /**
     * 设置是否开启全局未捕获异常
     */
    public static void initCrashHandler(Context context) {
        if (OnOffConstants.UNCAUGHT_EX_LEVEL == OnOffLevel.OFF) {//不写入
            return;
        }
        CrashHandler.getInstance().init(context).setCrashSave(true)
                .setCrashSaveTargetFolder(GlobalConstants.CRASH_PATH);
    }
}
