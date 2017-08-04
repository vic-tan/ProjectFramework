package com.tanlifei.exemple.dialog.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tlf.basic.uikit.dialog.base.popup.BasePopup;
import com.tlf.basic.utils.ToastUtils;
import com.tlf.basic.utils.ViewFindUtils;


public class ExempleDialogCustomPopupActivity extends BaseActionBarActivity implements View.OnClickListener{
    TextView mTvTopLeft;
    TextView mTvTopRight;
    TextView mTvBottomLeft;
    TextView mTvBottomRight;
    TextView mTvCenter;
    private Context mContext = this;
    private SimpleCustomPop mQuickCustomPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemple_dialog_custom_popup);
        initActionBar();
        actionBarView.setActionbarTitle("Custom Dialog分类");

        mTvTopLeft = ViewFindUtils.find(this, R.id.tv_top_left);
        mTvTopRight = ViewFindUtils.find(this, R.id.tv_top_right);
        mTvBottomLeft =  ViewFindUtils.find(this, R.id.tv_bottom_left);
        mTvBottomRight = ViewFindUtils.find(this, R.id.tv_bottom_right);
        mTvCenter =  ViewFindUtils.find(this, R.id.tv_center);

        mTvTopLeft.setOnClickListener(this);
        mTvTopRight.setOnClickListener(this);
        mTvBottomLeft.setOnClickListener(this);
        mTvBottomRight.setOnClickListener(this);
        mTvCenter.setOnClickListener(this);

        mQuickCustomPopup = new SimpleCustomPop(mContext);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_center){
            clickCenterBtn();
        }else if(v.getId() == R.id.tv_top_left){
            clickTopLeftBtn();
        }else if(v.getId() == R.id.tv_top_right){
            clickTopRightBtn();
        }else if(v.getId() == R.id.tv_bottom_left){
            clickBottomLeftBtn();
        }else if(v.getId() == R.id.tv_bottom_right){
            clickBottomRightBtn();
        }
    }


    void clickCenterBtn() {
        mQuickCustomPopup
                .alignCenter(true)
                .anchorView(mTvCenter)
                .gravity(Gravity.BOTTOM)
                .offset(0, 0)
                .dimEnabled(false)
                .show();
    }

    void clickTopLeftBtn() {
        mQuickCustomPopup
                .anchorView(mTvTopLeft)
                .gravity(Gravity.BOTTOM)
                .offset(0, 0)
                .dimEnabled(false)
                .show();
    }

    void clickTopRightBtn() {
        mQuickCustomPopup
                .anchorView(mTvTopRight)
                .offset(-10, 5)
                .gravity(Gravity.BOTTOM)
                .dimEnabled(false)
                .show();
    }

    void clickBottomLeftBtn() {
        mQuickCustomPopup
                .anchorView(mTvBottomLeft)
                .offset(10, -5)
                .gravity(Gravity.TOP)
                .dimEnabled(true)
                .show();
    }

    void clickBottomRightBtn() {
        mQuickCustomPopup
                .showAnim(null)
                .dismissAnim(null)
                .dimEnabled(true)
                .anchorView(mTvBottomRight)
                .offset(-10, -5)
                .dimEnabled(false)
                .gravity(Gravity.TOP)
                .show();
    }

    private class SimpleCustomPop extends BasePopup<SimpleCustomPop> {

        private TextView mTvItem1;
        private TextView mTvItem2;
        private TextView mTvItem3;
        private TextView mTvItem4;

        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View inflate = View.inflate(mContext, R.layout.exemple_dialog_popup_custom, null);
            mTvItem1 = (TextView) inflate.findViewById(R.id.tv_item_1);
            mTvItem2 = (TextView) inflate.findViewById(R.id.tv_item_2);
            mTvItem3 = (TextView) inflate.findViewById(R.id.tv_item_3);
            mTvItem4 = (TextView) inflate.findViewById(R.id.tv_item_4);
            return inflate;
        }

        @Override
        public void setUiBeforShow() {
            mTvItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(mContext, mTvItem1.getText());
                }
            });
            mTvItem2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(mContext,mTvItem2.getText());
                }
            });
            mTvItem3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(mContext, mTvItem3.getText());
                }
            });
            mTvItem4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(mContext, mTvItem4.getText());
                }
            });
        }
    }
}
