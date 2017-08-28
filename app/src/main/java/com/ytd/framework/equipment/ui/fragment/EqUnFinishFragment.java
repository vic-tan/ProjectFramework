package com.ytd.framework.equipment.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.ytd.framework.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_FALSE;


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
        mLvGames.setAdapter(getmRefreshAdapter());
        mLvGames.setEmptyView(mFlEmptyView);
        mLvGames.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestLoadMore();
            }
        });
        setTabsTitleText(2,R.string.sliding_tab_strip_pager_unfinish);
    }
    @Override
    public void after() {
        setTabsTitleText(2,R.string.sliding_tab_strip_pager_unfinish);
        super.after();
    }

    @Override
    public String getState() {
        return LOOKSTATUS_TAG_FALSE;
    }


    @Override
    public View getDataView() {
        return mLvGames;
    }


    @Override
    public View setPtrRootLayout() {
        return ptrRootLayout;
    }


}
