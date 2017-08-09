package com.ytd.framework.equipment.ui.fragment;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.ui.fragment.refreshview.BaseAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.ui.activity.PropertyDetailsActivity_;
import com.ytd.support.constants.fixed.UrlConstants;
import com.ytd.support.utils.ResUtils;

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
public class EqFinishFragment extends BaseAbsRefreshFragment {

    public static final String TAG = EqFinishFragment.class.getSimpleName();

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
        return new AbsCommonAdapter<EquipmentBean>(getActivity(), R.layout.eq_all_refresh_list_item, (List<EquipmentBean>) mRefreshList) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final EquipmentBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.count, "x"+bean.getCount());
                holder.setText(R.id.eqType, "设备型号：" + bean.getEqType());
                holder.setText(R.id.propertyID, "资产编号：" + bean.getEqId());
                holder.setText(R.id.useAddress, "使用科室：" + bean.getUseAddress());
                holder.setText(R.id.propertyStutas, "资产状态:" + bean.getPropertyStatus());
                holder.setText(R.id.startDate, "启用日期：" + bean.getStart_data());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.selectText);
                RoundTextView startWork = holder.getView(R.id.startWork);
                RoundTextView lookDetails = holder.getView(R.id.lookDetails);

                if (StringUtils.isEquals(bean.getLookStatus(), "0")) {//未盘点
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未盘点");
                    startWork.setVisibility(View.VISIBLE);
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已盘点");
                    startWork.setVisibility(View.GONE);
                }

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext, PropertyDetailsActivity_.class, "bean", bean);
                    }
                });


            }

        };
    }


    @Override
    public void after() {
        EquipmentBean bean = new EquipmentBean();
        bean.setTitle("血管仿真模型");
        bean.setEqType("HT-EW-DAFALFS开号");
        bean.setCount("1");
        bean.setEqId("983673q23489341");
        bean.setUseAddress("中心实现室");
        bean.setStart_data("2017年05月10日");
        bean.setPropertyStatus("在用");
        bean.setLookStatus("2");
        mRefreshList.add(bean);
        mRefreshAdapter.notifyDataSetChanged();
    }
}
