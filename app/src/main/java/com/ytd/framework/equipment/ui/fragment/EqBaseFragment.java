package com.ytd.framework.equipment.ui.fragment;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.bean.params.BaseEventbusParams;
import com.ytd.common.ui.fragment.refreshview.BaseLocalAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.equipment.ui.activity.EquipmentActivity;
import com.ytd.framework.equipment.ui.activity.EquipmentDetailsActivity_;
import com.ytd.framework.equipment.ui.activity.EquipmentScanDetailsResultActivity_;
import com.ytd.support.constants.fixed.JsonConstants;
import com.ytd.support.utils.ResUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ytd.common.bean.params.BaseEventbusParams.RE_SCAN_START;
import static com.ytd.framework.equipment.bean.EquipmentBean.LOOKSTATUS_TAG_TRUE;


public abstract class EqBaseFragment extends BaseLocalAbsRefreshFragment {


    protected IEquipmentPresenter equipmentPresenter;
    protected IProperyPresenter properyPresenter;


    void init() {
        EventBus.getDefault().register(this);
        super.supperInit(getActivity());
        equipmentPresenter = new EquipmentPresenterImpl();
        properyPresenter = new ProperyPresenterImpl();

    }

    @Subscribe
    public void onEventMainThread(BaseEventbusParams event) {
        if (event.getTag() == RE_SCAN_START) {
            getRefreshPtrLayoutView().autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    @Override
    public AbsCommonAdapter setRefreshAdapter() {
        return new AbsCommonAdapter<EquipmentBean>(getActivity(), R.layout.eq_all_refresh_list_item, (List<EquipmentBean>) mRefreshList) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final EquipmentBean bean, int position) {
                holder.setText(R.id.title, bean.getSBMC());
                holder.setText(R.id.count, "x" + bean.getCount());
                holder.setText(R.id.eqType, "设备型号：" + bean.getQYRQ());
                holder.setText(R.id.propertyID, "资产编号：" + bean.getSBBH());
                holder.setText(R.id.useAddress, "使用科室：" + bean.getKSMC());
                holder.setText(R.id.propertyStutas, "资产状态:" + bean.getPropertyStatus());
                holder.setText(R.id.startDate, "启用日期：" + bean.getQYRQ());
                holder.setText(R.id.unitName, bean.getDW());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.selectText);
                RoundTextView startWork = holder.getView(R.id.startWork);
                RoundTextView lookDetails = holder.getView(R.id.lookDetails);

                if (StringUtils.isEquals(bean.getState(), LOOKSTATUS_TAG_TRUE)) {//未盘点
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已盘点");
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未盘点");
                }

                if (indexTabs == 2) {
                    startWork.setVisibility(View.VISIBLE);
                } else {
                    startWork.setVisibility(View.GONE);
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
                        PropertyBean bean2 = properyPresenter.findById(getActivity(), getPropertyBean().getPDDH());
                        Map<String, Object> map = new HashMap<>();
                        map.put("bean", bean);
                        map.put("scanTag", 3);
                        map.put("propertyBean",bean2);
                        StartActUtils.start(mContext, EquipmentScanDetailsResultActivity_.class, map);
                    }
                });

            }

        };
    }


    @Override
    public List localSQLFindLimit(boolean isPage, int currPagetemp) {
        int currPage = currPagetemp - 1;
        return equipmentPresenter.findLimit(getActivity(), getPropertyBean().getPDDH(), getState(), currPage * JsonConstants.PAGE_SIZE, JsonConstants.PAGE_SIZE);
//        return equipmentPresenter.findAll(getActivity(),getPropertyBean().getMy_id());
    }

    public abstract String getState();

    protected PropertyBean getPropertyBean() {
        return ((EquipmentActivity) getActivity()).getPropertyBean();
    }

    private int indexTabs = 0;

    protected void setTabsTitleText(int indexTabs, int tabsTitileTxt) {
        this.indexTabs = indexTabs;
        String tab = ResUtils.getStr(tabsTitileTxt) + "  (" + equipmentPresenter.findTotalcount(getActivity(), getPropertyBean().getPDDH(), getState()) + ")";
        ((TextView) (((EquipmentActivity) getActivity()).getmTabs().getTabsContainer().getChildAt(indexTabs))).setText(tab);
    }
}
