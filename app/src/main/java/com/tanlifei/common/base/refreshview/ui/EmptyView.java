package com.tanlifei.common.base.refreshview.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanlifei.framework.R;

/**
 * Desction:
 * Author:pengjianbo
 * Date:16/3/9 上午11:54
 */
public class EmptyView {

    public static void showLoading(ViewGroup emptyView) {
        if(emptyView == null) {
            return;
        }
        ProgressBar pbLoading = (ProgressBar) emptyView.findViewById(R.id.pb_loading);
        pbLoading.setVisibility(View.VISIBLE);
        TextView tvEmptyMsg = (TextView) emptyView.findViewById(R.id.tv_empty_message);
        tvEmptyMsg.setVisibility(View.GONE);
    }

    public static void showNetErrorEmpty(ViewGroup emptyView) {
        if(emptyView == null) {
            return;
        }
        ProgressBar pbLoading = (ProgressBar) emptyView.findViewById(R.id.pb_loading);
        pbLoading.setVisibility(View.GONE);
        TextView tvEmptyMsg = (TextView) emptyView.findViewById(R.id.tv_empty_message);
        tvEmptyMsg.setVisibility(View.VISIBLE);
        tvEmptyMsg.setText(R.string.common_net_error);
    }

    public static void showNoDataEmpty(ViewGroup emptyView) {
        if(emptyView == null) {
            return;
        }
        ProgressBar pbLoading = (ProgressBar) emptyView.findViewById(R.id.pb_loading);
        pbLoading.setVisibility(View.GONE);
        TextView tvEmptyMsg = (TextView) emptyView.findViewById(R.id.tv_empty_message);
        tvEmptyMsg.setVisibility(View.VISIBLE);
        tvEmptyMsg.setText(R.string.common_empty_data);
    }
}
