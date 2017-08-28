package com.ytd.framework.equipment.ui.activity;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.NetUtils;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.tlf.basic.utils.ToastUtils;
import com.ytd.common.bean.BaseJson;
import com.ytd.common.ui.activity.actionbar.BaseScannerReceiverActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.EquipmentBean;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.framework.main.bean.EntrepotBeanList;
import com.ytd.framework.main.ui.activity.CameraScanActivity;
import com.ytd.support.http.ResultCallback;
import com.ytd.support.utils.HttpParamsUtils;
import com.ytd.support.utils.HttpRequestUtils;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import okhttp3.Call;

import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_TRUE;
import static com.ytd.support.constants.fixed.UrlConstants.UPLOADINVENTORYITEMLIST;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
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

    private KProgressHUD hud;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @AfterViews
    void init() {
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setData() {
        try {
            finshNum.setText(bean.getFinshNum());
            totalNum.setText(bean.getTotalNum());

            name.setText("盘点人:" + bean.getXM());
            data.setText("盘点日期:" + bean.getEnd_data());
            title.setText("盘点单名称:" + bean.getTitle());

            area.setText("盘点区域：" + bean.getArea());
            address.setText("资产分类：" + bean.getAddress());
            if(!StringUtils.isEmpty(bean.getRQ())){
                startDate.setText("启用日期:" + bean.getRQ().substring(0, 10));
            }

            price.setText("价格区间:" + bean.getPrice());
            qeSumNum.setText("设  备:" + bean.getTotalNum());
            startProperty.setText("资产原值:" + bean.getStart_property());
            endProperty.setText("资产净值:" + bean.getEnd_property());
            if (StringUtils.isEquals(bean.getSTATUS(), UPDATELOAD_TAG_TRUE)) {//未上传
                selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
                updateload.setText("已上传");
                opt.setVisibility(View.GONE);
            } else {
                selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
                updateload.setText("未上传");
                opt.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Click({R.id.lookeEqBtn, R.id.updateLoadBtn, R.id.lookQe})
    void click(View v) {
        switch (v.getId()) {
            case R.id.updateLoadBtn://上传
                hud = KProgressHUD.create(mContext)
                        .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                        .setDimAmount(0.5f)
                        .setCancellable(false)
                        .setLabel("正在上传....");
                simulateProgressUpdate();
                hud.show();
                break;
            case R.id.lookQe://查看
            case R.id.lookeEqBtn://查看
                StartActUtils.start(mContext, EquipmentActivity_.class, "bean", bean);
                break;
        }
    }

    private void getStoreList() {
        if (NetUtils.isConnected(mContext)) {
            HttpRequestUtils.getInstance().postTestFormBuilder(UPLOADINVENTORYITEMLIST, HttpParamsUtils.getStorelistParams()).build().execute(new ResultCallback(mContext) {
                @Override
                public void onCusResponse(BaseJson response) {
                    EntrepotBeanList jsonBean = new Gson().fromJson(new Gson().toJson(response.getData()), EntrepotBeanList.class);

                }

                @Override
                public void onError(Call call, Exception e) {

                }
            });
        } else {
            ToastUtils.show(mContext,"请连接网络！");
        }
    }

    private void  uploadInventoryItemList(){
        List<EquipmentBean> updateList = equipmentPresenter.findByUpdateTag(mContext,bean.getPDDH(),EquipmentBean.UPDATE_TAG);
        if(!ListUtils.isEmpty(updateList)){
            String str = new Gson().toJson(updateList);

        }

    }


    private void simulateProgressUpdate() {
        hud.setMaxProgress(100);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                hud.setProgress(currentProgress);
                hud.setLabel("已上传" + currentProgress + "%");
                if (currentProgress < 100) {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }

}
