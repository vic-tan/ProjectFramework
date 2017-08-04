package com.tanlifei.support.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.tanlifei.framework.main.ui.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * tanlifei
 * ResUtils
 */
public class ResUtils {


    //************************************************ Assets values************************************************************//
    public static String getFileFromAssets(String fileName) {
        if (fileName.equals("")) {
            return null;
        }

        StringBuilder s = new StringBuilder("");
        try {
            InputStreamReader in = new InputStreamReader(BaseApplication.appContext.getResources().getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //************************************************ Properties values************************************************************//

    /**
     * 根据文件名字读取内部文件
     *
     * @param
     * @return
     */
    public static Properties getProperties(String fileName) {
        if (fileName.equals("")) {
            return null;
        }
        Properties props = new Properties();
        try {
            InputStream in = BaseApplication.appContext.getAssets().open(fileName);
            props.load(in);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return props;
    }


    //************************************************ Raw values************************************************************//
    public static String getFileFromRaw(int id) {
        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(BaseApplication.appContext.getResources().openRawResource(id));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //************************************************ string values************************************************************//

    public static String getStr(int id) {
        return BaseApplication.appContext.getResources().getString(id);
    }


    //************************************************ colors values************************************************************//

    public static int getColor(int id) {
        return BaseApplication.appContext.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(int id) {
        return BaseApplication.appContext.getResources().getColorStateList(id);
    }

    //************************************************ arrays values************************************************************//

    public static String[] getStringArray(int id) {
        return BaseApplication.appContext.getResources().getStringArray(id);
    }

    public static int[] getIntArray( int id) {
        return BaseApplication.appContext.getResources().getIntArray(id);
    }

    //************************************************ dimens values************************************************************//

    public static float getDimens( int id) {
        return BaseApplication.appContext.getResources().getDimension(id);
    }

    //************************************************ Drawable values************************************************************//

    public static Drawable getDrawable(int id) {
        return BaseApplication.appContext.getResources().getDrawable(id);
    }


}
