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
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.ui.fragment.refreshview.BaseLocalAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.equipment.ui.activity.PropertyActivity;
import com.ytd.framework.equipment.ui.activity.PropertyDetailsActivity_;
import com.ytd.support.constants.fixed.JsonConstants;
import com.ytd.support.utils.ResUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.list_equipment_fragment)
public class ListPropertyFragment extends BaseLocalAbsRefreshFragment {

    public static final String TAG = ListPropertyFragment.class.getSimpleName();


    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    ListViewFinal mLvGames;
    IProperyPresenter properyPresenter;


    @AfterViews
    void init() {
        super.supperInit(getActivity());
        properyPresenter = new ProperyPresenterImpl();
        mLvGames.setAdapter(getmRefreshAdapter());
        mLvGames.setEmptyView(mFlEmptyView);
        mLvGames.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestLoadMore();
            }
        });
        setTabTotalcount();
    }


    public void setTabTotalcount() {
        String tab = ResUtils.getStr(R.string.sliding_tab_strip_pager_has_heaer) + "  (" + properyPresenter.findTotalcount(getActivity()) + ")";
        ((TextView) (((PropertyActivity) getActivity()).getmTabs().getTabsContainer().getChildAt(0))).setText(tab);
    }

    @Override
    public View getDataView() {
        return mLvGames;
    }


    @Override
    public View setPtrRootLayout() {
        return ptrRootLayout;
    }


    @Override
    public AbsCommonAdapter setRefreshAdapter() {
        return new AbsCommonAdapter<PropertyBean>(getActivity(), R.layout.property_refresh_list_item, (List<PropertyBean>) mRefreshList) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final PropertyBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.phone, bean.getPhone());
                holder.setText(R.id.price, bean.getPrice());
                holder.setText(R.id.arce, bean.getArea());
                holder.setText(R.id.add, bean.getAddress());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.stutas);
                if (StringUtils.isEquals(bean.getStatus(), "0")) {//未完成
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未完成");
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已完成");
                }
                holder.setText(R.id.start_num, bean.getFinshNum());
                holder.setText(R.id.end_num, "/" + bean.getTotalNum());
                holder.setText(R.id.data, bean.getStart_data() + "一" + bean.getEnd_data());

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
    public List localSQLFindLimit(boolean isPage, int currPagetemp) {
        int currPage = currPagetemp - 1;
        return properyPresenter.findLimit(getActivity(), currPage * JsonConstants.PAGE_SIZE, JsonConstants.PAGE_SIZE);
    }


    @Override
    public void after() {
        setTabTotalcount();
        super.after();
    }
}
