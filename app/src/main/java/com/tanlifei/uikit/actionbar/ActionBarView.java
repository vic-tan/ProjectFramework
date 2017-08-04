package com.tanlifei.uikit.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanlifei.framework.R;
import com.tlf.basic.base.autolayout.AutoFrameLayout;
import com.tlf.basic.utils.CornerUtils;
import com.tlf.basic.utils.InflaterUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ViewFindUtils;

/**
 * 自定义的ActionBarView
 * <p/>
 * Created by tanlifei on 16/8/12.
 */
public class ActionBarView extends AutoFrameLayout {


    private BaseActionBarView mBarView;
    private View mView;

    /*--------------- 标题及背影设置 --------------*/
    private FrameLayout mActionBarLayout;

    private TextView actionbarTitle;

    /*--------------- 返回设置 --------------*/
    private LinearLayout actionbar_back_layout;
    //private ImageView actionbar_back_icon;
    private TextView actionbar_back_text;

    /*--------------- 操作设置 --------------*/
    private LinearLayout actionbar_opt;
    private TextView actionbar_opt_right_text;
    private TextView actionbar_opt_left_text;
    private Button actionbar_opt_right_icon;
    private Button actionbar_opt_left_icon;

    /**
     * 设置加点击事件回调
     */
    private OnOptClickListener mOptClickListener;

    /**
     * 设置返回击事件回调
     */
    private OnBackClickListener mBackClickListener;

    public ActionBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionBarView(Context context) {
        this(context, null);
    }

