package com.tanlifei.uikit.actionbar;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.tanlifei.framework.R;
import com.tanlifei.support.utils.ResUtils;
import com.tlf.basic.utils.StringUtils;

/**
 * Created by tanlifei on 16/8/12.
 */
public class BaseActionBarView {

    /*<!-- 标题及背影设置 -->
    <attr name="abv_backgroundColor" format="color" /><!--actionbar 背影颜色-->
    <attr name="abv_title_text" format="string" /><!--actionbar 标题-->
    <attr name="abv_title_textColor" format="color" /><!--actionbar 标题颜色-->
    <attr name="abv_title_textSize" format="dimension" /><!--actionbar 标题字体大小 单位 px-->

    <!-- 返回设置 -->
    <attr name="abv_back_text" format="string" /><!--actionbar 返回文字-->
    <attr name="abv_back_textColor" format="color" /><!--actionbar 返回文字颜色-->
    <attr name="abv_back_textSize" format="dimension" /><!--actionbar 返回文字字体大小 单位 px-->
    <attr name="abv_back_text_dimiss" format="boolean" /><!--actionbar 返回文字是否显示-->
    <attr name="abv_back_iconDrawableDimiss" format="boolean" /><!--actionbar 返回图标是否显示 -->
    <attr name="abv_back_dimiss" format="boolean" /><!--actionbar 返回文字和图标是否显示 -->

    <!-- 右边操作view ,提供两个文字view 和两个图片view, 它们在同一LinearLayout 上，顺序从左到右为abv_opt_right_text ,abv_opt_right_iconDrawable,abv_opt_left_text,abv_opt_left_iconDrawable -->
    <attr name="abv_opt_right_text" format="string" /><!--actionbar 操作文字1-->
    <attr name="abv_opt_right_textColor" format="color" /><!--actionbar 操作文字1颜色-->
    <attr name="abv_opt_right_textSize" format="dimension" /><!--actionbar 操作文字1字体大小 单位 px-->
    <attr name="abv_opt_left_text" format="string" /><!--actionbar 操作文字2-->
    <attr name="abv_opt_left_textColor" format="color" /><!--actionbar 操作文字2颜色-->
    <attr name="abv_opt_left_textSize" format="dimension" /><!--actionbar 操作文字2字体大小 单位 px -->
    <attr name="abv_opt_right_iconDrawable_normol" format="reference" /><!--actionbar 操作图片1 未选中时显示-->
    <attr name="abv_opt_right_iconDrawable_pressed" format="reference" /><!--actionbar 操作图片1 选中时显示-->
    <attr name="abv_opt_left_iconDrawable_normol" format="reference" /><!--actionbar 操作图片2 未选中时显示-->
    <attr name="abv_opt_left_iconDrawable_pressed" format="reference" /><!--actionbar 操作图片2 选中时显示-->*/

    private View mView;//布局

    /*--------------- 默认常量 --------------*/

    /**
     * 字体体颜色 默认白色
     */
    private int DEFAULT_COLOR = ResUtils.getColor(R.color.common_actionbar_title_normal);
    /**
     * 默认背影颜色红色
     */
    private int DEFAULT_BACKGROUNDCOLOR = ResUtils.getColor(R.color.theme_color);
    /**
     * 默标题字体大小 单位 px
     */
    private float DEFAULT_TITLE_TEXT_SIZE = ResUtils.getDimens(R.dimen.common_text_size_actionbar_title);

    /**
     * 默认返回字体大小 单位 px
     */
    private float DEFAULT_BACK_TEXT_SIZE = ResUtils.getDimens(R.dimen.common_text_size_actionbar_opt);

    /**
     * 默认操作字体大小 单位 px
     */
    private float DEFAULT_OPT_TEXT_SIZE = ResUtils.getDimens(R.dimen.common_text_size_actionbar_opt);



    /*--------------- 标题及背影设置 --------------*/

    /**
     * 背影颜色 默认红色
     */
    protected int backgroundColor;

    /**
     * 标题
     */
    protected String title_text;

    /**
     * 标题字体颜色 默认白色
     */
    protected int title_textColor;

    /**
     * 标题字体大小 单位 px
     */
    protected float title_textSize;


     /*--------------- 返回设置 --------------*/

    /**
     * 返回文字 默认显示返回
     */
    protected String back_text = "返回";

    /**
     * 返回文字颜色 默认白色
     */
    protected ColorStateList back_textColor;

    /**
     * 返回文字字体大小 单位 px
     */
    protected float back_textSize;

    /**
     * 返回文字是否显示 默认显示
     */
    protected boolean back_text_dimiss = false;

