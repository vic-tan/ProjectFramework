package com.ytd.framework.equipment.ui.fragment;


import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.framework.R;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.DialogCallback;
import com.ytd.support.utils.ResUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
    ImageView one;
    @ViewById
    ImageView two;

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
    @Click({R.id.star_date_name, R.id.end_date_name, R.id.one, R.id.two, R.id.one_txt, R.id.two_txt, R.id.save})
    void click(View v) {
        switch (v.getId()) {
            case R.id.one_txt:
            case R.id.one:
                priceTag = 0;
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
                starPripceName.setText("");
                endPriceName.setText("");
                break;
            case R.id.two_txt:
            case R.id.two:
                priceTag = 1;
                two.setBackground(ResUtils.getDrawable(R.mipmap.select_true));
                one.setBackground(ResUtils.getDrawable(R.mipmap.select_false));
                starPripceName.setText("");
                endPriceName.setText("");
                break;
            case R.id.star_date_name://选择时间
                selectDate(starDateName, 1);
                break;
            case R.id.end_date_name://选择时间
                selectDate(endDateName, 2);
                break;
            case R.id.save://
                if (StringUtils.isEmpty(name.getText().toString())) {
                    ToastUtils.show(getActivity(), "盘点单名称不能为空,请填写盘点单名称");
                    return;
                }

                //TODO 互折选择
                if (!StringUtils.isEmpty(endPriceName.getText().toString()) || !StringUtils.isEmpty(starPripceName.getText().toString())) {
                    if (StringUtils.isEmpty(starPripceName.getText().toString())) {
                        ToastUtils.show(getActivity(), "开始价格不能为空,请填写开始价格");
                        return;
                    }
                    if (StringUtils.isEmpty(endPriceName.getText().toString())) {
                        ToastUtils.show(getActivity(), "结束价格不能为空,请填写结束价格");
                        return;
                    }
                }

                OkHttpUtils.post().url(UrlConstants.APP_VERSION_UPDATE).paramsForJson(requsetParams()).build().execute(new DialogCallback(getActivity()) {
                    @Override
                    public void onCusResponse(BaseJson response) {
                        ToastUtils.show(mContext, response.getData() + "");
                        clear();
                    }
                });

                break;
        }
    }

    public Map<String, Object> requsetParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", "ipeiban2016");
        return map;
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
