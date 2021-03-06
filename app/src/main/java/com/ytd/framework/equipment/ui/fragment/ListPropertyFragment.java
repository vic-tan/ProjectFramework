package com.ytd.framework.equipment.ui.fragment;


import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tlf.basic.base.adapter.abslistview.AbsCommonAdapter;
import com.tlf.basic.base.adapter.abslistview.AbsViewHolder;
import com.tlf.basic.refreshview.more.ListViewFinal;
import com.tlf.basic.refreshview.more.OnLoadMoreListener;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.base.refreshview.ui.EmptyView;
import com.ytd.common.bean.params.BaseEventbusParams;
import com.ytd.common.ui.fragment.refreshview.BaseLocalAbsRefreshFragment;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.equipment.presenter.IProperyPresenter;
import com.ytd.framework.equipment.presenter.impl.ProperyPresenterImpl;
import com.ytd.framework.equipment.ui.activity.PropertyActivity;
import com.ytd.framework.equipment.ui.activity.PropertyDetailsActivity_;
import com.ytd.framework.main.ui.BaseApplication;
import com.ytd.support.constants.fixed.JsonConstants;
import com.ytd.support.utils.ResUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.ytd.common.bean.params.BaseEventbusParams.RE_SCAN_START;
import static com.ytd.common.bean.params.BaseEventbusParams.RE_START;
import static com.ytd.common.bean.params.BaseEventbusParams.UPDATE_START;
import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.list_equipment_fragment)
public class ListPropertyFragment extends BaseLocalAbsRefreshFragment {

    public static final String TAG = ListPropertyFragment.class.getSimpleName();


    @ViewById(R.id.ptr_root_layout)
    RelativeLayout ptrRootLayout;
    @ViewById(R.id.lv_games)
    ListViewFinal mLvGames;
    IProperyPresenter properyPresenter;


    @AfterViews
    void init() {
        EventBus.getDefault().register(this);
        super.supperInit(getActivity());
        properyPresenter = new ProperyPresenterImpl();
        mLvGames.setAdapter(getmRefreshAdapter());
        mLvGames.setEmptyView(mFlEmptyView);
        mLvGames.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestLoadMore();
            }
        });
        setTabTotalcount();
    }

    @Subscribe
    public void onEventMainThread(BaseEventbusParams event) {
        if (event.getTag() == RE_START || event.getTag() == RE_SCAN_START || event.getTag() == UPDATE_START) {
            getRefreshPtrLayoutView().autoRefresh();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    public void setTabTotalcount() {
        String tab = ResUtils.getStr(R.string.sliding_tab_strip_pager_has_heaer) + "  (" + properyPresenter.findTotalcount(getActivity()) + ")";
        ((TextView) (((PropertyActivity) getActivity()).getmTabs().getTabsContainer().getChildAt(0))).setText(tab);
    }

    @Override
    public View getDataView() {
        return mLvGames;
    }


    @Override
    public View setPtrRootLayout() {
        return ptrRootLayout;
    }


    @Override
    public AbsCommonAdapter setRefreshAdapter() {
        return new AbsCommonAdapter<PropertyBean>(getActivity(), R.layout.property_refresh_list_item, (List<PropertyBean>) mRefreshList) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void convert(AbsViewHolder holder, final PropertyBean bean, final int position) {
                holder.setText(R.id.title, bean.getBZ());
//                holder.setText(R.id.phone, bean.getPhone());
//                holder.setText(R.id.price, bean.getPrice());
                holder.setText(R.id.arce, BaseApplication.userBean.getStoreName());
//                holder.setText(R.id.add, bean.getAddress());
                ImageView selectTag = holder.getView(R.id.selectTag);
                TextView selectText = holder.getView(R.id.stutas);
                if (StringUtils.isEquals(bean.getSTATUS(), UPDATELOAD_TAG_TRUE)) {//已完成
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                    selectText.setText("已完成");
                } else {
                    selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                    selectText.setText("未完成");
                }
                holder.setText(R.id.start_num, bean.getFinshNum());
                holder.setText(R.id.end_num, "/" + bean.getTotalNum());
                if (!StringUtils.isEmpty(bean.getRQ())) {
                    holder.setText(R.id.data, bean.getRQ());
                }
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PropertyBean bean1 = properyPresenter.findById(mContext, bean.getPDDH());
                        StartActUtils.start(mContext, PropertyDetailsActivity_.class, "bean", bean1);
                    }
                });
                holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (StringUtils.isEquals(bean.getSTATUS(), "0")) {
                            NormalDialog(bean, position);
                        } else {
                            NormalDialogStyleTwo(bean, position);
                        }

                    }
                });
            }

        };
    }


    private void NormalDialog(final PropertyBean bean, final int position) {
        final NormalDialog dialog = new NormalDialog(getActivity());
        dialog.isTitleShow(false);
        dialog.contentGravity(Gravity.CENTER);
        dialog.content("\n" + "您还未上传盘点单,确定要删除该盘点单吗？？" + "\n")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        delete(bean, position);
                        dialog.dismiss();
                    }
                });

    }

    private void NormalDialogStyleTwo(final PropertyBean bean, final int position) {
        final NormalDialog dialog = new NormalDialog(getActivity());
        dialog.isTitleShow(false);
        dialog.contentGravity(Gravity.CENTER);
        dialog.content("\n" + "您确定要删除该盘点单吗？？" + "\n")
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        delete(bean, position);
                        dialog.dismiss();
                    }
                });

    }

    private void delete(final PropertyBean bean, final int position) {
        KProgressHUD hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .setLabel(getActivity().getResources().getString(R.string.common_dialog_loading))
                .setCancellable(false);
        hud.show();
        int cout = properyPresenter.deleteById(getActivity(), bean.getPDDH());
        if (cout > 0) {
            getmRefreshList().remove(position);
            getmRefreshAdapter().notifyDataSetChanged();
            if (ListUtils.isEmpty(getmRefreshList())) {
                EmptyView.showNoDataEmpty(mFlEmptyView);
            }
            setTabTotalcount();
            ToastUtils.show(getActivity(), "删除成功！");
        }
        hud.dismiss();
    }


    @Override
    public List localSQLFindLimit(boolean isPage, int currPagetemp) {
        int currPage = currPagetemp - 1;
        return properyPresenter.findLimit(getActivity(), currPage * JsonConstants.PAGE_SIZE, JsonConstants.PAGE_SIZE);
    }


    @Override
    public void after() {
        setTabTotalcount();
        super.after();
    }
}
