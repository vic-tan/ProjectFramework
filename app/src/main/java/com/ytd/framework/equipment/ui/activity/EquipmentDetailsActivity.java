package com.ytd.framework.equipment.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.main.bean.PDStateBean;
import com.ytd.framework.main.presenter.IPDStatePresenter;
import com.ytd.framework.main.presenter.impl.PDStatePresenterImpl;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TextView SBXH;
    @ViewById
    TextView SBZT;
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
    IPDStatePresenter statePresenter;
    List<PDStateBean> pdStatelist;
    Map<String, PDStateBean> map;

    @AfterViews
    void init() {
        initActionBar();
        bean = getIntent().getParcelableExtra("bean");
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                StartActUtils.start(mContext,
                        CameraScanActivity.class);
            }
        });
        pdStatelist = new ArrayList<>();
        statePresenter = new PDStatePresenterImpl();
        pdStatelist = statePresenter.findAll();
        map = new HashMap<>();
        if (!ListUtils.isEmpty(pdStatelist)) {
            for (PDStateBean forBean : pdStatelist) {
                map.put(forBean.getMy_id(), forBean);
            }
        }
        setData();
    }

    private void setData() {
        title.setText("资产名称：" + bean.getSBMC());
        SBXH.setText("设备型号：" + bean.getSBXH());
        if(map.containsKey(bean.getSBZT())){
            SBZT.setText("设备状态：" + map.get(bean.getSBZT()).getName());
        }else{
            SBZT.setText("设备状态：");
        }
        address.setText("资产编号：" + bean.getSBBH());
        eqNumber.setText("资产条码号：" + bean.getSBTMBH());
        useAddress.setText("使用科室：" + bean.getKSMC());
        eqStandard.setText("资产规格：" + bean.getSBGG());
        unitName.setText("单位：" + bean.getDW());
        startDate.setText("启用日期：" + bean.getQYRQ());
        startProperty.setText("原值：" + bean.getYZ());
        endProperty.setText("净值：" + bean.getJZ());
        oldProperty.setText("折旧：" + bean.getZJ());
        eqType.setText("资产分类：" + bean.getMC());
        saveAddress.setText("存放地点：" + bean.getCFDD());



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
