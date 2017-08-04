package com.ytd.framework.main.interactor.impl;

import android.content.Context;
import android.view.View;

import com.ytd.framework.R;
import com.ytd.framework.main.interactor.IGuideInteractor;
import com.tlf.basic.utils.InflaterUtils;
import com.tlf.basic.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytd on 16/5/12.
 */
public class GuideInteractorImpl implements IGuideInteractor {

    private List<View> guideViews;

    @Override
    public List<View> addGuideViews(Context context, View.OnClickListener listener) {
        if (ListUtils.isEmpty(guideViews)) {
            guideViews = new ArrayList<>();
            guideViews.add(InflaterUtils.inflate(context, R.layout.main_activity_guide_page_one));
            guideViews.add(InflaterUtils.inflate(context, R.layout.main_activity_guide_page_two));
            guideViews.add(InflaterUtils.inflate(context, R.layout.main_activity_guide_page_three));
            View v = InflaterUtils.inflate(context, R.layout.main_activity_guide_page_four);
            v.findViewById(R.id.imgBtn_start).setOnClickListener(listener);
            guideViews.add(v);

        }
        return guideViews;
    }
}
