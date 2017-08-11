package com.ytd.framework.equipment.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.LinearLayout;

import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.support.constants.fixed.ScannerInterface;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.DialogCallback;
import com.ytd.support.utils.UnFinshUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_scan_result_activity)
public class EquipmentScanResultActivity extends BaseActionBarActivity {


    @ViewById
    LinearLayout opt;
    public static final String TAG = EquipmentScanResultActivity.class.getSimpleName();

    ScannerInterface scanner;
    IntentFilter intentFilter;
    BroadcastReceiver scanReceiver;
    private static final String RES_ACTION = "android.intent.action.SCANRESULT";

    @AfterViews
    void init() {
        initActionBar();
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                UnFinshUtils.unFinshToast(mContext);
            }
        });
        initScanner();
        opt.setVisibility(View.GONE);
        setData();
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
                final String scanResult = intent.getStringExtra("value") + "\n";
                ToastUtils.show(mContext, "结果：" + scanResult);
                findScanResult(scanResult);
            }
        }
    }


    //查询单
    public void findScanResult(String  scanResult) {
        OkHttpUtils.post().url(UrlConstants.APP_VERSION_UPDATE).paramsForJson(tagList()).build().execute(new DialogCallback(mContext) {
            @Override
            public void onCusResponse(BaseJson response) {
                ToastUtils.show(mContext, response.getData() + "");
                setData();
                opt.setVisibility(View.VISIBLE);
            }
        });
    }

    public Map<String, Object> tagList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", "ipeiban2016");
        return map;
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
