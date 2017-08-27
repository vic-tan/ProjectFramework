package com.ytd.framework.equipment.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_details_activity)
public class EquipmentDetailsActivity extends BaseScannerReceiverActivity {

    public static final String TAG = EquipmentDetailsActivity.class.getSimpleName();
    @ViewById
    TextView title;
    @ViewById
    TextView address;
    @ViewById
    TextView eqNumber;
    @ViewById
    TextView useAddress;
    @ViewById
    TextView eqStandard;
    @ViewById
    TextView unitName;
    @ViewById
    TextView startDate;
    @ViewById
    TextView startProperty;
    @ViewById
    TextView oldProperty;
    @ViewById
    TextView endProperty;
    @ViewById
    TextView eqType;
    @ViewById
    TextView saveAddress;

    private EquipmentBean bean;


    @AfterViews
    void init() {
        initActionBar();
        bean = getIntent().getParcelableExtra("bean");
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                StartActUtils.start(mContext,
                        EquipmentReactionScanActivity_.class);
            }
        });
        setData();
    }

    private void setData() {

        title.setText("资产名称：" + bean.getSBMC());
        address.setText("资产编号：" + bean.getSBBH());
        eqNumber.setText("资产条码号：" + bean.getSBTMBH());
        useAddress.setText("使用科室：" + bean.getEqStandard());
        eqStandard.setText("使用科室：" + bean.getKSMC());
        unitName.setText("单位：" + bean.getDW());
        startDate.setText("启用日期：" + bean.getQYRQ());
        startProperty.setText("原值：" + bean.getYZ());
        endProperty.setText("净值：" + bean.getJZ());
        oldProperty.setText("折旧：" + bean.getZJ());
        eqType.setText("资产分类：" + bean.getSBBH());
        saveAddress.setText("存放地点：" + bean.getSaveAddress());



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
