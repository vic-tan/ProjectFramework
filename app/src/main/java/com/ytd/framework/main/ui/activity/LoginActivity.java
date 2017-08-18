package com.ytd.framework.main.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * * Created by ytd on 16/1/19.
 */
@Fullscreen
@EActivity(R.layout.login_activity)
public class LoginActivity extends BaseActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @ViewById
    MClearEditText user_account_edit;

    @ViewById
    MClearEditText user_pwd_edit;

    @ViewById
    RoundTextView login;

    IUserPresenter userPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions();
    }

    @AfterViews
    void init() {
        userPresenter = new UserPresenterImpl();
        startService(new Intent(this, CheckAppUpdateService.class));
    }


    @Click(R.id.login)
    void click(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (StringUtils.isEmpty(user_account_edit.getText().toString())) {
                    ToastUtils.show(this, "工号或账号不能为空，请输入正确账号或工号，测试账号或工号为" + ResUtils.getStr(R.string.login_name));
                    break;
                }
                if (!StringUtils.isEquals(user_account_edit.getText().toString(), ResUtils.getStr(R.string.login_name))) {
                    ToastUtils.show(this, "工号或账号不正确，请输入测试账号或工号为" + ResUtils.getStr(R.string.login_name));
                    break;
                }
                if (StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
                    ToastUtils.show(this, "密码不能为空，请输入正确密码，测试账号或工号密码为" + ResUtils.getStr(R.string.login_pwd));
                    break;
                }
                if (!StringUtils.isEquals(user_pwd_edit.getText().toString(), ResUtils.getStr(R.string.login_pwd))) {
                    ToastUtils.show(this, "密码不正确，请输入测试账号或工号密码为" + ResUtils.getStr(R.string.login_pwd));
                    break;
                }

               /* OkHttpUtils.post().url(UrlConstants.APP_VERSION_UPDATE).paramsForJson(loginParams()).build().execute(new DialogCallback(mContext) {
                    @Override
                    public void onCusResponse(BaseJson response) {

                    }
                });*/
                saveUserInfo();
                StartActUtils.start(mContext, HomeActivity_.class);
                StartActUtils.finish(mContext);
                break;
        }
    }

    public Map<String, Object> loginParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", "ipeiban2016");
        return map;
    }


    public void saveUserInfo() {
        UserBean bean = new UserBean();
        bean.setLoginName(user_account_edit.getText().toString());
        bean.setPwd(user_pwd_edit.getText().toString());
        bean.setLoginState(1);
        userPresenter.save(bean);
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
