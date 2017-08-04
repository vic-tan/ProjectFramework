package com.tanlifei.exemple.refreshview.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tanlifei.common.bean.BaseJson;
import com.tanlifei.common.ui.activity.refreshview.BaseAbsRefreshActivity;
import com.tanlifei.exemple.refreshview.adapter.BannerAdapter;
import com.tanlifei.exemple.refreshview.bean.BannerBaen;
import com.tanlifei.exemple.refreshview.bean.TrainBean;
import com.tanlifei.framework.R;
import com.tanlifei.support.constants.fixed.UrlConstants;
import com.tanlifei.support.utils.GsonJsonUtils;
import com.tanlifei.support.utils.ImageLoadUtils;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.tlf.basic.uikit.viewpager.ChildViewPager;
import com.tlf.basic.uikit.viewpager.CircleIndicator;
import com.tlf.basic.utils.DateFormatUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载资源数据界面
 * Created by tanlifei on 16/1/19.
 */
@EActivity(R.layout.exemple_acitivity_ptr_listview)
public class ExempleListViewAndAdActivity extends BaseAbsRefreshActivity {
    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    ListViewFinal mLvGames;
    private List<BannerBaen> mBannerBaenList;

    void initBanner() {
        View v = LayoutInflater.from(this).inflate(R.layout.common_layout_viewpager, null);
        ChildViewPager vp = (ChildViewPager) v.findViewById(R.id.vp_banner);
        vp.setInterval(3000);
        vp.startAutoScroll();
        vp.setAdapter(new BannerAdapter(this,mBannerBaenList));
        mLvGames.addHeaderView(v);
        mPtrLayout.disableWhenHorizontalMove(true);
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        indicator.setViewPager(vp,mBannerBaenList.size());
    }

    private String getBannerDate() {
        return "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"8a987d5155b47f520155be577d260179\",\n" +
                "            \"name\": \"2016《培训》直达号--唯品大学（广州站）\",\n" +
                "            \"image\": \"http://www.ipeiban.com.cn/mstatic/M00/00/0A/Chj90Vd84M2EPAtdAAAAAL8WRus720.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"8a987d51551412a10155490db7a0102f\",\n" +
                "            \"name\": \"2016中国企业培训服务会展-成都站\",\n" +
                "            \"image\": \"http://www.ipeiban.com.cn/mstatic/M00/00/12/Chj9rFdg1KuEAzxHAAAAAF5-3BU070.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"8a987d51551412a10155299141f6056d\",\n" +
                "            \"name\": \"2016《培训》直达号--苏宁大学（南京站）\",\n" +
                "            \"image\": \"http://www.ipeiban.com.cn/mstatic/M00/00/04/Chj90VdWatGEf7SGAAAAAH3FNXs021.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"code\": \"0000\",\n" +
                "    \"msg\": \"操作成功\"\n" +
                "}";

    }

    @AfterViews
    void init() {

        super.supperInit();
        actionBarView.setActionbarTitle("listView 标准上拉下拉带广告轮播");
        mLvGames.setAdapter(getmRefreshAdapter());
        mLvGames.setEmptyView(mFlEmptyView);
        mLvGames.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestLoadMore();
            }
        });
        mBannerBaenList = new ArrayList<>();
        BaseJson bean = new Gson().fromJson(getBannerDate(), BaseJson.class);
        mBannerBaenList = GsonJsonUtils.fromJsonArray(new Gson().toJson(bean.getData()), BannerBaen.class);
        initBanner();
    }


    @Override
    public View getDataView() {
        return mLvGames;
    }

    @Override
    public Class<?> parseClassName() {
        return TrainBean.class;
    }

    @Override
    public View setPtrRootLayout() {
        return ptrRootLayout;
    }

    @Override
    public String requestUrl() {
        return UrlConstants.LIST_URL;
    }

    @Override
    public Map<String, String> requestParams() {
        Map<String, String> map = new HashMap<>();
        map.put("json", "{\n" +
                "    \"sid\": \"ipeiban2016\",\n" +
                "    \"pageNumber\": " + 1 + ",\n" +
                "    \"pageSize\": 10\n" +
                "}");
        return map;
    }


    @Override
    public AbsCommonAdapter setRefreshAdapter() {
        return new AbsCommonAdapter<TrainBean>(mContext, R.layout.exemple_refresh_list_item, (List<TrainBean>) mRefreshList) {
            @Override
            protected void convert(AbsViewHolder holder, TrainBean bean, int position) {
                ImageLoadUtils.getInstance().loadImageView((ImageView) holder.getView(R.id.cover),bean.getCover(), R.mipmap.ic_gf_default_photo);
                holder.setText(R.id.title, bean.getName());
                holder.setText(R.id.desc, "开始时间:" + DateFormatUtils.format(bean.getBegin_time(), DateFormatUtils.FormatType.DAY) + "\r\n"
                        + "结束时间:" + DateFormatUtils.format(bean.getEnd_time(), DateFormatUtils.FormatType.DAY));
            }

        };
    }

}
