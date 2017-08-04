/*
 * Copyright (c) 2015 Kaopiz Software Co., Ltd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tanlifei.exemple.actionbar;

import android.os.Bundle;
import android.view.View;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tanlifei.uikit.actionbar.ActionBarOptViewTagLevel;
import com.tanlifei.uikit.actionbar.OnOptClickListener;
import com.tlf.basic.utils.ToastUtils;


public class ExempleActionBarFourActivity extends BaseActionBarActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemple_actionbar_activity_four);
        /**
         * 1、必须在setContent(view ) 以后
         * 2、必须继承BaseActionBarActivity
         * 3、当前Activity 布局中加入 ActionBarView
         * 4、必须先调用BaseActionBarActivity.initActionBar()方法在做其它的操作
         * 5、代码设置的布局同样可设置
         */
        super.initActionBar();//必须
        //actionBarView.setActionbarTitle("这是代码设置标题");
        //actionBarView.setActionbarBackDimiss(true);
        actionBarView.setActionbarOptLeftText("清空");
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                ToastUtils.show(mContext, viewTag.value() + "");
            }
        });
    }



}