    /**
     * 获得我自定义的样式属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ActionBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mView = InflaterUtils.inflate(context, R.layout.common_base_actionbar_view);
        mBarView = new BaseActionBarView(mView, context.obtainStyledAttributes(attrs, R.styleable.ActionBarView));
        initView();
        addView(mView);
    }

    public void initView() {
        /*--------------- 标题及背影设置 --------------*/
        mActionBarLayout = ViewFindUtils.find(mView, R.id.actionbar_rlly_root);
        actionbarTitle = ViewFindUtils.find(mView, R.id.actionbar_title);
        mActionBarLayout.setBackgroundColor(mBarView.backgroundColor);
        actionbarTitle.setText(mBarView.title_text);
        actionbarTitle.setTextColor(mBarView.title_textColor);
        actionbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBarView.title_textSize);


        /*--------------- 返回设置 --------------*/
        actionbar_back_layout = ViewFindUtils.find(mView, R.id.actionbar_back_layout);
        actionbar_back_layout.setVisibility(mBarView.back_dimiss ? View.GONE : View.VISIBLE);//是否隐藏返回键
        // actionbar_back_icon = ViewFindUtils.find(mView, R.id.actionbar_back_icon);
        // actionbar_back_icon.setVisibility(mBarView.back_iconDrawableDimiss ? View.GONE : View.VISIBLE);//是否隐藏返回图标
        actionbar_back_text = ViewFindUtils.find(mView, R.id.actionbar_back_text);
        actionbar_back_text.setText(mBarView.back_text);
        if (null != mBarView.back_textColor) {
            actionbar_back_text.setTextColor(mBarView.back_textColor);
        }
        actionbar_back_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBarView.back_textSize);

        actionbar_back_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackClickListener.onClick(v);
            }
        });

        /*--------------- 操作设置 --------------*/
        actionbar_opt = ViewFindUtils.find(mView, R.id.actionbar_opt);
        actionbar_opt_right_text = ViewFindUtils.find(mView, R.id.actionbar_opt_right_text);
        actionbar_opt_right_text.setText(mBarView.opt_right_text);
        if (null != mBarView.opt_right_textColor) {
            actionbar_opt_right_text.setTextColor(mBarView.opt_right_textColor);
        }
        actionbar_opt_right_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBarView.opt_right_textSize);

        actionbar_opt_left_text = ViewFindUtils.find(mView, R.id.actionbar_opt_left_text);
        actionbar_opt_left_text.setText(mBarView.opt_left_text);
        if (null != mBarView.opt_left_textColor) {
            actionbar_opt_left_text.setTextColor(mBarView.opt_left_textColor);
        }


        actionbar_opt_left_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mBarView.opt_left_textSize);

        actionbar_opt_right_icon = ViewFindUtils.find(mView, R.id.actionbar_opt_right_icon);
        if (null == mBarView.opt_right_iconDrawable_normol) {
            actionbar_opt_right_icon.setVisibility(View.GONE);
        } else {
            actionbar_opt_right_icon.setVisibility(View.VISIBLE);
            if (null == mBarView.opt_right_iconDrawable_pressed) {
                actionbar_opt_right_icon.setBackgroundDrawable((CornerUtils.btnSelector(mBarView.opt_right_iconDrawable_normol, mBarView.opt_right_iconDrawable_normol)));
            } else {
                actionbar_opt_right_icon.setBackgroundDrawable((CornerUtils.btnSelector(mBarView.opt_right_iconDrawable_normol, mBarView.opt_right_iconDrawable_pressed)));
            }
        }

        actionbar_opt_left_icon = ViewFindUtils.find(mView, R.id.actionbar_opt_left_icon);
        if (null == mBarView.opt_left_iconDrawable_normol) {
            actionbar_opt_left_icon.setVisibility(View.GONE);
        } else {
            actionbar_opt_left_icon.setVisibility(View.VISIBLE);
            if (null == mBarView.opt_left_iconDrawable_pressed) {
                actionbar_opt_left_icon.setBackgroundDrawable((CornerUtils.btnSelector(mBarView.opt_left_iconDrawable_normol, mBarView.opt_left_iconDrawable_normol)));
            } else {
                actionbar_opt_left_icon.setBackgroundDrawable((CornerUtils.btnSelector(mBarView.opt_left_iconDrawable_normol, mBarView.opt_left_iconDrawable_pressed)));
            }
        }

        actionbar_opt_right_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptClickListener.onClick(v, ActionBarOptViewTagLevel.RIGHT_TEXT);
            }
        });

        actionbar_opt_right_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptClickListener.onClick(v, ActionBarOptViewTagLevel.RIGHT_ICON_DRAWABLE);
            }
        });

        actionbar_opt_left_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptClickListener.onClick(v, ActionBarOptViewTagLevel.LEFT_TEXT);
            }
        });

        actionbar_opt_left_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOptClickListener.onClick(v, ActionBarOptViewTagLevel.LEFT_ICON_DRAWABLE);
            }
        });
        show();

    }

    public void show(){
        actionbar_opt_left_text.setVisibility(StringUtils.isEmpty(actionbar_opt_left_text.getText().toString()) ? View.GONE : View.VISIBLE);//是否隐藏返回文字
        actionbar_opt_right_text.setVisibility(StringUtils.isEmpty(actionbar_opt_right_text.getText().toString()) ? View.GONE : View.VISIBLE);//是否隐藏返回文字
        actionbar_back_text.setVisibility(mBarView.back_text_dimiss ? View.GONE : View.VISIBLE);//是否隐藏返回文字
    }

    public TextView getActionbarTitle() {
        return actionbarTitle;
    }

    public void setActionbarTitle(String actionbarTitle) {
        this.actionbarTitle.setText(actionbarTitle);
    }


    public void setActionbarOptLeftText(String actionbar_opt_left_text) {
        this.actionbar_opt_left_text.setText(actionbar_opt_left_text);
    }

    public void setActionbarBackText(String actionbar_back_text) {
        this.actionbar_back_text.setText(actionbar_back_text);
    }

    public void setActionbarBackDimiss(boolean dimiss) {
        this.actionbar_back_layout.setVisibility(dimiss ? GONE :VISIBLE);
    }

    public void setActionbarOptRightText(String actionbar_opt_right_text) {
        this.actionbar_opt_right_text.setText(actionbar_opt_right_text);
    }

    /**
     * 设置加点击事件回调
     *
     * @param mOptClickListener
     */
    public void setOnOptClickListener(OnOptClickListener mOptClickListener) {
        this.mOptClickListener = mOptClickListener;
    }

    /**
     * 设置返回击事件回调
     *
     * @param mBackClickListener
     */
    public void setOnBackClickListener(OnBackClickListener mBackClickListener) {
        this.mBackClickListener = mBackClickListener;
    }


    public LinearLayout getActionbar_back_layout() {
        return actionbar_back_layout;
    }

    public TextView getActionbar_back_text() {
        return actionbar_back_text;
    }

    public LinearLayout getActionbar_opt() {
        return actionbar_opt;
    }

    public TextView getActionbar_opt_right_text() {
        return actionbar_opt_right_text;
    }

    public TextView getActionbar_opt_left_text() {
        return actionbar_opt_left_text;
    }

    public Button getActionbar_opt_right_icon() {
        return actionbar_opt_right_icon;
    }

    public Button getActionbar_opt_left_icon() {
        return actionbar_opt_left_icon;
    }
}