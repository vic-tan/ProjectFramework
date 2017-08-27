package com.ytd.framework.equipment.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.support.utils.ResUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.property_search_activity)
public class PropertySearchActivity extends BaseScannerReceiverActivity {

    public static final String TAG = PropertySearchActivity.class.getSimpleName();
    @ViewById
    TextView searchBtn;
    @ViewById
    ImageView searchIcon;
    @ViewById
    EditText searchContent;
    @ViewById
    ListView list;
    @ViewById
    ProgressBar pbLoading;
    @ViewById
    TextView tvEmptyMessage;
    @ViewById
    FrameLayout flEmptyView;

    AbsCommonAdapter adapter;
    List<PropertyBean> listData;
    IProperyPresenter presenter;

    @AfterViews
    void init() {
        initActionBar();
        listData = new ArrayList<>();
        flEmptyView.setVisibility(View.GONE);
        presenter = new ProperyPresenterImpl();
        adapter = new AbsCommonAdapter<PropertyBean>(mContext, R.layout.property_refresh_list_item, (List<PropertyBean>) listData) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final PropertyBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.phone, bean.getPhone());
                holder.setText(R.id.price, bean.getPrice());
                holder.setText(R.id.arce, bean.getArea());
                holder.setText(R.id.add, bean.getAddress());
                holder.setText(R.id.start_num, bean.getFinshNum());
                holder.setText(R.id.end_num, "/" + bean.getTotalNum());
                holder.setText(R.id.data, bean.getRQ() + "一" + bean.getEnd_data());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.stutas);
                if (StringUtils.isEquals(bean.getSTATUS(), "0")) {//未完成
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未完成");
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已完成");
                }
                holder.setText(R.id.start_num, bean.getFinshNum());
                holder.setText(R.id.end_num, "/" + bean.getTotalNum());
                holder.setText(R.id.data, bean.getRQ() + "一" + bean.getEnd_data());

                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext, PropertyDetailsActivity_.class, "bean", bean);
                    }
                });
            }

        };
        list.setAdapter(adapter);
    }

    @Click(R.id.searchBtn)
    void click(View v) {
        if (StringUtils.isEmpty(searchContent.getText().toString())) {
            ToastUtils.show(mContext, "搜索内容不能为空");
            return;
        }
        searchResult(searchContent.getText().toString());
    }


    public void searchResult(String search) {
        listData.clear();
        List<PropertyBean> list = presenter.findBySearch(mContext, search);
        if(ListUtils.isEmpty(list)){
            ToastUtils.show(mContext,"暂时没有搜索您要找的数据!");
        }
        listData.addAll(list);
        adapter.notifyDataSetChanged();
        if (ListUtils.isEmpty(listData)) {
            flEmptyView.setVisibility(View.GONE);
        } else {
            flEmptyView.setVisibility(View.GONE);
        }
    }

}
