package com.tanlifei.common.ui.activity.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tanlifei.common.bean.BasePhotoBean;
import com.tanlifei.common.ui.activity.BaseActivity;
import com.tanlifei.framework.R;
import com.tanlifei.support.utils.ImageLoadUtils;
import com.tlf.basic.photoview.galleryfinal.widget.zoonview.PhotoView;
import com.tlf.basic.photoview.galleryfinal.widget.zoonview.PhotoViewAttacher;
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
public class BaseBrowsePhotoActivity extends BaseActivity {

    public static final String INTENT_PARAMS_LIST = "list";//浏览图片实体
    public static final String INTENT_PARAMS_INDEX = "index";//当前点击下标
    public static final String INTENT_PARAMS_IS_LOCAL = "is_local";//是否查看本的图片

    private ViewPager mViewPager;
    public List list;
    private int currtentIndex;
    private TextView index;
    private boolean isLocal = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntentData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_photo_view_viewpager);
        initWidget();
        initData();
        initListener();
    }

    private void getIntentData() {
        list = (List) getIntent().getSerializableExtra(INTENT_PARAMS_LIST);
        currtentIndex = getIntent().getIntExtra(INTENT_PARAMS_INDEX, -1);
        isLocal = getIntent().getBooleanExtra(INTENT_PARAMS_IS_LOCAL, false);
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                index.setText((mViewPager.getCurrentItem() + 1) + "/" + list.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public static void start(Context context, List list, int index, boolean isLocal) {
        startData(context, list, index, isLocal);
    }

    public static void start(Context context, List list, int index) {
        startData(context, list, index, false);
    }

    private static void startData(Context context, List list, int index, boolean isLocal) {
        Map<String, Object> map = new HashMap<>();
        map.put(INTENT_PARAMS_LIST, list);
        map.put(INTENT_PARAMS_INDEX, index);
        map.put(INTENT_PARAMS_IS_LOCAL, isLocal);
        StartActUtils.start(context, BaseBrowsePhotoActivity.class, map);
    }


    private void initData() {
        mViewPager.setAdapter(new SamplePagerAdapter());
        if (currtentIndex != -1 && currtentIndex >= 0) {
            mViewPager.setCurrentItem(currtentIndex);
        }
        if (null != list && list.size() > 0) {
            index.setText((mViewPager.getCurrentItem() + 1) + "/" + list.size());
        }
    }

    private void initWidget() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        index = (TextView) findViewById(R.id.index);
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final Holder holder = new Holder();
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.common_photo_view_item, null);
            holder.photoView = (PhotoView) view.findViewById(R.id.iv_photo);
            holder.load = (ProgressBar) view.findViewById(R.id.prg_load);
            ImageLoadUtils.getInstance().loadImageView(holder.photoView, getImgUrl(position), R.mipmap.ic_gf_default_photo, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.load.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.load.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.load.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    holder.load.setVisibility(View.GONE);
                }
            });
            (container).addView(view, 0);
            holder.photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    StartActUtils.finish(mContext);
                }
            });

            return view;
        }

        public String getImgUrl(int position) {
            return isLocal ? "file://" + ((BasePhotoBean) list.get(position)).getUrl() : ((BasePhotoBean) list.get(position)).getUrl();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    static class Holder {
        PhotoView photoView;
        ProgressBar load;
    }

    @Override
    protected void setSystemBarTint(int statusBarTintResource) {

    }
}
