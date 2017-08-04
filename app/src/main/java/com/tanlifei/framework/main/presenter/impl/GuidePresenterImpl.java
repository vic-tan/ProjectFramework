package com.tanlifei.framework.main.presenter.impl;

import android.content.Context;
import android.view.View;

import com.tanlifei.framework.main.interactor.IGuideInteractor;
import com.tanlifei.framework.main.interactor.impl.GuideInteractorImpl;
import com.tanlifei.framework.main.presenter.IGuidePresenter;
import com.tanlifei.framework.main.ui.view.GuideView;

import java.util.List;

/**
 * Created by tanlifei on 16/5/12.
 */
public class GuidePresenterImpl implements IGuidePresenter {

    private GuideView guideView;
    private IGuideInteractor interactor;
    private Context mContext;

    public GuidePresenterImpl(GuideView guideView, Context mContext) {
        this.guideView = guideView;
        this.mContext = mContext;
        interactor = new GuideInteractorImpl();
    }

    @Override
    public List<View> addGuideViews(View.OnClickListener clickListener) {
        return interactor.addGuideViews(mContext, clickListener);
    }
}
