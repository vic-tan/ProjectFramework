package com.ytd.framework.equipment.ui.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.CountDownTimer;
import com.tlf.basic.utils.InputMethodManagerUtils;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.EquipmentListBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.bean.PropertyListBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.BaseApplication;
import com.ytd.support.http.MultipleCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.support.utils.ResUtils;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.ytd.support.constants.fixed.UrlConstants.GETINVENTORYITEMLIST;
import static com.ytd.support.constants.fixed.UrlConstants.GETINVENTORYLIST;
import static com.ytd.support.constants.fixed.UrlConstants.GETPDSTATELIST;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.add_equipment_fragment)
public class AddPropertyFragment extends Fragment {
    public static final String TAG = AddPropertyFragment.class.getSimpleName();


    @ViewById
    RoundTextView save;
    @ViewById
    EditText name;
    @ViewById
    EditText starAreaName;
    @ViewById
    EditText endAreaName;
    @ViewById
    EditText typeName;
    @ViewById
    TextView starDateName;
    @ViewById
    TextView endDateName;
    @ViewById
    EditText starPripceName;
    @ViewById
    EditText endPriceName;
    @ViewById
    ScrollView scrollView;
    @ViewById
    ImageView one;
    @ViewById
    ImageView two;
    Unregistrar mUnregistrar;
    IConfigPresenter configPresenter;
    IProperyPresenter properyPresenter;
    IEquipmentPresenter equipmentPresenter;
    IUserPresenter userPresenter;

    List<PropertyBean> propertyList;
    List<EquipmentBean> equipmentList;
    int pageIndex = 1;
    int downloadTag = 0;
    NormalDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clear() {
        priceTag = 0;
        name.setText("");
        starAreaName.setText("");
        endAreaName.setText("");
        typeName.setText("");
        starDateName.setText("");
        endDateName.setText("");
        starPripceName.setText("");
        endPriceName.setText("");
        one.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
    }


