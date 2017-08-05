package com.ytd.framework.main.ui.fragment;


import android.app.Fragment;
import android.view.View;

import com.tlf.basic.utils.ToastUtils;
import com.ytd.framework.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.home_fragment)
public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Click({R.id.equipment, R.id.more})
    void click(View v) {
        switch (v.getId()) {
            case R.id.equipment:
                break;
            case R.id.more:
                ToastUtils.show(getActivity(), R.string.wait);
                break;
        }
    }

}
