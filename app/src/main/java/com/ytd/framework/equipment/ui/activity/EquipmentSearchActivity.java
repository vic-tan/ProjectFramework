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
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.support.utils.ResUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_TRUE;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.equipment_search_activity)
public class EquipmentSearchActivity extends BaseScannerReceiverActivity {

    public static final String TAG = EquipmentSearchActivity.class.getSimpleName();
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
    List<EquipmentBean> listData;
    IEquipmentPresenter presenter;

    @AfterViews
    void init() {
        initActionBar();
        listData = new ArrayList<>();
        flEmptyView.setVisibility(View.GONE);
        presenter = new EquipmentPresenterImpl();
        adapter = new AbsCommonAdapter<EquipmentBean>(mContext, R.layout.eq_all_refresh_list_item, listData) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final EquipmentBean bean, int position) {
                holder.setText(R.id.title, bean.getSBMC());
                holder.setText(R.id.count, "x" + bean.getCount());
                holder.setText(R.id.eqType, "设备型号：" + bean.getSBBH());
                holder.setText(R.id.propertyID, "资产编号：" + bean.getEqId());
                holder.setText(R.id.useAddress, "使用科室：" + bean.getKSMC());
                holder.setText(R.id.propertyStutas, "资产状态:" + bean.getPropertyStatus());
                holder.setText(R.id.startDate, "启用日期：" + bean.getQYRQ());
                holder.setText(R.id.unitName, bean.getDW());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.selectText);
                RoundTextView startWork = holder.getView(R.id.startWork);
                RoundTextView lookDetails = holder.getView(R.id.lookDetails);

                if (StringUtils.isEquals(bean.getState(), LOOKSTATUS_TAG_TRUE)) {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已盘点");
                    startWork.setVisibility(View.GONE);
                } else {//未盘点
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未盘点");
                    startWork.setVisibility(View.VISIBLE);
                }

                lookDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext,
                                EquipmentDetailsActivity_.class, "bean", bean);
                    }
                });

                startWork.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActUtils.start(mContext,
                                EquipmentDetailsActivity_.class, "bean", bean);
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
        List<EquipmentBean> list = presenter.findBySearch(mContext, search);
        if (ListUtils.isEmpty(list)) {
            ToastUtils.show(mContext, "暂时没有搜索您要找的数据!");
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
