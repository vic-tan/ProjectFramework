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


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.list_equipment_fragment)
public class EqFinishFragment extends EqBaseFragment {

    public static final String TAG = EqFinishFragment.class.getSimpleName();

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
        setTabsTitleText(1,R.string.sliding_tab_strip_pager_finish);
    }

    @Override
    public String getState() {
        return "1";
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
