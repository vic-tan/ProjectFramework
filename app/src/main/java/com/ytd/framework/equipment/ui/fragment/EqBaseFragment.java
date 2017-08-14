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
import com.ytd.common.ui.fragment.refreshview.BaseAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IEquipmentPresenter;
import com.ytd.framework.equipment.presenter.impl.EquipmentPresenterImpl;
import com.ytd.framework.equipment.ui.activity.EquipmentActivity;
import com.ytd.framework.equipment.ui.activity.EquipmentDetailsActivity_;
import com.ytd.framework.equipment.ui.activity.EquipmentScanResultActivity_;
import com.ytd.support.utils.ResUtils;

import java.util.List;



public abstract class EqBaseFragment extends BaseAbsRefreshFragment {


    protected IEquipmentPresenter equipmentPresenter;


    void init() {
        super.supperInit(getActivity());
        equipmentPresenter = new EquipmentPresenterImpl();

    }




    @Override
    public AbsCommonAdapter setRefreshAdapter() {
        return new AbsCommonAdapter<EquipmentBean>(getActivity(), R.layout.eq_all_refresh_list_item, (List<EquipmentBean>) mRefreshList) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final EquipmentBean bean, int position) {
                holder.setText(R.id.title, bean.getTitle());
                holder.setText(R.id.count, "x"+bean.getCount());
                holder.setText(R.id.eqType, "设备型号：" + bean.getEqType());
                holder.setText(R.id.propertyID, "资产编号：" + bean.getEqId());
                holder.setText(R.id.useAddress, "使用科室：" + bean.getUseAddress());
                holder.setText(R.id.propertyStutas, "资产状态:" + bean.getPropertyStatus());
                holder.setText(R.id.startDate, "启用日期：" + bean.getStart_data());
                holder.setText(R.id.unitName,  bean.getUnitName());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.selectText);
                RoundTextView startWork = holder.getView(R.id.startWork);
                RoundTextView lookDetails = holder.getView(R.id.lookDetails);

                if (StringUtils.isEquals(bean.getLookStatus(), "0")) {//未盘点
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未盘点");
                    startWork.setVisibility(View.VISIBLE);
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已盘点");
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
                        StartActUtils.start(mContext,
                                EquipmentScanResultActivity_.class);
                    }
                });

            }

        };
    }




   protected PropertyBean getPropertyBean(){
       return  ((EquipmentActivity)getActivity()).getPropertyBean();
   }

   protected  void setTabsTitleText(int indexTabs,int tabsTitileTxt){
       ((TextView) (((EquipmentActivity) getActivity()).getmTabs().getTabsContainer().getChildAt(indexTabs))).setText(ResUtils.getStr(tabsTitileTxt) + "  ("+mRefreshList.size()+")");
   }
}