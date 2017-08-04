package com.tanlifei.exemple.dialog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;

import java.util.ArrayList;
import java.util.List;


public class ExempleDialogPopupHomeActivity extends BaseActionBarActivity {
    private final List<String> mItems = new ArrayList<>();
    private final Class<?>[] mClazzs = {ExempleDialogCustomPopupActivity.class, ExempleDialogBubblePopupActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems.add("Custom Popup");
        mItems.add("BubblePopup");
        setContentView(R.layout.exemple_base_adapter_activity_simple_home);
        initActionBar();
        actionBarView.setActionbarTitle("Popup分类");
        ListView lv = (ListView) findViewById(R.id.main_lv_list);
        lv.setAdapter(new AbsCommonAdapter<String>(this, R.layout.main_activity_home_list_item, mItems) {
            @Override
            protected void convert(AbsViewHolder holder, String s, final int position) {
                holder.setText(R.id.main_list_item_name, s);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, mClazzs[position]);
                        startActivity(intent);
                    }
                });
            }
        });
    }


}
