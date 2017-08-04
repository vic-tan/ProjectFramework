package com.tanlifei.uikit.actionbar;

/**
 * actionBar操作View状态
 * 右边操作view ,提供两个文字view 和两个图片view, 它们在同一LinearLayout 上，顺序从左到右为abv_opt_right_text,abv_opt_left_iconDrawable ,abv_opt_left_text,abv_opt_right_iconDrawable
 *
 * @author tanlifei
 * @date 2015年8月13日 上午11:30:51
 */
public enum ActionBarOptViewTagLevel {
    /**
     * abv_opt_right_text
     */
    RIGHT_TEXT(1),//
    /**
     * abv_opt_left_iconDrawable
     */
    LEFT_ICON_DRAWABLE(2),


    /**
     * abv_opt_left_text
     */
    LEFT_TEXT(3),
    /**
     * abv_opt_right_iconDrawable
     */
    RIGHT_ICON_DRAWABLE(4);

    private int value = 1;

    private ActionBarOptViewTagLevel(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }

    public static ActionBarOptViewTagLevel HttpTaskStatus(int value) {    //    手写的从int到enum的转换函数
        switch (value) {
            case 1:
                return RIGHT_TEXT;
            case 2:
                return LEFT_ICON_DRAWABLE;
            case 3:
                return LEFT_TEXT;
            case 4:
                return RIGHT_ICON_DRAWABLE;
            default:
                return RIGHT_TEXT;
        }
    }

    public int value() {
        return this.value;
    }
}
