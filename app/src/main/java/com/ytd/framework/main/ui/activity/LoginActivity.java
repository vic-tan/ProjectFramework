package com.ytd.framework.main.ui.activity;

import android.view.View;

import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.AppCacheUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import static com.ytd.support.constants.fixed.GlobalConstants.APP_LOGIN_NAME;

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


    @AfterViews
    void init() {

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
                AppCacheUtils.getInstance(this).put(APP_LOGIN_NAME,user_account_edit.getText().toString());
                StartActUtils.start(this, HomeActivity_.class);
                StartActUtils.finish(this);
                break;
        }
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
