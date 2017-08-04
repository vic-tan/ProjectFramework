package com.tanlifei.exemple.refreshview.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tanlifei.common.ui.activity.refreshview.BaseRvRefreshActivity;
import com.tanlifei.exemple.refreshview.bean.TrainBean;
import com.tanlifei.framework.R;
import com.tanlifei.support.constants.fixed.UrlConstants;
import com.tanlifei.support.utils.ImageLoadUtils;
import com.tlf.basic.base.adapter.recycler.RvCommonAdapter;
import com.tlf.basic.base.adapter.recycler.RvViewHolder;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.tlf.basic.refreshview.more.RecyclerViewFinal;
import com.tlf.basic.utils.DateFormatUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载资源数据界面
 * Created by tanlifei on 16/1/19.
 */
@EActivity(R.layout.exemple_acitivity_ptr_recycler)
public class ExempleRecyclerViewActivity extends BaseRvRefreshActivity {

    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    public RecyclerViewFinal mLvGames;



    @AfterViews
    void init() {
        super.supperInit();
        actionBarView.setActionbarTitle("RecyclerView 刷新");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLvGames.setLayoutManager(linearLayoutManager);
        mLvGames.setAdapter(getmRefreshAdapter());
        mLvGames.setEmptyView(mFlEmptyView);
        mLvGames.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestLoadMore();
            }
        });
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
    public RvCommonAdapter setRefreshAdapter() {
        return new RvCommonAdapter<TrainBean>(mContext, R.layout.exemple_refresh_list_item, (List<TrainBean>) mRefreshList) {
            @Override
            protected void convert(RvViewHolder holder, TrainBean bean, int position) {
                ImageLoadUtils.getInstance().loadImageView((ImageView) holder.getView(R.id.cover),bean.getCover(), R.mipmap.ic_gf_default_photo);
                holder.setText(R.id.title, bean.getName());
                holder.setText(R.id.desc, "开始时间:" + DateFormatUtils.format(bean.getBegin_time(), DateFormatUtils.FormatType.DAY) + "\r\n"
                        + "结束时间:" + DateFormatUtils.format(bean.getEnd_time(), DateFormatUtils.FormatType.DAY));
            }

        };
    }
}
