package com.tanlifei.framework.main.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.tanlifei.framework.R;
import com.tanlifei.framework.main.bean.AppUpdateBean;
import com.tanlifei.framework.main.ui.service.AppDownloadService;
import com.tanlifei.framework.main.ui.service.CheckAppUpdateService;
import com.tanlifei.support.utils.DialogTools;
import com.tanlifei.support.utils.ResUtils;
import com.tlf.basic.base.autolayout.AutoLayoutActivity;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.utils.ActivityManager;
import com.tlf.basic.utils.AppUtils;
import com.tlf.basic.utils.InflaterUtils;
import com.tlf.basic.utils.StringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

/**
 * app服务类， 用于服务中间处理，比较从后台开一人服务，在界面显示提示，如升级，网络断开提示之类
 * Created by tanlifei on 16/1/19.
 */
@Fullscreen //全屏
@EActivity(R.layout.main_activity_app_update)
public class AppServiceActivity extends AutoLayoutActivity {

    protected Context mContext;
    /**
     * 必须传入一个tag 来区分从哪个后台入进
     */
    public static String INTENT_TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManager.getActivityManager().addActivity(this);
    }

    @AfterViews
    void init() {
        String tag = getIntent().getStringExtra(INTENT_TAG);
        if (!StringUtils.isEmpty(tag) && StringUtils.isEquals(tag, "appUpdate")) {//app 版本升级业务处理
            appUpdate((AppUpdateBean) getIntent().getParcelableExtra("bean"));
        } else {
            colseAcitvity();
        }

    }

    /**
     * app 版本升级业务处理
     *
     * @param appUpdateBean
     */
    private void appUpdate(final AppUpdateBean appUpdateBean) {
        if (null != appUpdateBean && appUpdateBean.getVersion_code() > AppUtils.getVersionCode(mContext)) {
           /* NormalScrollViewDialog dialog = new NormalScrollViewDialog(this) {
                @Override
                public void setUiBeforShow() {
                    getmTvOk().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), AppDownloadService.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("bean", appUpdateBean);
                            intent.putExtras(bundle);
                            startService(intent);
                            dismiss();
                        }
                    });
                    getmTvExit().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismiss();
                        }
                    });
                }
            };
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
            dialog.getmTvContent().setText(Html.fromHtml(appUpdateBean.getDesc()).toString());
            dialog.setCanceledOnTouchOutside(false);*/

            View contetView = InflaterUtils.inflate(this,R.layout.main_version_content_view);
            TextView contetent = (TextView) contetView.findViewById(R.id.mTvContent);
            contetent.setText(Html.fromHtml(appUpdateBean.getDesc()).toString());
            DialogTools.getInstance(mContext).title(ResUtils.getStr(R.string.app_update_dialog_title)).content(Html.fromHtml(appUpdateBean.getDesc()).toString()).setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick(View v, Dialog dialog) {
                    dialog.dismiss();
                }
            }, new OnBtnClickL() {
                @Override
                public void onBtnClick(View v, Dialog dialog) {
                    Intent intent = new Intent(getBaseContext(), AppDownloadService.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bean", appUpdateBean);
                    intent.putExtras(bundle);
                    startService(intent);
                    dialog.dismiss();
                }
            }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    stopService(new Intent(mContext, AppDownloadService.class));
                    colseAcitvity();
                }
            }).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(mContext, CheckAppUpdateService.class));//停止查检升级服务
        colseAcitvity();

    }


    @Override
    public void onBackPressed() {
        colseAcitvity();
    }

    private void colseAcitvity() {
        ActivityManager.getActivityManager().finishActivityDefultAnim(this);
        ((Activity) mContext).overridePendingTransition(R.anim.common_activity_default_finish, R.anim.common_activity_default_finish);
    }
}
