package com.ytd.framework.main.ui.activity;

import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.tlf.basic.utils.coder.Base64Coder;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.ui.activity.BaseActivity;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.bean.EntrepotBeanList;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.support.http.DialogCallback;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.uikit.edittext.MClearEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.ytd.support.constants.fixed.UrlConstants.GETSTORELIST;
import static com.ytd.support.constants.fixed.UrlConstants.LOGIN;

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
    IConfigPresenter configPresenter;
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

    EntrepotBean selectBean;

    @AfterViews
    void init() {
        listData = new ArrayList<>();
        configPresenter = new ConfigPresenterImpl();
        userPresenter = new UserPresenterImpl();
        startService(new Intent(this, CheckAppUpdateService.class));
        adapter = new AbsCommonAdapter<EntrepotBean>(mContext, R.layout.list_entrepot, listData) {
            @Override
            protected void convert(AbsViewHolder holder, EntrepotBean entrepotBean, int position) {
                holder.setText(R.id.name, entrepotBean.getName());
            }
        };
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        HttpRequestUtils.getInstance().postFormBuilder(GETSTORELIST, HttpParamsUtils.getStorelistParams()).build().execute(new ResultCallback(mContext) {
            @Override
            public void onCusResponse(BaseJson response) {
                EntrepotBeanList jsonBean = new Gson().fromJson(response.getData().toString(), EntrepotBeanList.class);
                if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                    listData.addAll(jsonBean.getItemList());
                    ckEdit.setText(listData.get(0).getName());
                    selectBean = listData.get(0);
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ckEdit.setText(listData.get(i).getName());
                list.setVisibility(View.GONE);
            }
        });
    }


    private Map<String, String> getLoginParams() {
        String pwd = "";
        if (!StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
            pwd = Base64Coder.encodeToString(user_pwd_edit.getText().toString().getBytes(), Base64.DEFAULT);
        }
        return HttpParamsUtils.getLoginParams(mContext, user_account_edit.getText().toString(), pwd);
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
                    ToastUtils.show(this, "工号或账号不能为空！");
                    break;
                }

                HttpRequestUtils.getInstance().postFormBuilder(LOGIN, getLoginParams()).build().execute(new DialogCallback(mContext) {
                    @Override
                    public void onCusResponse(BaseJson response) {
                        UserBean jsonBean = new Gson().fromJson(response.getData().toString(), UserBean.class);
                        if (null != jsonBean) {
                            saveUserInfo(jsonBean);
                            StartActUtils.start(mContext, HomeActivity_.class);
                            StartActUtils.finish(mContext);
                        } else {
                            ToastUtils.show(mContext, "请求失败");
                        }
                    }
                });
                break;
        }
    }


    public void saveUserInfo(UserBean jsonBean) {
        jsonBean.setLoginName(user_account_edit.getText().toString());
        jsonBean.setPwd(user_pwd_edit.getText().toString());
        if (null != selectBean) {
            jsonBean.setStoreId(selectBean.getId());
            jsonBean.setStoreName(selectBean.getName());
        }
        userPresenter.save(jsonBean);
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
