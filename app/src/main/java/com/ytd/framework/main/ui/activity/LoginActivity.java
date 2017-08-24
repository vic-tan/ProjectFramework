package com.ytd.framework.main.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.http.okhttp.builder.PostFormBuilder;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.DomainUtils;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

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
    @ViewById
    ImageView logo;
    @ViewById
    ImageView ckText;
    @ViewById
    ImageView ckDown;
    @ViewById
    TextView ckEdit;
    @ViewById
    ListView list;

    List<EntrepotBean> listData;
    AbsCommonAdapter adapter;

    @AfterViews
    void init() {
        listData = new ArrayList<>();
        userPresenter = new UserPresenterImpl();
        startService(new Intent(this, CheckAppUpdateService.class));
        adapter = new AbsCommonAdapter<EntrepotBean>(mContext, R.layout.list_entrepot, listData) {
            @Override
            protected void convert(AbsViewHolder holder, EntrepotBean entrepotBean, int position) {
                holder.setText(R.id.name, entrepotBean.getName());
            }
        };
        list.setAdapter(adapter);
        postFormBuilder().build().execute(new ResultCallback(mContext) {
            @Override
            public void onCusResponse(BaseJson response) {
                ToastUtils.show(mContext, response.getIsSuccess() + "");
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);

            }
        });
    }

    public Map<String, String> headers() {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        return map;
    }

    private PostFormBuilder postFormBuilder() {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(DomainUtils.getInstance().domain() + UrlConstants.GETSTORELIST).headers(headers());
        postFormBuilder.addParams("PageIndex", "1");
        postFormBuilder.addParams("PageSize", "1000");
        return postFormBuilder;
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

                /*OkHttpUtils.post().url(UrlConstants.APP_VERSION_UPDATE).paramsForJson(loginParams()).build().execute(new DialogCallback(mContext) {
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

    public void version() {
        String json = "{\n" +
                "    \"Code\": 100,\n" +
                "    \"Msg\": \"请求成功！\",\n" +
                "    \"IsSuccess\": true,\n" +
                "    \"Data\": {\n" +
                "        \"ID\": 1,\n" +
                "        \"Url\": \"www.baidu.com/01\",\n" +
                "        \"VersionID\": \"1.1.11\",\n" +
                "        \"Memo\": \"测试版0101\",\n" +
                "        \"AddDate\": \"2017-08-21T15:55:09\"\n" +
                "    }\n" +
                "}";
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
