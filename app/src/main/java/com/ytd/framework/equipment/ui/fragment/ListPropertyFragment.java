package com.ytd.framework.equipment.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.tlf.basic.utils.StartActUtils;
import com.ytd.common.ui.fragment.refreshview.BaseAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.ui.activity.PropertyDetailsActivity_;
import com.ytd.support.constants.fixed.UrlConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.list_equipment_fragment)
public class ListPropertyFragment extends BaseAbsRefreshFragment {

    public static final String TAG = ListPropertyFragment.class.getSimpleName();

    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    ListViewFinal mLvGames;


    @AfterViews
    void init() {
        super.supperInit(getActivity());
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
        return PropertyBean.class;
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
        return new AbsCommonAdapter<PropertyBean>(getActivity(), R.layout.property_refresh_list_item, (List<PropertyBean>) mRefreshList) {
            @Override
            protected void convert(AbsViewHolder holder, final PropertyBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.phone, bean.getPhone());
                holder.setText(R.id.price, bean.getPrice());
                holder.setText(R.id.arce, bean.getArea());
                holder.setText(R.id.add, bean.getAddress());
                holder.setText(R.id.num, "一共有:" + bean.getFinshNum() + "/" + bean.getTotalNum() + "台设备已盘点");
                holder.setText(R.id.data, bean.getStart_data() + "一" + bean.getEnd_data());

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext, PropertyDetailsActivity_.class,"bean",bean);
                    }
                });
            }

        };
    }

    @Override
    public void after() {
        PropertyBean bean = new PropertyBean();
        bean.setName("汪总");
        bean.setStart_property("1002323.123");
        bean.setEnd_property("983673.343");
        bean.setTitle("汪科长的盘点");
        bean.setPrice("一万以下");
        bean.setAddress("中心实现室");
        bean.setEnd_data("2017年05月12日");
        bean.setStart_data("2017年05月10日");
        bean.setPhone("13823297564");
        bean.setArea("中心仓库");
        bean.setFinshNum("200");
        bean.setTotalNum("300");
        mRefreshList.add(bean);
        mRefreshAdapter.notifyDataSetChanged();
    }
}
