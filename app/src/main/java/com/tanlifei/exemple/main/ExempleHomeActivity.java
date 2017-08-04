package com.tanlifei.exemple.main;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.exemple.main.bean.ExempleHomeListBean;
import com.tanlifei.exemple.main.presenter.ExempleHomePresenter;
import com.tanlifei.framework.R;
import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.uikit.expandable.ExpandableTextView;
import com.tlf.basic.utils.StartActUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 首页界面
 * Created by tanlifei on 16/1/19.
 */
@EActivity(R.layout.exemple_activity_home)
public class ExempleHomeActivity extends BaseActionBarActivity {

    public static final String TAG = ExempleHomeActivity.class.getSimpleName();
    private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();
    private ExempleHomePresenter presenter;
    @ViewById(R.id.main_lv_list)
    ListView mList;


    @AfterViews
    void init() {
        super.initActionBar();
        actionBarView.setActionbarTitle("示例列表");
        presenter = new com.tanlifei.exemple.main.presenter.ExempleHomePresenterImpl();
        mList.setAdapter(new AbsCommonAdapter<ExempleHomeListBean>(this, R.layout.exemple_activity_home_list_item, presenter.addList()) {
            @Override
            protected void convert(AbsViewHolder holder, final ExempleHomeListBean bean, int position) {
                holder.setText(R.id.mexemple_list_item_name, bean.getTitle());
                ((ExpandableTextView) holder.getView(R.id.expand_text_view)).setmClickType(ExpandableTextView.ClickFooter);
                ((ExpandableTextView) holder.getView(R.id.expand_text_view)).setConvertText(mConvertTextCollapsedStatus, position, bean.getDesc());
                holder.getView(R.id.expand_text_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext, bean.getClazz());
                    }
                });
                holder.setOnClickListener(R.id.mexemple_list_item_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext, bean.getClazz());
                    }
                });
            }
        });
    }


    /**
     * 退出App
     */
    @Override
    public void onBackPressed() {
        exitApp();
    }


}
