package com.ytd.framework.equipment.ui.activity;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tlf.basic.uikit.kprogresshud.KProgressHUD;
import com.tlf.basic.uikit.roundview.RoundTextView;
import com.tlf.basic.utils.StartActUtils;
import com.tlf.basic.utils.StringUtils;
import com.ytd.common.ui.activity.actionbar.BaseActionBarActivity;
import com.ytd.framework.R;
import com.ytd.framework.equipment.bean.PropertyBean;
import com.ytd.support.utils.ResUtils;
import com.ytd.uikit.actionbar.ActionBarOptViewTagLevel;
import com.ytd.uikit.actionbar.OnOptClickListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.ytd.framework.equipment.bean.PropertyBean.UPDATELOAD_TAG_FALSE;

/**
 * 首页界面
 * Created by ytd on 16/1/19.
 */
@EActivity(R.layout.property_details_activity)
public class PropertyDetailsActivity extends BaseActionBarActivity {

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
                        EquipmentReactionScanActivity_.class);
            }
        });
        setData();


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setData() {
        finshNum.setText(bean.getFinshNum());
        totalNum.setText(bean.getTotalNum());

        name.setText("盘点人:" + bean.getXM());
        data.setText("盘点日期:" + bean.getEnd_data());
        title.setText("盘点单名称:" + bean.getTitle());

        area.setText("盘点区域：" + bean.getArea());
        address.setText("资产分类：" + bean.getAddress());
        startDate.setText("启用日期:" + bean.getRQ());
        price.setText("价格区间:" + bean.getPrice());
        qeSumNum.setText("设  备:" + bean.getTotalNum());
        startProperty.setText("资产原值:" + bean.getStart_property());
        endProperty.setText("资产净值:" + bean.getEnd_property());
        if (StringUtils.isEquals(bean.getUpdateload(), UPDATELOAD_TAG_FALSE)) {//未上传
            selectTag.setBackground(ResUtils.getDrawable(R.mipmap.unselect));
            updateload.setText("未上传");
            opt.setVisibility(View.VISIBLE);
        } else {
            selectTag.setBackground(ResUtils.getDrawable(R.mipmap.select));
            updateload.setText("已上传");
            opt.setVisibility(View.GONE);
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
