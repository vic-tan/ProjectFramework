package com.ytd.framework.equipment.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.support.constants.fixed.ScannerInterface;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_reaction_scan_activity)
public class EquipmentReactionScanActivity extends BaseActionBarActivity {



    protected KProgressHUD hud;
    protected IEquipmentPresenter equipmentPresenter;
    protected IProperyPresenter properyPresenter;
    public static final String TAG = EquipmentReactionScanActivity.class.getSimpleName();

    ScannerInterface scanner;
    IntentFilter intentFilter;
    BroadcastReceiver scanReceiver;
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";
    NormalDialog dialog;


    @AfterViews
    void init() {
        initActionBar();
        equipmentPresenter = new EquipmentPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                //findScanResult(scanResult);
            }
        });
        initScanner();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(mContext.getResources().getString(R.string.common_dialog_loading))
                .setCancellable(false);
        dialog = new NormalDialog(mContext);
    }



    private void initScanner() {
        scanner = new ScannerInterface(this);
        scanner.resultScan();//iScan恢复默认设置
        scanner.enablePlayBeep(true);//是否允许蜂鸣反馈
        scanner.enableFailurePlayBeep(true);//扫描失败蜂鸣反馈
        scanner.enableAddKeyValue(1);/**附加无、回车、Teble、换行*/
        /**设置扫描结果的输出模式，参数为0和1：
         * 0为模拟输出（在光标停留的地方输出扫描结果）；
         * 1为广播输出（由应用程序编写广播接收者来获得扫描结果，并在指定的控件上显示扫描结果）
         * 这里采用接收扫描结果广播并在TextView中显示*/
        scanner.setOutputMode(1);

        //扫描结果的意图过滤器的动作一定要使用"android.intent.action.SCANRESULT"
        intentFilter = new IntentFilter(RES_ACTION);
        //注册广播接受者
        scanReceiver = new ScannerResultReceiver();
        registerReceiver(scanReceiver, intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishScanner();
    }

    /**
     * 结束扫描
     */
    private void finishScanner() {
        scanner.scan_stop();
        unregisterReceiver(scanReceiver);    //反注册广播接收者
    }


    /**
     * 扫描结果的广播接收者
     */
    private class ScannerResultReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RES_ACTION)) {
                //获取扫描结果
                final String scanResult = intent.getStringExtra("value");
//                ToastUtils.show(mContext, "结果：" + scanResult);
                if (null != hud)
                    hud.show();
                //TODO
                /**
                 * 扫码ID是写死的，还有资产ID，到时修改
                 */

                findScanResult(scanResult);
            }
        }
    }


    //查询单
    public void findScanResult(String scanResult) {
        List<EquipmentBean> list = equipmentPresenter.findScanCode(mContext, scanResult);
        if (ListUtils.isEmpty(list)) {
            hud.dismiss();
            ToastUtils.show(mContext, "没有找到您扫描的设备信息!");
        } else {
            PropertyBean propertyBean = properyPresenter.findById(mContext,list.get(0).getPDDH());
            hud.dismiss();
            Map<String, Object> map = new HashMap<>();
            map.put("bean", list.get(0));
            map.put("scanTag", 0);
            map.put("propertyBean",propertyBean);
            StartActUtils.start(mContext, EquipmentScanDetailsResultActivity_.class, map);
        }

    }

    public Map<String, Object> tagList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", "ipeiban2016");
        return map;
    }


}
