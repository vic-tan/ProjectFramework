package com.ytd.framework.main.interactor;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by ytd on 16/5/12.
 */
public interface IGuideInteractor {


    List<View> addGuideViews(Context context, View.OnClickListener listener);
}
