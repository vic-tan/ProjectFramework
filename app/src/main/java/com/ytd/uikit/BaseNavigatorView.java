package com.ytd.uikit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tlf.basic.base.autolayout.AutoLinearLayout;

/**
 * 活动详情底部tabs
 * Created by aspsine on 16/3/31.
 */
public abstract class BaseNavigatorView extends AutoLinearLayout {

    OnBottomNavigatorViewItemClickListener mOnBottomNavigatorViewItemClickListener;


    public BaseNavigatorView(Context context) {
        this(context, null);
    }

    public BaseNavigatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseNavigatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        inflate(context, navigatorViewLayoutResId(), this);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBottomNavigatorViewItemClickListener.onBottomNavigatorViewItemClick(finalI, v);
                }
            });
        }
    }

    public void select(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == position) {
                selectChild(child, true);
            } else {
                selectChild(child, false);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void selectChild(View child, boolean select) {
        if (child instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) child;
            group.setSelected(select);
            for (int i = 0; i < group.getChildCount(); i++) {
                selectChild(group.getChildAt(i), select);
            }
        } else {
            child.setSelected(select);
            if (child instanceof ImageView) {
                ImageView iv = (ImageView) child;
                iv.setDrawingCacheEnabled(true);
                if (select) {
                    iv.setBackgroundResource(respreImageArray()[Integer.parseInt(iv.getTag() + "")]);
                } else {
                    iv.setBackgroundResource(resnormalImageArray()[Integer.parseInt(iv.getTag() + "")]);
                }
            }
        }
    }

    public void setOnBottomNavigatorViewItemClickListener(OnBottomNavigatorViewItemClickListener listener) {
        this.mOnBottomNavigatorViewItemClickListener = listener;
    }

    public abstract int navigatorViewLayoutResId();

    public abstract int[] respreImageArray();

    public abstract int[] resnormalImageArray();

    public interface OnBottomNavigatorViewItemClickListener {
        void onBottomNavigatorViewItemClick(int position, View view);
    }

}
