package com.ytd.framework.main.presenter.impl;

import android.content.Context;
import android.view.View;

import com.ytd.framework.main.interactor.IGuideInteractor;
import com.ytd.framework.main.interactor.impl.GuideInteractorImpl;
import com.ytd.framework.main.presenter.IGuidePresenter;
import com.ytd.framework.main.ui.view.GuideView;

import java.util.List;

/**
 * Created by ytd on 16/5/12.
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
