package com.ytd.framework.equipment.ui.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tlf.basic.uikit.dialog.listener.OnBtnClickL;
import com.tlf.basic.uikit.dialog.widget.NormalDialog;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.bean.params.BaseEventbusParams;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.main.ui.BaseApplication;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.support.http.DialogCallback;
import com.ytd.support.http.MultipleCallback2;
import com.ytd.support.utils.GsonJsonUtils;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.ytd.common.bean.params.BaseEventbusParams.RE_SCAN_START;
import static com.ytd.common.bean.params.BaseEventbusParams.UPDATE_START;
import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;
import static com.ytd.support.constants.fixed.UrlConstants.PDABIND;
import static com.ytd.support.constants.fixed.UrlConstants.UPLOADINVENTORYITEMLIST;
import static com.ytd.support.utils.HttpParamsUtils.PAGESIZEINT;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
@EActivity(R.layout.property_details_activity)
public class PropertyDetailsActivity extends BaseScannerReceiverActivity {

    public static final String TAG = PropertyDetailsActivity.class.getSimpleName();
    @ViewById
    LinearLayout opt;
    @ViewById
    RoundTextView updateLoadBtn;
    @ViewById
    RoundTextView lookeEqBtn;
    @ViewById
    TextView finshNum;
    @ViewById
    TextView dh;
    @ViewById
    TextView totalNum;
    @ViewById
    TextView name;
    @ViewById
    TextView data;
    @ViewById
    TextView title;
    @ViewById
    TextView area;
    @ViewById
    TextView address;
    @ViewById
    TextView startDate;
    @ViewById
    TextView price;
    @ViewById
    TextView qeSumNum;
    @ViewById
    TextView startProperty;
    @ViewById
    TextView endProperty;
    @ViewById
    ImageView selectTag;
    @ViewById
    TextView updateload;

    private PropertyBean bean;

