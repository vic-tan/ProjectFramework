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
import com.tlf.basic.utils.NetUtils;
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
import com.ytd.framework.main.presenter.IEntrepotPresenter;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.EntrepotPresenterImpl;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.BaseApplication;
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
    IEntrepotPresenter entrepotPresenter;
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
        entrepotPresenter = new EntrepotPresenterImpl();
        startService(new Intent(this, CheckAppUpdateService.class));
        adapter = new AbsCommonAdapter<EntrepotBean>(mContext, R.layout.list_entrepot, listData) {
            @Override
            protected void convert(AbsViewHolder holder, EntrepotBean entrepotBean, int position) {
                holder.setText(R.id.name, entrepotBean.getName());
            }
        };
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        getStoreList();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ckEdit.setText(listData.get(i).getName());
                list.setVisibility(View.GONE);
            }
        });
    }


    //仓库列表处理
    private void getStoreList() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(GETSTORELIST, HttpParamsUtils.getStorelistParams()).build().execute(new ResultCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    EntrepotBeanList jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), EntrepotBeanList.class);
                    if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                        entrepotPresenter.save(jsonBean.getItemList());
                        setStoreBean(jsonBean.getItemList());
                    }
                }

                @Override
                public void onError(Call call, Exception e) {
                    try {
                        setStoreBean(entrepotPresenter.findAll());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } else {
            try {
                setStoreBean(entrepotPresenter.findAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setStoreBean(List<EntrepotBean> setList) {
        if (!ListUtils.isEmpty(setList)) {
            listData.addAll(setList);
            ckEdit.setText(setList.get(0).getName());
            selectBean = setList.get(0);
        }
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
                getLogin();
                break;
        }
    }

    //登录业务处理
    private void getLogin() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(LOGIN, getLoginParams()).build().execute(new DialogCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    UserBean jsonBean = new Gson().fromJson(response.getData().toString(), UserBean.class);
                    if (null != jsonBean) {
                        saveUserInfo(jsonBean, false);
                        StartActUtils.start(mContext, HomeActivity_.class);
                        StartActUtils.finish(mContext);
                    } else {
                        ToastUtils.show(mContext, "请求失败");
                    }
                }

                @Override
                public void onError(Call call, Exception e) {
                    localLogin();
                }
            });
        } else {
            localLogin();
        }
    }

    //离线登录
    public void localLogin() {
        String pwd = "";
        if (!StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
            pwd = Base64Coder.encodeToString(user_pwd_edit.getText().toString().getBytes(), Base64.DEFAULT);
        }
        UserBean userBean = new UserBean();
        userBean.setLoginName(user_account_edit.getText().toString());
        userBean.setPwd(pwd);
        if (null == selectBean) {
            userBean.setStoreId("0101");
        } else {
            if(StringUtils.isEmpty(selectBean.getStoreId())){
                userBean.setStoreId(selectBean.getId());
            }else {
                userBean.setStoreId(selectBean.getStoreId());
            }
        }
        UserBean loginUser = userPresenter.findLoginUser(userBean);
        if (null == loginUser) {
            ToastUtils.show(mContext, "离线登录失败，查看仓库及用户名或者密码是否正确");
        } else {
            validityLogin(loginUser);
        }

    }

    //离线七天有效登录
    public void validityLogin(UserBean loginUser) {
        long oneDay = 86400000;//一天的毫秒数
        long curr = System.currentTimeMillis();
        long sevenDay = oneDay * 7;//7天有效
        long interval = curr - loginUser.getLastDate();
        if (interval > sevenDay) {
            ToastUtils.show(mContext, "离线登录七天有效，您离线已过期，请连接网络在线登录");
        } else {
            BaseApplication.userBean = loginUser;
            StartActUtils.start(mContext, HomeActivity_.class);
            StartActUtils.finish(mContext);
        }


    }

    public void saveUserInfo(UserBean jsonBean, boolean isOnlineLogin) {
        if (!isOnlineLogin) {//在线
            jsonBean.setLastDate(System.currentTimeMillis());
        }
        String pwd = "";
        if (!StringUtils.isEmpty(user_pwd_edit.getText().toString())) {
            pwd = Base64Coder.encodeToString(user_pwd_edit.getText().toString().getBytes(), Base64.DEFAULT);
        }
        jsonBean.setLoginName(user_account_edit.getText().toString());
        jsonBean.setPwd(pwd);
        if (null != selectBean) {
            jsonBean.setStoreId(selectBean.getId());
            jsonBean.setStoreName(selectBean.getName());
        }
        userPresenter.save(jsonBean);
        BaseApplication.userBean = jsonBean;
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
