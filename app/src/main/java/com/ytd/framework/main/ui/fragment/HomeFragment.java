package com.ytd.framework.main.ui.fragment;


import android.app.Fragment;
import android.view.View;

import com.tlf.basic.uikit.viewpager.ChildViewPager;
import com.tlf.basic.uikit.viewpager.CircleIndicator;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.framework.R;
import com.ytd.framework.equipment.ui.activity.PropertyActivity_;
import com.ytd.framework.main.adapter.BannerAdapter;
import com.ytd.framework.main.bean.BannerBaen;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.home_fragment)
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();
    @ViewById
    ChildViewPager vpBanner;
    @ViewById
    CircleIndicator indicator;
    private List<BannerBaen> mBannerBaenList;


    void initBanner() {
        vpBanner.setInterval(3000);
        vpBanner.startAutoScroll();
        vpBanner.setAdapter(new BannerAdapter(getActivity(), mBannerBaenList));
        indicator.setViewPager(vpBanner, mBannerBaenList.size());
    }

    @AfterViews
    void init() {
        mBannerBaenList = new ArrayList<>();
        getBannerDate();
        initBanner();
    }

    private void getBannerDate() {
        BannerBaen baen = new BannerBaen();
        baen.setImage(R.mipmap.banner);
        BannerBaen baen2 = new BannerBaen();
        baen2.setImage(R.mipmap.banner_tow);
        BannerBaen baen3 = new BannerBaen();
        baen3.setImage(R.mipmap.banner_three);
        mBannerBaenList.add(baen);
        mBannerBaenList.add(baen2);
        mBannerBaenList.add(baen3);


    }

    @Click({R.id.equipment, R.id.more})
    void click(View v) {
        switch (v.getId()) {
            case R.id.equipment:
                StartActUtils.start(getActivity(), PropertyActivity_.class);
                break;
            case R.id.more:
                ToastUtils.show(getActivity(), R.string.wait);
                break;
        }
    }

}
