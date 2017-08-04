package com.tanlifei.common.ui.activity.photoview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.framework.R;
import com.tanlifei.support.utils.ImageLoadUtils;
import com.tanlifei.uikit.actionbar.ActionBarOptViewTagLevel;
import com.tanlifei.uikit.actionbar.OnBackClickListener;
import com.tanlifei.uikit.actionbar.OnOptClickListener;
import com.tlf.basic.photoview.galleryfinal.model.PhotoInfo;
import com.tlf.basic.photoview.galleryfinal.widget.zoonview.PhotoView;
import com.tlf.basic.uikit.dialog.listener.OnOperItemClickL;
import com.tlf.basic.uikit.dialog.widget.ActionSheetTools;
import com.tlf.basic.utils.StartActUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 查年多张图片
 *
 * @author tanlifei
 * @ClassName: BaseBrowsePhotoActivity
 * @Description: 用一句话描述该文件做什么
 * @date 2015年2月28日 下午3:42:37
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseDeletePhotoActivity extends BaseActionBarActivity {

    public static final String INTENT_PARAMS_LIST = "list";//浏览图片实体
    public static final String INTENT_PARAMS_INDEX = "index";//当前点击下标
    public static final String REQUEST_CODE = "requestCode";

    private ViewPager mViewPager;
    public List<PhotoInfo> list;
    public SamplePagerAdapter adapter;
    private int currtentIndex;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntentData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_photo_delete_view_viewpager);
        super.initActionBar();

        actionBarView.getActionbar_opt_left_text().setVisibility(View.VISIBLE);
        actionBarView.setActionbarOptLeftText("删除");
        //setTranslucentStatus(android.R.color.transparent);
        initWidget();
        initData();
        initListener();
    }

    private void getIntentData() {
        list = (List<PhotoInfo>) getIntent().getSerializableExtra(INTENT_PARAMS_LIST);
        currtentIndex = getIntent().getIntExtra(INTENT_PARAMS_INDEX, -1);
        requestCode = getIntent().getIntExtra(REQUEST_CODE, -1);
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currtentIndex = mViewPager.getCurrentItem();
                actionBarView.setActionbarTitle((mViewPager.getCurrentItem() + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        actionBarView.setOnOptClickListener(new OnOptClickListener() {
            @Override
            public void onClick(View v, ActionBarOptViewTagLevel viewTag) {
                new ActionSheetTools().getInstance(mContext, new String[]{"删除"}).isTitleShow(true).title("要删除这张图片吗?").show().setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(Dialog dialog, AdapterView<?> parent, View view, int position, long id) {
                        try {
                            dialog.dismiss();
                            delete(dialog);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        actionBarView.setOnBackClickListener(new OnBackClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    public void back() {
        Map<String, Object> map = new HashMap<>();
        map.put(INTENT_PARAMS_LIST, list);
        StartActUtils.setResult((Activity) mContext, map, requestCode);
        StartActUtils.finish(mContext);
    }


    private void delete(Dialog dialog) {
        if (list.size() == 1) {//最后一张
            list.remove(currtentIndex);
            back();
        } else {
            if (currtentIndex == 0) {
                list.remove(currtentIndex);
                currtentIndex = 0;
            } else {
                list.remove(currtentIndex);
                currtentIndex = currtentIndex - 1;
            }
            adapter.notifyDataSetChanged();
            changeDate();
        }

    }


    public static void start(Context context, List list, int index, int requestCode) {
        Map<String, Object> map = new HashMap<>();
        map.put(INTENT_PARAMS_LIST, list);
        map.put(INTENT_PARAMS_INDEX, index);
        map.put(REQUEST_CODE, requestCode);
        StartActUtils.forResult(context, BaseDeletePhotoActivity.class, map, requestCode);
    }


    private void initData() {
        adapter = new SamplePagerAdapter();
        mViewPager.setAdapter(adapter);
        changeDate();
    }

    private void changeDate() {
        if (currtentIndex != -1 && currtentIndex >= 0) {
            mViewPager.setCurrentItem(currtentIndex);
        }
        if (null != list && list.size() > 0) {
            actionBarView.setActionbarTitle((mViewPager.getCurrentItem() + 1) + "/" + list.size());
        }
    }

    private void initWidget() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    class SamplePagerAdapter extends PagerAdapter {
        private int mChildCount = 0;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final Holder holder = new Holder();
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.common_photo_delete_view_item, null);
            holder.photoView = (PhotoView) view.findViewById(R.id.iv_photo);
            ImageLoadUtils.getInstance().loadImageView(holder.photoView, "file://" + list.get(position).getPhotoPath(), R.mipmap.ic_gf_default_photo);
            (container).addView(view, 0);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (mChildCount > 0) {
                mChildCount--;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }

    static class Holder {
        PhotoView photoView;
    }
}
