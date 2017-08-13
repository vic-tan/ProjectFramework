package com.ytd.framework.equipment.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.support.constants.fixed.UrlConstants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.list_equipment_fragment)
public class EqUnFinishFragment extends EqBaseFragment {

    public static final String TAG = EqUnFinishFragment.class.getSimpleName();

    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    ListViewFinal mLvGames;


    @AfterViews
    void init() {
        super.init();
        mRefreshList.addAll(equipmentPresenter.findByState(getActivity(), getPropertyBean().getMy_id(),"0"));
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
    public void after() {
        setTabsTitleText(2,R.string.sliding_tab_strip_pager_unfinish);
        mRefreshAdapter.notifyDataSetChanged();
    }
}
