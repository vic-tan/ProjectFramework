package com.ytd.framework.equipment.ui.activity;

import android.view.View;

import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_details_activity)
public class EquipmentDetailsActivity extends BaseActionBarActivity {

    public static final String TAG = EquipmentDetailsActivity.class.getSimpleName();

    private EquipmentBean bean;


    @AfterViews
    void init() {
        initActionBar();
        bean = getIntent().getParcelableExtra("bean");
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                StartActUtils.start(mContext,
                        EquipmentScanResultActivity_.class);
            }
        });
        setData();
    }

    private void setData() {
       /* finshNum.setText(bean.getFinshNum());
        totalNum.setText(bean.getTotalNum());

        name.setText("盘点人:" + bean.getName());
        data.setText("盘点日期:" + bean.getEnd_data());
        title.setText("盘点单名称:" + bean.getTitle());

        area.setText("盘点区域：" + bean.getArea());
        address.setText("资产分类：" + bean.getAddress());
        startDate.setText("启用日期:" + bean.getStart_data());
        price.setText("价格区间:" + bean.getPrice());
        qeSumNum.setText("设  备:" + bean.getTotalNum());
        startProperty.setText("资产原值:" + bean.getStart_property());
        endProperty.setText("资产净值:" + bean.getEnd_property());
        updateload.setText("盘点单上传:" + bean.getTotalNum());*/


    }

    /*@Click({R.id.lookeEqBtn, R.id.updateLoadBtn})
    void click(View v) {
        switch (v.getId()) {
            case R.id.updateLoadBtn://上传
                UnFinshUtils.unFinshToast(mContext);
                break;
            case R.id.lookeEqBtn://查看
                StartActUtils.start(mContext, EquipmentActivity_.class);
                break;
        }
    }*/


}
