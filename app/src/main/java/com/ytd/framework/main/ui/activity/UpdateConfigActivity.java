package com.ytd.framework.main.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.http.okhttp.OkHttpUtils;
import com.tlf.basic.http.okhttp.builder.PostFormBuilder;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.tlf.basic.utils.coder.MD5Coder;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.IEntrepotPresenter;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.EntrepotPresenterImpl;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.http.TokenCallback;
import com.ytd.support.utils.MacUtils;
import com.ytd.support.utils.SPUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.ytd.framework.main.presenter.impl.SplashPresenterImpl.FIRST_LAUNCHER_APP_TAG;
import static com.ytd.framework.main.ui.BaseApplication.userBean;

/**
 * 登录
 * * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.update_config_activity)
public class UpdateConfigActivity extends BaseScannerReceiverActivity {

    public static final String TAG = UpdateConfigActivity.class.getSimpleName();

    @ViewById
    MClearEditText user_account_edit;
    @ViewById
    MClearEditText user_pwd_edit;
    @ViewById
    RoundTextView login;
    IConfigPresenter presenter;
    private String tag = "0";
    ConfigBean bean;
    @ViewById
    ImageView ckDown;
    @ViewById
    TextView ckEdit;
    @ViewById
    ListView list;

    List<EntrepotBean> listData;
    AbsCommonAdapter adapter;
    EntrepotBean selectBean;
    IEntrepotPresenter entrepotPresenter;
    IUserPresenter userPresenter;

    @AfterViews
    void init() {
        initActionBar();
        presenter = new ConfigPresenterImpl();
        listData = new ArrayList<>();
        entrepotPresenter = new EntrepotPresenterImpl();
        userPresenter = new UserPresenterImpl();
        tag = getIntent().getParcelableExtra("tag");
        bean = presenter.find();
        user_account_edit.setText(bean.getUrl());
        user_pwd_edit.setText(bean.getPDAKEY());
        setStoreBean(entrepotPresenter.findAll());
        adapter = new AbsCommonAdapter<EntrepotBean>(mContext, R.layout.list_entrepot, listData) {
            @Override
            protected void convert(AbsViewHolder holder, EntrepotBean entrepotBean, int position) {
                holder.setText(R.id.name, entrepotBean.getName());
            }
        };
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ckEdit.setText(listData.get(i).getName());
                list.setVisibility(View.GONE);
                selectBean = listData.get(i);
            }
        });
    }


    private void setStoreBean(List<EntrepotBean> setList) {
        if (!ListUtils.isEmpty(setList)) {
            listData.addAll(setList);
            showLastStore();
        }
    }

    private void showLastStore() {
        UserBean loginUser = userPresenter.findLoginUser(userBean);
        if (null != loginUser) {
            for (EntrepotBean forBean : listData) {
                if (StringUtils.isEquals(loginUser.getStoreId(), forBean.getMy_id())) {
                    ckEdit.setText(forBean.getName());
                    selectBean = forBean;
                }
            }
        } else {
            ckEdit.setText(listData.get(0).getName());
            selectBean = listData.get(0);
        }
    }

    @Click({R.id.login, R.id.ck_layout})
    void click(View v) {
        switch (v.getId()) {
            case R.id.ck_layout:
                if (list.getVisibility() == View.VISIBLE) {
                    list.setVisibility(View.GONE);
                } else {
                    list.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.login:
                if (StringUtils.isEmpty(user_account_edit.getText().toString())) {
                    ToastUtils.show(this, "API地址不能为空，请输入API地址");
                    break;
                }
                if (StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
                    ToastUtils.show(this, "密PDAKEY不能为空，请输入PDAKEY");
                    break;
                }

                postFormBuilder().build().execute(new TokenCallback(mContext) {
                    @Override
                    public void onCusResponse(ConfigBean response) {
                        SPUtils.putBoolean(FIRST_LAUNCHER_APP_TAG, true);
                        saveConfigInfo(response);
                        StartActUtils.start(mContext, LoginActivity_.class);
                        StartActUtils.finish(mContext);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (null != hud && hud.isShowing())
                            hud.dismiss();
                        try {////{"error":"invalid_grant","error_description":"PDA验证信息不正确！"}
//                            String error =
                            ToastUtils.show(mContext, "请再试一次");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                });
                break;
        }
    }

    private PostFormBuilder postFormBuilder() {
        String pwd = (MD5Coder.getMD5Code("PDA" + user_pwd_edit.getText().toString()) + "").toUpperCase();
        String mac = MacUtils.getWlanMac(mContext);
        String url = user_account_edit.getText().toString() + UrlConstants.TOKEN;
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url).headers(headers());
        postFormBuilder.addParams("grant_type", "password");
        postFormBuilder.addParams("userName", mac);
        postFormBuilder.addParams("password", pwd);
        return postFormBuilder;
    }


    public Map<String, String> headers() {
        Map<String, String> map = new HashMap<>();
        map.put("Content-Type", "application/x-www-form-urlencoded");
        return map;
    }

    public void saveConfigInfo(ConfigBean bean) {
        String pwd = user_pwd_edit.getText().toString()+"";
        bean.setUrl(user_account_edit.getText().toString());
        bean.setPDAKEY(pwd);
        presenter.save(bean);
        saveUserInfo();
    }


    public void saveUserInfo() {
        if (null != selectBean) {
            userBean.setStoreId(selectBean.getMy_id());
            userBean.setStoreName(selectBean.getName());
        }
        userPresenter.replaceUser(userBean);
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
