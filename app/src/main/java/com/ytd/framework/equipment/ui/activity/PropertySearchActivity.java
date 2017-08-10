package com.ytd.framework.equipment.ui.activity;

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
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;

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
public class PropertySearchActivity extends BaseActionBarActivity {

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


    @AfterViews
    void init() {
        initActionBar();
        listData = new ArrayList<>();
        flEmptyView.setVisibility(View.GONE);
        adapter = new AbsCommonAdapter<PropertyBean>(mContext, R.layout.property_refresh_list_item, (List<PropertyBean>) listData) {
            @Override
            protected void convert(AbsViewHolder holder, final PropertyBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.phone, bean.getPhone());
                holder.setText(R.id.price, bean.getPrice());
                holder.setText(R.id.arce, bean.getArea());
                holder.setText(R.id.add, bean.getAddress());
                holder.setText(R.id.num, "一共有:" + bean.getFinshNum() + "/" + bean.getTotalNum() + "台设备已盘点");
                holder.setText(R.id.data, bean.getStart_data() + "一" + bean.getEnd_data());

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
        searchResult();

    }


    public void searchResult() {
        PropertyBean bean = new PropertyBean();
        bean.setName("汪总");
        bean.setStart_property("1002323.123");
        bean.setEnd_property("983673.343");
        bean.setTitle("汪科长的盘点");
        bean.setPrice("一万以下");
        bean.setAddress("中心实现室");
        bean.setEnd_data("2017年05月12日");
        bean.setStart_data("2017年05月10日");
        bean.setPhone("13823297564");
        bean.setArea("中心仓库");
        bean.setFinshNum("200");
        bean.setTotalNum("300");
        listData.add(bean);
        adapter.notifyDataSetChanged();
        if(ListUtils.isEmpty(listData)){
            flEmptyView.setVisibility(View.GONE);
        }else{
            flEmptyView.setVisibility(View.GONE);
        }
    }

}
