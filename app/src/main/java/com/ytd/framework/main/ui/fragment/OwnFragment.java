package com.ytd.framework.main.ui.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tlf.basic.base.autolayout.AutoLinearLayout;
import com.tlf.basic.uikit.dialog.listener.OnOperItemClickL;
import com.tlf.basic.uikit.dialog.widget.ActionSheetDialog;
import com.tlf.basic.utils.ActivityManager;
import com.tlf.basic.utils.AppCacheUtils;
import com.ytd.framework.R;
import com.ytd.framework.main.ui.service.AppDownloadService;
import com.ytd.framework.main.ui.service.CheckAppUpdateService;
import com.ytd.support.utils.UnFinshUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.own_fragment)
public class OwnFragment extends Fragment {
    public static final String TAG = OwnFragment.class.getSimpleName();


    @ViewById
    AutoLinearLayout root_view;

    @ViewById
    TextView name;

    @AfterViews
    void init() {
        name.setText(AppCacheUtils.getInstance(getActivity()).getString("user_name"));
    }

    @Click({R.id.skin_layout, R.id.lianxi_layout, R.id.eixt})
    void click(View v) {
        switch (v.getId()) {
            case R.id.skin_layout:
                UnFinshUtils.unFinshToast(getActivity());
                break;
            case R.id.lianxi_layout:
                UnFinshUtils.unFinshToast(getActivity());
                break;
            case R.id.eixt:
                ActionSheetDialog();
                break;
        }
    }


    private void ActionSheetDialog() {
        final String[] stringItems = {"确定退出"};
        final ActionSheetDialog dialog = new ActionSheetDialog(getActivity(), stringItems, root_view);
        dialog.title("您确定要退出登录吗？")//
                .titleTextSize_PX(12)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(Dialog dialog, AdapterView<?> parent, View view, int position, long id) {
                getActivity().stopService(new Intent(getActivity(), CheckAppUpdateService.class));//查检升级服务
                getActivity().stopService(new Intent(getActivity(), AppDownloadService.class));//下载app升级服务
                //finish所有页面和kill app
                ActivityManager.getActivityManager().appExit(getActivity());
                dialog.dismiss();
            }
        });
    }

}
