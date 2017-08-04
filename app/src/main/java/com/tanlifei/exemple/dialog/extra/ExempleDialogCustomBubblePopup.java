package com.tanlifei.exemple.dialog.extra;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanlifei.framework.R;
import com.tlf.basic.uikit.dialog.base.popup.BaseBubblePopup;
import com.tlf.basic.utils.ToastUtils;


public class ExempleDialogCustomBubblePopup extends BaseBubblePopup<ExempleDialogCustomBubblePopup> {

    private ImageView mIvBubble;
    private TextView mTvBubble;

    public ExempleDialogCustomBubblePopup(Context context) {
        super(context);
    }

    @Override
    public View onCreateBubbleView() {
        View inflate = View.inflate(mContext, R.layout.exemple_dialog_popup_bubble_image, null);
        mTvBubble = (TextView) inflate.findViewById(R.id.tv_bubble);
        mIvBubble = (ImageView) inflate.findViewById(R.id.iv_bubble);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        super.setUiBeforShow();

        mTvBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext, "mTvBubble--->");
            }
        });
        mIvBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(mContext, "mIvBubble--->");
            }
        });
    }
}
