package com.ytd.framework.main.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import com.ytd.framework.equipment.ui.activity.EquipmentScanDetailsResultActivity_;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * Created by tanlifei on 2017/8/14.
 */

public class CameraScanActivity extends BaseActionBarActivity implements QRCodeView.Delegate,AdapterView.OnClickListener {
    private static final String TAG = CameraScanActivity.class.getSimpleName();
    private String testScanID = "324EWa975b5dbcbefdd9c015WDdbd05f898w";
    private QRCodeView mQRCodeView;
    protected IEquipmentPresenter equipmentPresenter;
    protected IProperyPresenter properyPresenter;
    protected KProgressHUD hud;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        equipmentPresenter = new EquipmentPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(mContext.getResources().getString(R.string.common_dialog_loading))
                .setCancellable(false);
        findViewById(R.id.scan_barcode).setOnClickListener(this);
        findViewById(R.id.scan_qrcode).setOnClickListener(this);
        findViewById(R.id.open_light).setOnClickListener(this);
        findViewById(R.id.close_light).setOnClickListener(this);
        initActionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        mQRCodeView.startSpot();
        findScanResult(result);

    }

    //查询单
    public void findScanResult(String scanResult) {
        //TODO 改testScanID
        List<EquipmentBean> list = equipmentPresenter.findScanCode(mContext, testScanID);
        if (ListUtils.isEmpty(list)) {
            hud.dismiss();
            ToastUtils.show(mContext, "没有找到您扫描的设备信息!"+scanResult);
            mQRCodeView.startSpot();
        } else {
            PropertyBean propertyBean = properyPresenter.findById(mContext,list.get(0).getPDDH());
            ToastUtils.show(mContext, scanResult);
            hud.dismiss();
            Map<String, Object> map = new HashMap<>();
            map.put("bean", list.get(0));
            map.put("scanTag", 1);
            map.put("propertyBean",propertyBean);
            StartActUtils.start(mContext, EquipmentScanDetailsResultActivity_.class, map);
        }

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_light:
                mQRCodeView.openFlashlight();
                break;
            case R.id.close_light:
                mQRCodeView.closeFlashlight();
                break;
            case R.id.scan_barcode:
                mQRCodeView.changeToScanBarcodeStyle();
                mQRCodeView.startSpot();
                break;
            case R.id.scan_qrcode:
                mQRCodeView.changeToScanQRCodeStyle();
                mQRCodeView.startSpot();
                break;

        }
    }
}