   /* *//**
     * 返回图标是否显示 默认显示
     *//*
    protected boolean back_iconDrawableDimiss = false;*/

    /**
     * 返回文字和图标是否显示 默认显示
     */
    protected boolean back_dimiss = false;

     /*--------------- 操作设置 --------------*/

    /**
     * 操作文字1 默认为空，为空时不显示
     */
    protected String opt_right_text;

    /**
     * 操作文字1颜色 默认白色
     */
    protected ColorStateList opt_right_textColor;

    /**
     * 操作文字1字体大小 单位 px
     */
    protected float opt_right_textSize;

    /**
     * 操作文字2 默认为空，为空时不显示
     */
    protected String opt_left_text;

    /**
     * 操作文字2颜色 默认白色
     */
    protected ColorStateList opt_left_textColor;

    /**
     * 操作文字2字体大小 单位 px
     */
    protected float opt_left_textSize;

    /**
     * 操作图片1 未选中时显示图片 为null时不显示
     */
    protected Drawable opt_right_iconDrawable_normol;

    /**
     * 操作图片1 选中时显示图片 为null时不显示
     */
    protected Drawable opt_right_iconDrawable_pressed;

    /**
     * 操作图片2 未选中时显示图片 为null时不显示
     */
    protected Drawable opt_left_iconDrawable_normol;

    /**
     * 操作图片2 选中时显示图片 为null时不显示
     */
    protected Drawable opt_left_iconDrawable_pressed;

    public BaseActionBarView(View view, TypedArray typedArray) {
        mView = view;

        /*--------------- 标题及背影设置 --------------*/
        backgroundColor = typedArray.getColor(R.styleable.ActionBarView_abv_backgroundColor, DEFAULT_BACKGROUNDCOLOR);
        title_text = typedArray.getString(R.styleable.ActionBarView_abv_title_text);

        title_textColor = typedArray.getColor(R.styleable.ActionBarView_abv_title_textColor, DEFAULT_COLOR);
        title_textSize = typedArray.getDimensionPixelSize(R.styleable.ActionBarView_abv_title_textSize, (int) DEFAULT_TITLE_TEXT_SIZE);


         /*--------------- 返回设置 --------------*/
        back_text = typedArray.getString(R.styleable.ActionBarView_abv_back_text);
        if(StringUtils.isEmpty(back_text)){
            back_text = "返回";
        }
        back_textColor = typedArray.getColorStateList(R.styleable.ActionBarView_abv_back_textColor);
        back_textSize = typedArray.getDimensionPixelSize(R.styleable.ActionBarView_abv_back_textSize, (int)DEFAULT_BACK_TEXT_SIZE);
        back_text_dimiss = typedArray.getBoolean(R.styleable.ActionBarView_abv_back_text_dimiss, false);
       // back_iconDrawableDimiss = typedArray.getBoolean(R.styleable.ActionBarView_abv_back_iconDrawableDimiss, false);
        ;
        back_dimiss = typedArray.getBoolean(R.styleable.ActionBarView_abv_back_dimiss, false);


         /*--------------- 操作设置 --------------*/
        opt_right_text = typedArray.getString(R.styleable.ActionBarView_abv_opt_right_text);
        opt_right_textColor = typedArray.getColorStateList(R.styleable.ActionBarView_abv_opt_right_textColor);
        opt_right_textSize = typedArray.getDimensionPixelSize(R.styleable.ActionBarView_abv_opt_right_textSize, (int)DEFAULT_OPT_TEXT_SIZE);
        opt_left_textColor = typedArray.getColorStateList(R.styleable.ActionBarView_abv_opt_left_textColor);
        opt_left_text = typedArray.getString(R.styleable.ActionBarView_abv_opt_left_text);
        opt_left_textSize = typedArray.getDimensionPixelSize(R.styleable.ActionBarView_abv_opt_left_textSize, (int)DEFAULT_OPT_TEXT_SIZE);


        opt_right_iconDrawable_normol = typedArray.getDrawable(R.styleable.ActionBarView_abv_opt_right_iconDrawable_normol);
        opt_right_iconDrawable_pressed = typedArray.getDrawable(R.styleable.ActionBarView_abv_opt_right_iconDrawable_pressed);
        opt_left_iconDrawable_normol = typedArray.getDrawable(R.styleable.ActionBarView_abv_opt_left_iconDrawable_normol);
        opt_left_iconDrawable_pressed = typedArray.getDrawable(R.styleable.ActionBarView_abv_opt_left_iconDrawable_pressed);
    }
}
