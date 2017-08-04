package com.tanlifei.exemple.baseadapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.exemple.baseadapter.bean.ExempleBaseAdpterBean;
import com.tanlifei.framework.R;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanlifei on 15/9/4.
 */
public class ExempleBaseAdapterSingleItemTypeActivity extends BaseActionBarActivity {
    private List<ExempleBaseAdpterBean> mDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemple_dialog_activity_home);
        initDatas();
        initActionBar();
        actionBarView.setActionbarTitle("单一布局");
        ListView lv = (ListView) findViewById(R.id.main_lv_list);
        lv.setAdapter(new AbsCommonAdapter<ExempleBaseAdpterBean>(this,
                R.layout.exemple_activity_base_adapter_single, mDatas) {
            @Override
            protected void convert(AbsViewHolder holder, final ExempleBaseAdpterBean exempleBaseAdpterBean, int position) {
                holder.setText(R.id.id_title, exempleBaseAdpterBean.getTitle())
                        .setText(R.id.id_desc, exempleBaseAdpterBean.getDesc())
                        .setText(R.id.id_time, exempleBaseAdpterBean.getTime())
                        .setText(R.id.id_phone, exempleBaseAdpterBean.getPhone());

                holder.setOnClickListener(R.id.id_title, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.show(ExempleBaseAdapterSingleItemTypeActivity.this, exempleBaseAdpterBean.getTitle());
                    }
                });
            }


        });
    }


    private void initDatas() {
        mDatas = new ArrayList<ExempleBaseAdpterBean>();

        ExempleBaseAdpterBean bean = new ExempleBaseAdpterBean("Android新技能Get 1",
                "Android打造万能的ListView和GridView适配器", "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 2", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 3", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 4", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 5", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 6", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 7", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 8", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);
        bean = new ExempleBaseAdpterBean("Android新技能Get 9", "Android打造万能的ListView和GridView适配器",
                "2014-12-12", "10086");
        mDatas.add(bean);

    }
}
