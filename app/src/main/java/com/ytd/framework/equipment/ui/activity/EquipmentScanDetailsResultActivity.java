package com.ytd.framework.equipment.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundRelativeLayout;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.CountDownTimer;
import com.tlf.basic.utils.InputMethodManagerUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.params.BaseEventbusParams;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.main.bean.PDStateBean;
import com.ytd.framework.main.presenter.IPDStatePresenter;
import com.ytd.framework.main.presenter.impl.PDStatePresenterImpl;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ytd.common.bean.params.BaseEventbusParams.RE_SCAN_START;
import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_TRUE;
import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;

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
    TextView changeText;
    @ViewById
    EditText remark;
    @ViewById
    RoundRelativeLayout startDateLayout;

    @ViewById
    ScrollView scrollView;

    protected KProgressHUD hud;
    EquipmentBean bean;
    PropertyBean propertyBean;
    private int scanTag = 0;//0，表示感应扫描，1，表示相机扫描,3,手工盘点
    protected IEquipmentPresenter equipmentPresenter;
    IPDStatePresenter statePresenter;

    public static final String TAG = EquipmentScanDetailsResultActivity.class.getSimpleName();
    NormalDialog dialog;


    Unregistrar mUnregistrar;
    IProperyPresenter properyPresenter;

    List<PDStateBean> pdStatelist;

    @AfterViews
    void init() {
        initActionBar();
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                updateKeyboardStatusText(isOpen);
            }
        });
        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(this));
        bean = getIntent().getParcelableExtra("bean");
        propertyBean = getIntent().getParcelableExtra("propertyBean");
        scanTag = getIntent().getIntExtra("scanTag", 0);
        equipmentPresenter = new EquipmentPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();
        statePresenter = new PDStatePresenterImpl();
        pdStatelist = new ArrayList<>();
        pdStatelist = statePresenter.findAll();
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
        setData();

        remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入变化前执行
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入文本发生变化执行
                changeText.setText("还可以输入" + charSequence.length() + "/200个字");
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateKeyboardStatusText(boolean isOpen) {
        if (isOpen) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            saveBtn.setVisibility(View.GONE);
        } else {
            delayedStart(150);
        }
    }

    public void delayedStart(long delayed) {
        new CountDownTimer(delayed, delayed) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                saveBtn.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    private void setData() {
        title.setText("资产名称：" + bean.getSBMC());
        address.setText("资产编号：" + bean.getSBBH());
        eqNumber.setText("资产条码号：" + bean.getSBTMBH());
        useAddress.setText("使用科室：" + bean.getKSMC());
        eqStandard.setText("资产规格：" + bean.getSBGG());
        unitName.setText("单位：" + bean.getDW());
        startDate.setText("启用日期：" + bean.getQYRQ());
        startProperty.setText("原值：" + bean.getYZ());
        endProperty.setText("净值：" + bean.getJZ());
        oldProperty.setText("折旧：" + bean.getZJ());
        eqType.setText("资产分类：" + bean.getSBBH());
        saveAddress.setText("存放地点：" + bean.getCFDD());
        int i = 0;
        for (PDStateBean pdStateBean : pdStatelist) {
            if (pdStateBean.getMy_id().equals(bean.getUseStatus())) {
                useStatus.setText(pdStateBean.getName());
                i = 1;
            }
        }
        if (i == 0) {
            useStatus.setText(StringUtils.isEquals(bean.getUseStatus(), "") ? "" : bean.getUseStatus());
        }
        if (StringUtils.isEmpty(bean.getLookDate())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            date.setText(str);
        } else {
            date.setText(bean.getLookDate());
        }
        remark.setText(bean.getMemo());

    }


    @Click({R.id.testResult, R.id.useStatus, R.id.date, R.id.saveBtn})
    void click(View v) {
        switch (v.getId()) {
            case R.id.useStatus://使用状态
                InputMethodManagerUtils.hideSoftInput(mContext, v);
                useStatus();
                break;
            case R.id.date://选择时间
                //时间选择器
                InputMethodManagerUtils.hideSoftInput(mContext, v);
                selectDate();
                break;
            case R.id.saveBtn://
                try {
                    if (StringUtils.isEmpty(useStatus.getText().toString())) {
                        ToastUtils.show(mContext, "资产状态不能为空,请选择资产状态");
                        return;
                    }
                    if (StringUtils.isEmpty(date.getText().toString())) {
                        ToastUtils.show(mContext, "盘点日期不能为空,请选择盘点日期");
                        return;
                    }
                    bean.setUseStatus(pdStatelist.get((Integer) useStatus.getTag()).getMy_id());
                    bean.setLookDate(date.getText().toString());
                    bean.setMemo(remark.getText().toString());
                    if (null != propertyBean) {//不等于空
                        if (StringUtils.isEquals(propertyBean.getSTATUS(), UPDATELOAD_TAG_TRUE)) {//已上传
                            nextScan("\n" + "该盘点的设备信息已上传服务器不能修改,请继续扫描下一个设备" + "\n");
                            return;
                        } else {//未上传
                            if (StringUtils.isEquals(bean.getState(), LOOKSTATUS_TAG_TRUE)) {//是否已经盘点过
                                updateScan("\n" + "该盘点的设备信息已盘点过了，是否重新修改保存 ?" + "\n");
                                return;
                            }
                        }
                    }
                    saveDB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    private void saveDB() {
        try {
            boolean saveTag = false;
            if (scanTag == 3) {
                saveTag = equipmentPresenter.selfScanUpdate(mContext, bean, propertyBean.getPDDH());
            } else {
                saveTag = equipmentPresenter.scanUpdate(mContext, bean);
            }
            if (saveTag) {
                hud.dismiss();
                EventBus.getDefault().post(
                        new BaseEventbusParams(RE_SCAN_START, "scan"));
                nextScan("\n" + "操作成功,请继续扫描下一个设备" + "\n");
            } else {
                hud.dismiss();
            }
        } catch (Exception e) {
            hud.dismiss();
            e.printStackTrace();
        }
    }


    //扫描下一个
    public void updateScan(String title) {
        dialog.content(title)//
                .btnNum(3)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("取消", "继续下一个", "修改")//
                .show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {//next
                        dialog.dismiss();
                        openScan();
                    }
                }
                ,
                new OnBtnClickL() {//middle btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {//update
                        equipmentPresenter.update(mContext, bean);
                        openScan();
                        dialog.dismiss();
                    }
                }
        );
    }

    public void openScan() {
        if (scanTag == 1) {//感应扫描
         /*   StartActUtils.start(mContext, EquipmentReactionScanActivity_.class);
        } else {//相机扫描*/
            StartActUtils.start(mContext, CameraScanActivity.class);
        }
        StartActUtils.finish(mContext);
    }

    //扫描下一个
    public void nextScan(String title) {
        dialog.content(title)//
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
                if (scanTag == 1) {//感应扫描
                 /*   StartActUtils.start(mContext, EquipmentReactionScanActivity_.class);
                } else {/相机扫描*/
                    StartActUtils.start(mContext, CameraScanActivity.class);
                }
                StartActUtils.finish(mContext);
            }
        });
    }

    public void useStatus() {
        final List<String> optionsItems = new ArrayList<>();
        for (PDStateBean pdStateBean : pdStatelist) {
            optionsItems.add(pdStateBean.getName());
        }
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                useStatus.setText(optionsItems.get(options1));
                useStatus.setTag(options1);
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
