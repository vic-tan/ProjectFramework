package com.ytd.framework.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tlf.basic.base.adapter.pagerview.RecyclingPagerAdapter;
import com.tlf.basic.utils.InflaterUtils;
import com.ytd.framework.R;
import com.ytd.framework.main.bean.BannerBaen;
import com.ytd.support.utils.ImageLoadUtils;

import java.util.List;

/**
 * Desction:
 * Author:pengjianbo
 * Date:16/3/16 上午11:10
 */
public class BannerAdapter extends RecyclingPagerAdapter {
    private Context mContext;
    private List<BannerBaen> mImageList;

    public BannerAdapter(Context context, List<BannerBaen> mImageList) {
        this.mContext = context;
        this.mImageList = mImageList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = InflaterUtils.inflate(mContext, R.layout.common_banner_item);
            holder.imageView = (ImageView) convertView.findViewById(R.id.banner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int pos = getPosition(position);
        ImageLoadUtils.getInstance().loadImageView(holder.imageView,"drawable://" +mImageList.get(pos).getImage(), R.mipmap.ic_gf_default_photo);
        holder.imageView.setFocusable(false);
        holder.imageView.setFocusableInTouchMode(false);
        return convertView;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public int getPosition(int position) {
        return position % mImageList.size();
    }

    public static class ViewHolder {
        private ImageView imageView;
    }
}
