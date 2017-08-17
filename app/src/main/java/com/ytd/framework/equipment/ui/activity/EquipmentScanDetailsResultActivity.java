package com.ytd.framework.equipment.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundRelativeLayout;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_scan_dateils_result_activity)
public class EquipmentScanDetailsResultActivity extends BaseActionBarActivity {


    @ViewById
    RoundTextView saveBtn;
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
    @ViewById
    TextView stutasTitile;
    @ViewById
    TextView useStatus;
    @ViewById
    TextView nameTitile;
    @ViewById
    TextView date;
    @ViewById
    TextView dateTitile;
    @ViewById
    EditText remark;
    @ViewById
    RoundRelativeLayout startDateLayout;
    @ViewById
    RelativeLayout resultLayout;


    protected KProgressHUD hud;
    EquipmentBean bean;
    private int scanTag = 0;//0，表示感应扫描，1，表示相机扫描
    protected IEquipmentPresenter equipmentPresenter;

    public static final String TAG = EquipmentScanDetailsResultActivity.class.getSimpleName();
    NormalDialog dialog;


    @AfterViews
    void init() {
        initActionBar();
        bean = getIntent().getParcelableExtra("bean");
        scanTag = getIntent().getIntExtra("scanTag", 0);
        equipmentPresenter = new EquipmentPresenterImpl();
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
            }
        });
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(mContext.getResources().getString(R.string.common_dialog_loading))
                .setCancellable(false);
        dialog = new NormalDialog(mContext);
        resultLayout.setVisibility(View.VISIBLE);
        setData();
    }

    private void setData() {
        title.setText("资产名称：" + bean.getTitle());
        address.setText("资产编号：" + bean.getEqId());
        eqNumber.setText("资产条码号：" + bean.getBarCode());
        useAddress.setText("使用科室：" + bean.getEqStandard());
        eqStandard.setText("使用科室：" + bean.getUseAddress());
        unitName.setText("单位：" + bean.getUnitName());
        startDate.setText("启用日期：" + bean.getStart_data());
        startProperty.setText("原值：" + bean.getStart_property());
        endProperty.setText("净值：" + bean.getEnd_property());
        oldProperty.setText("折旧：" + bean.getOld_property());
        eqType.setText("资产分类：" + bean.getEqType());
        saveAddress.setText("存放地点：" + bean.getSaveAddress());
        useStatus.setText(bean.getUseStatus());
        date.setText(bean.getLookDate());
        remark.setText(bean.getRemark());
    }


    @Click({R.id.testResult, R.id.useStatus, R.id.date, R.id.saveBtn})
    void click(View v) {
        switch (v.getId()) {
            case R.id.useStatus://使用状态
                useStatus();
                break;
            case R.id.date://选择时间
                //时间选择器
                selectDate();
                break;
            case R.id.saveBtn://
                if (StringUtils.isEmpty(useStatus.getText().toString())) {
                    ToastUtils.show(mContext, "资产状态不能为空,请选择资产状态");
                    return;
                }
                if (StringUtils.isEmpty(date.getText().toString())) {
                    ToastUtils.show(mContext, "盘点日期不能为空,请选择盘点日期");
                    return;
                }
                if (StringUtils.isEmpty(remark.getText().toString())) {
                    ToastUtils.show(mContext, "备注不能为空,请填写备注");
                    return;
                }
                hud.show();
                bean.setUseStatus(useStatus.getText().toString());
                bean.setLookDate(date.getText().toString());
                bean.setRemark(remark.getText().toString());
                bean.setLookStatus("1");
                boolean saveTag = equipmentPresenter.update(mContext, bean);
                if (saveTag) {
                    hud.dismiss();
                    dialog.content("\n" + "操作成功,请继续扫描下一个设备" + "\n")//
                            .btnNum(1)
                            .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                            .btnText("继续下一个")//
                            .show();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick(View v, Dialog dialog) {
                            dialog.dismiss();
                            if (scanTag == 0) {//感应扫描
                                StartActUtils.start(mContext, EquipmentReactionScanActivity_.class);
                            } else {//相机扫描
                                StartActUtils.start(mContext, CameraScanActivity.class);
                            }
                            StartActUtils.finish(mContext);
                        }
                    });
                } else {
                    hud.dismiss();
                }
                break;

        }
    }

    public void useStatus() {
        final List<String> optionsItems = new ArrayList<>();
        optionsItems.add("在用");
        optionsItems.add("停用");
        optionsItems.add("禁用");
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                useStatus.setText(optionsItems.get(options1));
            }
        }).setSubmitColor(Color.WHITE)//确定按钮文字颜色
                .setCancelColor(Color.WHITE)//取消按钮文字颜色
                .setTitleBgColor(ResUtils.getColor(R.color.theme_color))//标题背景颜色 Night mode
                .build();
        pvOptions.setPicker(optionsItems);
        pvOptions.show();
    }


    public void selectDate() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date1, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date.setText(format.format(date1));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                .setCancelColor(Color.WHITE)//取消按钮文字颜色
                .setTitleBgColor(ResUtils.getColor(R.color.theme_color))//标题背景颜色 Night mode
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


}