    private KProgressHUD supperHud;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    void init() {
//        supperHud = KProgressHUD.create(mContext)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setDimAmount(0.5f)
//                .setLabel("正在上传盘点单....")
//                .setCancellable(false);
        supperHud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                .setDimAmount(0.5f)
                .setCancellable(false)
                .setLabel("正在上传盘点单....");
        beDefeated = new ArrayList<>();
        EventBus.getDefault().register(this);
        initActionBar();
        bean = getIntent().getParcelableExtra("bean");
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                StartActUtils.start(mContext,
                        CameraScanActivity.class);
            }
        });
        setData();


    }

    @Subscribe
    public void onEventMainThread(BaseEventbusParams event) {
        if (event.getTag() == RE_SCAN_START) {
            PropertyBean bean1 = properyPresenter.findById(mContext, bean.getPDDH());
            bean = bean1;
            setData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setData() {
        try {
            totalNum.setText(bean.getTotalNum());
            dh.setText("盘点单号:" + bean.getPDDH());
            name.setText("盘点人:" + bean.getXM());
            data.setText("盘点生成日期:" + bean.getRQ());
            title.setText("备注:" + bean.getBZ());

//            area.setText("盘点区域：" + bean.getArea());
//            address.setText("资产分类：" + bean.getAddress());
//            if (!StringUtils.isEmpty(bean.getRQ())) {
//                startDate.setText("启用日期:" + bean.getRQ());
//            }

//            price.setText("盘点单号:" + bean.getPrice());
            qeSumNum.setText("设备数量:" + bean.getTotalNum());
//            startProperty.setText("资产原值:" + bean.getStart_property());
//            endProperty.setText("资产净值:" + bean.getEnd_property());
            changeDate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeDate() {
        finshNum.setText(bean.getFinshNum());
        if (StringUtils.isEquals(bean.getSTATUS(), UPDATELOAD_TAG_TRUE)) {//未上传
            selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
            updateload.setText("已上传");
            opt.setVisibility(View.GONE);
        } else {
            selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
            updateload.setText("未上传");
            opt.setVisibility(View.VISIBLE);
        }
    }

    int currentProgress = 0;

    @Click({R.id.lookeEqBtn, R.id.updateLoadBtn, R.id.lookQe})
    void click(View v) {
        switch (v.getId()) {
            case R.id.updateLoadBtn://上传
                final List<EquipmentBean> updateList = equipmentPresenter.findByUpdateTag(mContext, bean.getPDDH(), EquipmentBean.UPDATE_TAG);
                if (ListUtils.isEmpty(updateList)) {
                    bindShow("\n" + "您没有盘点过设备！请先去盘点再上传" + "\n");
                    return;
                }
                if (Integer.parseInt(bean.getTotalNum()) > updateList.size()) {
                    int cuont = Integer.parseInt(bean.getTotalNum()) - updateList.size();
                    bindShow("\n" + "您还有" + cuont + "个设备未盘点，请先去盘点" + "\n");
                    return;
                }

                if (bindPDA()) {//绑定
                    beDefeated.clear();
                    pageIndex = 1;
                    supperHud.setMaxProgress(Integer.parseInt(bean.getTotalNum()));
                    currentProgress = 0;
                    supperHud.show();
                    findData(supperHud);
                }
                break;
            case R.id.lookQe://查看
                StartActUtils.start(mContext, EquipmentActivity_.class, "bean", bean);
                break;
            case R.id.lookeEqBtn://查看
                if (bindPDA()) {//绑定
                    StartActUtils.start(mContext, EquipmentActivity_.class, "bean", bean);
                }
                break;
        }
    }

    public boolean bindPDA() {
        if (null != bean && !bean.isPDABind()) {
            bindPDAShow("\n" + "您还未绑定还该盘点单，请先连网绑定再开始盘点！" + "\n");
            return false;
        }
        return true;
    }

    //扫描下一个
    public void bindShow(String title) {
        NormalDialog dialogBind = new NormalDialog(mContext);
        dialogBind.content(title)//
                .btnNum(1)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("确定")//
                .show();
        dialogBind.setCancelable(false);
        dialogBind.setCanceledOnTouchOutside(false);
        dialogBind.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        dialog.dismiss();
                        StartActUtils.start(mContext, EquipmentActivity_.class, "bean", bean);
//                        StartActUtils.finish(mContext);
                    }
                }
        );
    }

    //
    public void bindPDAShow(String title) {
        NormalDialog dialogBind = new NormalDialog(mContext);
        dialogBind.content(title)//
                .btnNum(2)
                .isTitleShow(false).contentGravity(Gravity.CENTER_HORIZONTAL)
                .btnText("取消", "确定")//
                .show();
        dialogBind.setCancelable(false);
        dialogBind.setCanceledOnTouchOutside(false);
        dialogBind.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick(View v, Dialog dialog) {//next
                        dialog.dismiss();
                        padBind();
                    }
                }
        );
    }

    private void padBind() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(PDABIND, getPDABindParams()).build().execute(new DialogCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    String jsonBean = (String) response.getData();
                    if (!StringUtils.isEmpty(jsonBean) && StringUtils.isEquals("1", jsonBean)) {
                        bean.setPDABind(true);
                        properyPresenter.update(mContext, bean);
                        ToastUtils.show(mContext, "绑定成功,请点击保存继续盘点！");
                    } else {
                        ToastUtils.show(mContext, "绑定失败,请重试！");
                    }
                }
            });
        } else {
            ToastUtils.show(mContext, "绑定必须要连网，请连接网络再试！");
        }
    }


    private Map<String, String> getPDABindParams() {
        return HttpParamsUtils.getPDABindParams(bean.getPDDH(), BaseApplication.userBean.getEquId(), BaseApplication.userBean.getLoginName());
    }


    public int totalPage(int total) {
        int pa = (int) Math.ceil(total / (PAGESIZEINT * 1.0));
        return pa;

    }

    public List localSQLFindLimit(int currPagetemp) {
        int currPage = currPagetemp - 1;
        return equipmentPresenter.updateFindLimit(this, bean.getPDDH(), currPage * PAGESIZEINT, PAGESIZEINT);
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getUploadInventoryItemList((List<EquipmentBean>) msg.obj);
                    break;
                case 2:
                    getUploadInventoryItemList2((List<EquipmentBean>) msg.obj);
                    break;
                case 3:
                    supperHud.dismiss();
                    Logger.i("---------3333--------");
                    ToastUtils.show(mContext, "上传失败");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void findData(final KProgressHUD runHud) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<EquipmentBean> updateList = localSQLFindLimit(pageIndex);
                        if (pageIndex == 1) {
                            myHandler.obtainMessage(1, updateList).sendToTarget();
                        } else {
                            myHandler.obtainMessage(2, updateList).sendToTarget();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        myHandler.sendEmptyMessage(2);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            myHandler.sendEmptyMessage(3);
        }

    }


    List<EquipmentBean> beDefeated;
    int pageIndex = 1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void getUploadInventoryItemList(final List<EquipmentBean> updateList) {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(UPLOADINVENTORYITEMLIST, uploadInventoryItemList(updateList)).build().execute(new MultipleCallback2(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
//                    List<EquipmentBean> jsonBean = (List<EquipmentBean>) response.getData();
                    try {
                        List<EquipmentBean> jsonBean = GsonJsonUtils.fromJsonArray(new Gson().toJson(response.getData()), EquipmentBean.class);
                        currentProgress = currentProgress + updateList.size();
                        supperHud.setProgress(currentProgress);
                        pageIndex = pageIndex + 1;
                        if (totalPage(Integer.parseInt(bean.getTotalNum())) >= pageIndex) {
                            findData(supperHud);
                            if (ListUtils.isEmpty(jsonBean)) {
                                properyPresenter.updateFinish(mContext, bean.getPDDH(), updateList);
                            } else {
                                beDefeated.addAll(jsonBean);
                            }
                        } else {
                            if (ListUtils.isEmpty(jsonBean)) {
                                ToastUtils.show(mContext, "上传成功");
                                properyPresenter.updateFinish(mContext, bean.getPDDH(), updateList);
                                bean.setSTATUS(UPDATELOAD_TAG_TRUE);
                                changeDate();
                                EventBus.getDefault().post(
                                        new BaseEventbusParams(UPDATE_START, "update"));
                            } else {
                                try {
                                    equipmentPresenter.updateFinsh(mContext, updateList, jsonBean);
                                    if (updateList.size() > jsonBean.size()) {
                                        bean.setFinshNum((Integer.parseInt(bean.getFinshNum()) + (updateList.size() - jsonBean.size())) + "");
                                        properyPresenter.updateFinishNum(mContext, bean.getPDDH(), (updateList.size() - jsonBean.size()) + "");
                                    }
                                    changeDate();
                                    EventBus.getDefault().post(
                                            new BaseEventbusParams(UPDATE_START, "update"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                ToastUtils.show(mContext, "上传失败");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        supperHud.dismiss();
                    }
                }

                @Override
                public void onError(Call call, Exception e) {
                    supperHud.dismiss();
                }
            });
        } else {
            ToastUtils.show(mContext, "请连接网络！");
        }
    }


    public void getUploadInventoryItemList2(final List<EquipmentBean> updateList) {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postFormBuilder(UPLOADINVENTORYITEMLIST, uploadInventoryItemList(updateList)).build().execute(new MultipleCallback2(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
//                    List<EquipmentBean> jsonBean = (List<EquipmentBean>) response.getData();
                    try {
                        List<EquipmentBean> jsonBean = GsonJsonUtils.fromJsonArray(new Gson().toJson(response.getData()), EquipmentBean.class);
                        pageIndex = pageIndex + 1;
                        currentProgress = currentProgress + updateList.size();
                        supperHud.setProgress(currentProgress);
                        if (totalPage(Integer.parseInt(bean.getTotalNum())) >= pageIndex) {
                            findData(supperHud);
                            if (ListUtils.isEmpty(jsonBean)) {
                                properyPresenter.updateFinish(mContext, bean.getPDDH(), updateList);
                            } else {
                                beDefeated.addAll(jsonBean);
                            }
                        } else {
                            if (ListUtils.isEmpty(jsonBean)) {
                                supperHud.dismiss();
                                ToastUtils.show(mContext, "上传成功");
                                properyPresenter.updateFinish(mContext, bean.getPDDH(), updateList);
                                bean.setSTATUS(UPDATELOAD_TAG_TRUE);
                                changeDate();
                                EventBus.getDefault().post(
                                        new BaseEventbusParams(UPDATE_START, "update"));
                            } else {
                                try {
                                    equipmentPresenter.updateFinsh(mContext, updateList, jsonBean);
                                    if (updateList.size() > jsonBean.size()) {
                                        bean.setFinshNum((Integer.parseInt(bean.getFinshNum()) + (updateList.size() - jsonBean.size())) + "");
                                        properyPresenter.updateFinishNum(mContext, bean.getPDDH(), (updateList.size() - jsonBean.size()) + "");
                                    }
                                    changeDate();
                                    EventBus.getDefault().post(
                                            new BaseEventbusParams(UPDATE_START, "update"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                ToastUtils.show(mContext, "上传失败");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        supperHud.dismiss();
                    }

                }

                @Override
                public void onError(Call call, Exception e) {
                    supperHud.dismiss();
                }
            });
        } else {
            ToastUtils.show(mContext, "请连接网络！");
        }
    }


    private Map<String, String> uploadInventoryItemList(List<EquipmentBean> updateList) {
        String str = new Gson().toJson(updateList);
        return HttpParamsUtils.uploadInventoryItemList(bean.getPDDH(), BaseApplication.userBean.getEquId(), BaseApplication.userBean.getLoginName(), BaseApplication.userBean.getStoreId(), str);
    }


    private void simulateProgressUpdate() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                supperHud.setProgress(currentProgress);
            }
        }, 100);
    }

}
