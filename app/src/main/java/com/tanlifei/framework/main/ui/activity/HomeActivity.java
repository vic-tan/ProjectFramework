package com.tanlifei.framework.main.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.demo.eventbus.DemoEventBusOneActivity;
import com.tanlifei.exemple.main.ExempleHomeActivity_;
import com.tanlifei.framework.R;
import com.tanlifei.framework.main.ui.service.CheckAppUpdateService;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.utils.StartActUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页界面
 * Created by tanlifei on 16/1/19.
 */
@EActivity(R.layout.main_activity_home)
public class HomeActivity extends BaseActionBarActivity {

    public static final String TAG = HomeActivity.class.getSimpleName();

    List<String> list = new ArrayList<>();
    @ViewById(R.id.main_lv_list)
    ListView mList;

    private void addList() {
        list.add("集成示范");
        list.add("测试示范");
    }



    @AfterViews
    void init() {
        startService(new Intent(this, CheckAppUpdateService.class));
        initActionBar();
        actionBarView.setActionbarTitle("首页");
        actionBarView.setActionbarBackDimiss(true);
        addList();
        mList.setAdapter(new AbsCommonAdapter<String>(this, R.layout.main_activity_home_list_item, list) {
            @Override
            protected void convert(final AbsViewHolder holder, String s, final int position) {
                holder.setText(R.id.main_list_item_name, s);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                StartActUtils.start(mContext, ExempleHomeActivity_.class);
                                break;
                            case 1:
                                StartActUtils.start(mContext, DemoEventBusOneActivity.class);
                                break;
                        }
                    }
                });
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }


}