    @AfterViews
    void init() {
        dialog = new NormalDialog(getActivity());
        propertyList = new ArrayList<>();
        equipmentList = new ArrayList<>();
        configPresenter = new ConfigPresenterImpl();
        userPresenter = new UserPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();
        equipmentPresenter = new EquipmentPresenterImpl();
        starPripceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入变化前执行
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入文本发生变化执行
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                setPriceSelet();

            }
        });


        endPriceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入变化前执行
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                setPriceSelet();
            }
        });
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(getActivity(), new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                updateKeyboardStatusText(isOpen);
            }
        });
        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(getActivity()));
    }

    private void updateKeyboardStatusText(boolean isOpen) {
        if (isOpen) {
            save.setVisibility(View.GONE);
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
                save.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setPriceSelet() {
        if (StringUtils.isEmpty(endPriceName.getText().toString()) && StringUtils.isEmpty(starPripceName.getText().toString())) {
            if (priceTag == 0) {
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
            } else {
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
            }

        } else {
            one.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
            two.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
        }
    }

    int priceTag;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Click({R.id.star_date_name, R.id.end_date_name, R.id.star_date_name_tag, R.id.end_date_name_tag, R.id.one, R.id.two, R.id.one_txt, R.id.two_txt, R.id.save})
    void click(View v) {
        switch (v.getId()) {
            case R.id.one_txt:
            case R.id.one:
                InputMethodManagerUtils.hideSoftInput(getActivity(), v);
                priceTag = 0;
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
                starPripceName.setText("");
                endPriceName.setText("");
                break;
            case R.id.two_txt:
            case R.id.two:
                InputMethodManagerUtils.hideSoftInput(getActivity(), v);
                priceTag = 1;
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
                starPripceName.setText("");
                endPriceName.setText("");
                break;
            case R.id.star_date_name:
            case R.id.star_date_name_tag://选择时间
                InputMethodManagerUtils.hideSoftInput(getActivity(), v);
                selectDate(starDateName, 1);
                break;
            case R.id.end_date_name:
            case R.id.end_date_name_tag://选择时间
                InputMethodManagerUtils.hideSoftInput(getActivity(), v);
                selectDate(endDateName, 2);
                break;
            case R.id.save://
                if (StringUtils.isEmpty(name.getText().toString())) {
                    ToastUtils.show(getActivity(), "盘点单号不能为空,请填写盘点单号");
                    return;
                }


                HttpRequestUtils.getInstance().postFormBuilder(GETINVENTORYLIST, getInventoryListParams()).build().execute(new MultipleCallback(getActivity(), "加载中...") {
                    @Override
                    public void onCusResponse(BaseJson response, KProgressHUD hud) {

                        PropertyListBean jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), PropertyListBean.class);
                        if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                            clearList();
                            propertyList.add(jsonBean.getItemList().get(0));
                            getPDStateList(jsonBean.getItemList().get(0).getPDDH(), hud);
                        } else {
                            hud.dismiss();
                            ToastUtils.show(getActivity(), "没有查到您要的资源！");
                        }
                    }

                });

                break;
        }
    }


    public void getPDStateList(final String PDDH, KProgressHUD hud) {
        HttpRequestUtils.getInstance().postFormBuilder(GETPDSTATELIST, getPDStateListParams(PDDH)).build().execute(new MultipleCallback(getActivity(), hud, true) {
            @Override
            public void onCusResponse(BaseJson response, KProgressHUD hud) {
                String status = (String) response.getData();
                /*1:已绑定 不是本设备
                2:未绑定
                3:已绑定 是本设备
*/
                downloadTag = 0;
                if (StringUtils.isEquals(status, "1")) {
                    downloadTag = 1;
                    tishi(PDDH, "\n" + "该盘点单已被其它设备绑定，是否继续下载" + "\n");
                } else if (StringUtils.isEquals(status, "2")) {
                    dowload(PDDH);
                } else if (StringUtils.isEquals(status, "3")) {
                    PropertyBean dbBeen = properyPresenter.findById(getActivity(), PDDH);
                    if (null == dbBeen) {
                        dowload(PDDH);
                    } else {
                        downloadTag = 2;
                        tishi(PDDH, "\n" + "您已绑定过了已下载过资源了，是否覆盖本地继续下载" + "\n");
                    }
                }
            }
        });
    }


    //扫描下一个
    public void tishi(final String PDDH, String title) {
        dialog.content(title)//
                .btnNum(2)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("取消", "确定")//
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
                        dowload(PDDH);
                    }
                }
        );
    }


    public void dowload(final String id) {
        HttpRequestUtils.getInstance().postFormBuilder(GETINVENTORYITEMLIST, getInventoryItemListParams(pageIndex, id)).build().execute(new MultipleCallback(getActivity(), "正在下载资源") {
            @Override
            public void onCusResponse(BaseJson response, KProgressHUD hud) {
                EquipmentListBean jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), EquipmentListBean.class);
                if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                    if (!ListUtils.isEmpty(propertyList)) {
                        equipmentList.addAll(jsonBean.getItemList());
                        pageIndex = pageIndex + 1;
                        if (totalPage(jsonBean.getTotal(), jsonBean.getPageSize()) >= pageIndex) {
                            dowloadFor(id, hud);
                        } else {
                            propertyList.get(0).setEqList(equipmentList);
                            hud.dismiss();
                        }
                    }

                } else {
                    pageIndex = 1;
                    ToastUtils.show(getActivity(), "没有查到您要的资源！");
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                pageIndex = 1;
                hud.dismiss();
                ToastUtils.show(getActivity(), "下载资源失败！");
            }
        });
    }

    public int totalPage(int total, int pageSize) {
        int pa = (int) Math.ceil(total / (pageSize * 1.0));
        return pa;

    }


    public void dowloadFor(final String id, KProgressHUD hud) {
        HttpRequestUtils.getInstance().postFormBuilder(GETINVENTORYITEMLIST, getInventoryItemListParams(pageIndex, id)).build().execute(new MultipleCallback(getActivity(), hud, false) {
            @Override
            public void onCusResponse(BaseJson response, KProgressHUD hud) {
                EquipmentListBean jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), EquipmentListBean.class);
                if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                    if (!ListUtils.isEmpty(propertyList)) {
                        equipmentList.addAll(jsonBean.getItemList());
                        pageIndex = pageIndex + 1;
                        if (totalPage(jsonBean.getTotal(), jsonBean.getPageSize()) >= pageIndex) {
                            dowloadFor(id, hud);
                        } else {
                            propertyList.get(0).setEqList(equipmentList);
                            save(id);
                            ToastUtils.show(getActivity(), "下载完成");
                            hud.dismiss();
                        }
                    }
                } else {
                    pageIndex = 1;
                    clearList();
                    hud.dismiss();
                    ToastUtils.show(getActivity(), "下载资源失败！请重试");
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                pageIndex = 1;
                clearList();
                hud.dismiss();
                ToastUtils.show(getActivity(), "下载资源失败！");
            }
        });
    }

    private void clearList() {
        propertyList.clear();
        equipmentList.clear();
    }

    private void save(String PDDH) {
        if (!ListUtils.isEmpty(propertyList)) {
            if (downloadTag == 2) {
                properyPresenter.deleteById(getActivity(), PDDH);
            }
            properyPresenter.save(getActivity(), propertyList);
        }
    }


    private Map<String, String> getInventoryListParams() {
        return HttpParamsUtils.getInventoryListParams(name.getText().toString(), BaseApplication.userBean.getStoreId());
    }

    private Map<String, String> getPDStateListParams(String PDDH) {
        return HttpParamsUtils.getPDStateListParams(PDDH, BaseApplication.userBean.getEquId());
    }

    private Map<String, String> getInventoryItemListParams(int index, String id) {
        return HttpParamsUtils.getInventoryItemListParams(index, id);
    }


    private long startTime;

    public void selectDate(final TextView view, final int start) {
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date1, View v) {//选中事件回调
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (start != 1) {
                    if (startTime == 0) {
                        ToastUtils.show(getActivity(), "请先填写开始日期");
                        return;
                    }
                    Logger.i(startTime + "--" + date1.getTime() + "---" + (startTime < date1.getTime()) + "");
                    if (startTime > date1.getTime()) {
                        ToastUtils.show(getActivity(), "结束日期不能开始日期之前");
                        return;
                    }
                    view.setText(format.format(date1));
                } else {
                    startTime = date1.getTime();
                    view.setText(format.format(date1));
                }


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
