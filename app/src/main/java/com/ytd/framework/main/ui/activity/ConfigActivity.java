package com.ytd.framework.main.ui.activity;

import android.view.View;

import com.google.gson.Gson;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.exception.AppException;
import com.ytd.support.exception.ErrorBean;
import com.ytd.support.http.TokenCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.support.utils.SPUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import okhttp3.Call;

import static com.ytd.framework.main.presenter.impl.SplashPresenterImpl.FIRST_LAUNCHER_APP_TAG;

/**
 * 登录
 * * Created by ytd on 16/1/19.
 */
@Fullscreen
@EActivity(R.layout.config_activity)
public class ConfigActivity extends BaseActivity {

    public static final String TAG = ConfigActivity.class.getSimpleName();

    @ViewById
    MClearEditText user_account_edit;
    @ViewById
    MClearEditText user_pwd_edit;
    @ViewById
    RoundTextView login;
    IConfigPresenter presenter;
    int tag = 0;

    @AfterViews
    void init() {
        presenter = new ConfigPresenterImpl();
    }


    @Click(R.id.login)
    void click(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (StringUtils.isEmpty(user_account_edit.getText().toString())) {
                    ToastUtils.show(this, "API地址不能为空，请输入API地址");
                    break;
                }
                if (StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
                    ToastUtils.show(this, "密PDAKEY不能为空，请输入PDAKEY");
                    break;
                }

                HttpRequestUtils.getInstance().postTokenFormBuilder(user_account_edit.getText().toString() + UrlConstants.TOKEN, HttpParamsUtils.getTokenParams(mContext, user_pwd_edit.getText().toString()), 1)
                        .build().execute(new TokenCallback(mContext) {
                    @Override
                    public void onCusResponse(ConfigBean response) {
                        SPUtils.putBoolean(FIRST_LAUNCHER_APP_TAG, false);
                        saveUserInfo(response);
                        StartActUtils.start(mContext, LoginActivity_.class);
                        StartActUtils.finish(mContext);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (null != hud && hud.isShowing())
                            hud.dismiss();
                        try {
                            ErrorBean jsonBean = new Gson().fromJson(new Gson().toJson(e), ErrorBean.class);///{"error":"invalid_grant","error_description":"PDA验证信息不正确！"}
                            if (null != jsonBean) {
                                if (!StringUtils.isEmpty(jsonBean.getError())) {
                                    if (tag == 0 ) {
                                        tag = 1;
                                        ToastUtils.show(mContext, "请再试一次");
                                    } else {
                                        ToastUtils.show(mContext, jsonBean.getError_description());
                                    }
                                }else{
                                    throw new AppException(mContext, e);
                                }
                            }else{
                                throw new AppException(mContext, e);
                            }
                        } catch (Exception e1) {
                            try {
                                throw new AppException(mContext, e);
                            } catch (AppException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }

                });
                break;
        }
    }

    public void saveUserInfo(ConfigBean bean) {
        String pwd = user_pwd_edit.getText().toString();
        bean.setUrl(user_account_edit.getText().toString());
        bean.setPDAKEY(pwd);
        presenter.save(bean);
    }


    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    protected void setSystemBarTint(int systemBarTint) {
    }

}
