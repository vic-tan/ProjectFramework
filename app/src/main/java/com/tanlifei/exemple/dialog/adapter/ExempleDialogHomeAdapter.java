package com.tanlifei.exemple.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tanlifei.exemple.dialog.ui.ExempleDialogDialogHomeActivity;
import com.tanlifei.framework.R;
import com.tlf.basic.utils.ViewFindUtils;


public class ExempleDialogHomeAdapter extends BaseExpandableListAdapter {
    private Context mContext;

    public ExempleDialogHomeAdapter(Context context) {
        this.mContext = context;
    }

    // --->group
    @Override
    public int getGroupCount() {
        return ExempleDialogDialogHomeActivity.mGroups.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ExempleDialogDialogHomeActivity.mGroups[groupPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.exemple_dialog_dialog_home, null);
        }

        TextView tv = ViewFindUtils.find(convertView, R.id.tv_bubble);
        tv.setText(ExempleDialogDialogHomeActivity.mGroups[groupPosition]);
        return convertView;
    }

    // --->child
    @Override
    public int getChildrenCount(int groupPosition) {
        return ExempleDialogDialogHomeActivity.mChilds[groupPosition].length;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ExempleDialogDialogHomeActivity.mChilds[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.exemple_dialog_dialog_home, null);
        }

        TextView tv = ViewFindUtils.find(convertView, R.id.tv_bubble);
        View v_line = ViewFindUtils.find(convertView, R.id.v_line);

        v_line.setVisibility(View.INVISIBLE);
        tv.setTextColor(Color.parseColor("#383838"));
        tv.setText(ExempleDialogDialogHomeActivity.mChilds[groupPosition][childPosition]);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
