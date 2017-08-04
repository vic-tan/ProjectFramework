package com.tanlifei.exemple.main.interactor;

import com.tanlifei.exemple.actionbar.ExempleActionBarMainActivity;
import com.tanlifei.exemple.baseadapter.ExempleBaseAdpterMainActivity;
import com.tanlifei.exemple.dialog.ui.ExempleDialogSimpleHomeActivity;
import com.tanlifei.exemple.kprogresshud.ExempleKProgresshudMainActivity;
import com.tanlifei.exemple.main.bean.ExempleHomeListBean;
import com.tanlifei.exemple.okhttp.ExempleOkHttpMainActivity;
import com.tanlifei.exemple.photo.ExemplePhotoMainActivity;
import com.tanlifei.exemple.refreshview.ui.ExempleRefreshViewMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanlifei on 16/5/12.
 */
public class ExempleHomeInteractorImpl implements ExempleHomeInteractor {


    @Override
    public List<ExempleHomeListBean> addList() {
        List<ExempleHomeListBean> list = new ArrayList<>();

        list.add(new ExempleHomeListBean("CommonActionBar", ExempleActionBarMainActivity.class,
                "自定义简单的ActionBar，使用简单方便，全适配"));
        list.add(new ExempleHomeListBean("BaseAdater", ExempleBaseAdpterMainActivity.class,
                "Android 万能的Adapter for ListView,RecyclerView,GridView等，支持多种Item类型的情况。简单的数据绑定(ListView与其使用方式一致)只需要简单的将Adapter继承CommonAdapter，复写convert方法即可。省去了自己编写ViewHolder等大量的重复的代码。" +
                "\n可以通过holder.getView(id)拿到任何控件。\nViewHolder中封装了大量的常用的方法，比如holder.setText(id,text)，holder.setOnClickListener(id,listener)等，可以支持使用。"));
        list.add(new ExempleHomeListBean("KProgresshud共用的加载框", ExempleKProgresshudMainActivity.class, "Android的实现ProgressHUD,类似于MBProgressHUD SVProgressHUD。"));

        list.add(new ExempleHomeListBean("OkHttp公用请求示例", ExempleOkHttpMainActivity.class, "APP开发中常见的请求接口过程中，界面显示，及跳转方式.."));
        list.add(new ExempleHomeListBean("下拉上拉刷新", ExempleRefreshViewMainActivity.class, "APP开发中常见的请求接口过程中，界面显示，及跳转方式.."));
        list.add(new ExempleHomeListBean("公用的Dailog", ExempleDialogSimpleHomeActivity.class, "分为常用一个按钮，和两个按钮，底部上弹，实现简单，自定义布局，适配所有android分辨率机型。"));
        list.add(new ExempleHomeListBean("图片相关应用", ExemplePhotoMainActivity.class, "分为常用图片加载缓存，图片显示特效，图片浏览等"));
        return list;
    }


}
