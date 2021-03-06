package com.ytd.framework.main.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
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
import com.ytd.framework.main.bean.ConfigBean;
import com.ytd.framework.main.bean.EntrepotBean;
import com.ytd.framework.main.bean.EntrepotBeanList;
import com.ytd.framework.main.bean.PDStateBeanList;
import com.ytd.framework.main.bean.UserBean;
import com.ytd.framework.main.presenter.IConfigPresenter;
import com.ytd.framework.main.presenter.IEntrepotPresenter;
import com.ytd.framework.main.presenter.IPDStatePresenter;
import com.ytd.framework.main.presenter.IUserPresenter;
import com.ytd.framework.main.presenter.impl.ConfigPresenterImpl;
import com.ytd.framework.main.presenter.impl.EntrepotPresenterImpl;
import com.ytd.framework.main.presenter.impl.PDStatePresenterImpl;
import com.ytd.framework.main.presenter.impl.UserPresenterImpl;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.framework.main.ui.service.TokenService;
import com.ytd.support.http.DialogCallback;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.support.utils.SPUtils;
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

import static com.ytd.framework.main.presenter.impl.SplashPresenterImpl.FIRST_LAUNCHER_APP_TAG;
import static com.ytd.framework.main.ui.BaseApplication.userBean;
import static com.ytd.support.constants.fixed.UrlConstants.GETPDSTATELIST;
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
    public static final String LOGIN_NAME = "login_name";
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
        if (!SPUtils.getBoolean(FIRST_LAUNCHER_APP_TAG, true)) {
            if (NetUtils.isConnected(mContext)) {
                startService(new Intent(this, TokenService.class));
            } else {
                validityToken();
            }

        }
        adapter = new AbsCommonAdapter<EntrepotBean>(mContext, R.layout.list_entrepot, listData) {
            @Override
            protected void convert(AbsViewHolder holder, EntrepotBean entrepotBean, int position) {
                holder.setText(R.id.name, entrepotBean.getName());
            }
        };
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        getStoreList();
        getPDStateList();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ckEdit.setText(listData.get(i).getName());
                list.setVisibility(View.GONE);
                selectBean = listData.get(i);
            }
        });
        user_account_edit.setText(SPUtils.getString(LOGIN_NAME, "") + "");
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
                        setStoreBean(entrepotPresenter.findAll());
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


    //获取盘点状态列表
    private void getPDStateList() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(GETPDSTATELIST, HttpParamsUtils.getPDStateListParams()).build().execute(new ResultCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    PDStateBeanList jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), PDStateBeanList.class);
                    if (null != jsonBean && !ListUtils.isEmpty(jsonBean.getItemList())) {
                        IPDStatePresenter statePresenter = new PDStatePresenterImpl();
                        statePresenter.save(jsonBean.getItemList());
                    }
                }

                @Override
                public void onError(Call call, Exception e) {

                }
            });
        }
    }

    private void setStoreBean(List<EntrepotBean> setList) {
        if (!ListUtils.isEmpty(setList)) {
            listData.addAll(setList);
            ckEdit.setText(listData.get(0).getName());
            selectBean = listData.get(0);
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
                    try {
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        UserBean jsonBean = gson.fromJson(response.getData().toString(), UserBean.class);
                        if (null != jsonBean) {
                            saveUserInfo(jsonBean, false);
                            StartActUtils.start(mContext, HomeActivity_.class);
                            StartActUtils.finish(mContext);
                        } else {
                            ToastUtils.show(mContext, "请求失败");
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Exception e) {
                    try {
                        localLogin();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } else {
            localLogin();
        }
    }

    //离线登录
    public void localLogin() {
        try {
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
                userBean.setStoreId(selectBean.getMy_id());

            }
            userBean.setStoreName(ckEdit.getText().toString());
            UserBean loginUser = userPresenter.findLoginUser(userBean);
            if (null == loginUser) {
                ToastUtils.show(mContext, "离线登录失败，查看仓库及用户名或者密码是否正确");
            } else {
                validityLogin(loginUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            userBean = loginUser;
            SPUtils.putString(LOGIN_NAME, user_account_edit.getText().toString());
            StartActUtils.start(mContext, HomeActivity_.class);
            StartActUtils.finish(mContext);
        }
    }

    public void validityToken() {
        ConfigBean configBean = configPresenter.find();
        if (null != configBean) {
            long oneDay = 86400000;//一天的毫秒数
            long curr = System.currentTimeMillis();
            long sevenDay = oneDay * 30;//30天有效
            long interval = curr - configBean.getLastDate();
            if (interval > sevenDay) {
                tishi();
            }
        }
    }

    //扫描下一个
    public void tishi() {
        NormalDialog dialog = new NormalDialog(mContext);
        dialog.content("\n" + "您登录的账号授权已过期，请重新授权！" + "\n")//
                .btnNum(1)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("确定")//
                .show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnBtnClickL(

                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {//next
                        dialog.dismiss();
                        StartActUtils.start(mContext, ConfigActivity_.class, "tag", 1);
                        StartActUtils.finish(mContext);
                    }
                }
        );
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
            jsonBean.setStoreId(selectBean.getMy_id());
            jsonBean.setStoreName(selectBean.getName());
        }
        userPresenter.save(jsonBean);
        SPUtils.putString(LOGIN_NAME, user_account_edit.getText().toString());
        userBean = jsonBean;
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
