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
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.main.ui.BaseApplication;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_TRUE;
import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;
import static com.ytd.support.constants.fixed.UrlConstants.PDABIND;

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
    private int scanTag = 0;//0，表示感应扫描，1，表示相机扫描
    protected IEquipmentPresenter equipmentPresenter;


    public static final String TAG = EquipmentScanDetailsResultActivity.class.getSimpleName();
    NormalDialog dialog;


    Unregistrar mUnregistrar;
    IProperyPresenter properyPresenter;

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
        useAddress.setText("使用科室：" + bean.getEqStandard());
        eqStandard.setText("使用科室：" + bean.getKSMC());
        unitName.setText("单位：" + bean.getDW());
        startDate.setText("启用日期：" + bean.getQYRQ());
        startProperty.setText("原值：" + bean.getYZ());
        endProperty.setText("净值：" + bean.getJZ());
        oldProperty.setText("折旧：" + bean.getZJ());
        eqType.setText("资产分类：" + bean.getSBBH());
        saveAddress.setText("存放地点：" + bean.getSaveAddress());
        useStatus.setText(bean.getUseStatus());
        date.setText(bean.getLookDate());
        remark.setText(bean.getRemark());

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


                bean.setUseStatus(useStatus.getText().toString());
                bean.setLookDate(date.getText().toString());
                bean.setRemark(remark.getText().toString());
                bean.setLookStatus("1");
                if (null != propertyBean) {//不等于空
                    if (StringUtils.isEquals(propertyBean.getSTATUS(), UPDATELOAD_TAG_TRUE)) {//已上传
                        nextScan("\n" + "该盘点的设备信息已上传服务器不能修改,请继续扫描下一个设备" + "\n");
                        return;
                    } else {//未上传
                        if (StringUtils.isEquals(bean.getLookStatus(), LOOKSTATUS_TAG_TRUE)) {//是否已经盘点过
                            updateScan("\n" + "该盘点的设备信息已盘点过了，是否重新修改保存 ?" + "\n");
                            return;
                        }
                    }
                }


                if (null != propertyBean && !propertyBean.isPDABind()) {
                    bindShow("\n" + "您还未绑定还该盘点单，请先连网绑定再开始盘点！" + "\n");
                } else {
                    saveDB();
                }
                break;

        }
    }

    //扫描下一个
    public void bindShow(String title) {
        NormalDialog dialogBind = new NormalDialog(mContext);
        dialogBind.content(title)//
                .btnNum(2)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("取消", "确定")//
                .show();
        dialogBind.setCancelable(false);
        dialogBind.setCanceledOnTouchOutside(false);
        dialogBind.setOnBtnClickL(
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
                        padBind();
                    }
                }
        );
    }

    private void saveDB() {
        boolean saveTag = equipmentPresenter.update(mContext, bean);
        if (saveTag) {
            hud.dismiss();
            nextScan("\n" + "操作成功,请继续扫描下一个设备" + "\n");
        } else {
            hud.dismiss();
        }
    }

    private void padBind() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(PDABIND, getPDABindParams()).build().execute(new ResultCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    String jsonBean = (String) response.getData();
                    if (!StringUtils.isEmpty(jsonBean) && StringUtils.isEquals("1", jsonBean)) {
                        propertyBean.setPDABind(true);
                        properyPresenter.update(mContext,propertyBean);
                        ToastUtils.show(mContext, "绑定成功,请点击保存继续盘点！");
                    }else{
                        ToastUtils.show(mContext, "绑定失败,请重试！");
                    }
                }


            });
        }else{
            ToastUtils.show(mContext,"绑定必须要连网，请连接网络再试！");
        }
    }

    private Map<String, String> getPDABindParams() {
        return HttpParamsUtils.getPDABindParams(propertyBean.getPDDH(), BaseApplication.userBean.getEquId(), BaseApplication.userBean.getLoginName());
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
        if (scanTag != 0) {//感应扫描
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
                if (scanTag != 0) {//感应扫描
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
