package com.tanlifei.exemple.photo;

import android.os.Bundle;
import android.view.View;

import com.tanlifei.common.bean.BasePhotoBean;
import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.common.ui.activity.photoview.BaseBrowsePhotoActivity;
import com.tanlifei.exemple.photo.galleryfinal.ExempleGalleryFinalActivity_;
import com.tanlifei.framework.R;
import com.tlf.basic.utils.StartActUtils;

import java.util.ArrayList;
import java.util.List;

public class ExemplePhotoMainActivity extends BaseActionBarActivity {

    List<BasePhotoBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemple_activity_photo_main);
        initActionBar();
        actionBarView.setActionbarTitle(" Photo 示例");
        list = new ArrayList<>();
        list.add(new BasePhotoBean("http://c.hiphotos.baidu.com/image/pic/item/10dfa9ec8a136327408ff2f2958fa0ec09fac794.jpg", "http://c.hiphotos.baidu.com/image/pic/item/10dfa9ec8a136327408ff2f2958fa0ec09fac794.jpg"));
        list.add(new BasePhotoBean("http://e.hiphotos.baidu.com/image/pic/item/96dda144ad345982b658b7e90ff431adcaef84fe.jpg", "http://e.hiphotos.baidu.com/image/pic/item/96dda144ad345982b658b7e90ff431adcaef84fe.jpg"));
        list.add(new BasePhotoBean("http://h.hiphotos.baidu.com/image/pic/item/574e9258d109b3dedb168a8ec8bf6c81810a4cae.jpg", "http://h.hiphotos.baidu.com/image/pic/item/574e9258d109b3dedb168a8ec8bf6c81810a4cae.jpg"));
        list.add(new BasePhotoBean("http://e.hiphotos.baidu.com/image/pic/item/e61190ef76c6a7ef726b5320fffaaf51f3de6663.jpg", "http://e.hiphotos.baidu.com/image/pic/item/e61190ef76c6a7ef726b5320fffaaf51f3de6663.jpg"));
        list.add(new BasePhotoBean("http://img5.duitang.com/uploads/item/201407/30/20140730152939_zPFKy.thumb.700_0.jpeg", "http://a.hiphotos.baidu.com/image/pic/item/bf096b63f6246b60fc49d17ce9f81a4c510fa277.jpg"));
        list.add(new BasePhotoBean("http://a.hiphotos.baidu.com/image/pic/item/8435e5dde71190ef1ef587d5cc1b9d16fdfa60a9.jpg", "http://a.hiphotos.baidu.com/image/pic/item/8435e5dde71190ef1ef587d5cc1b9d16fdfa60a9.jpg"));
        list.add(new BasePhotoBean("http://a.hiphotos.baidu.com/image/pic/item/8435e5dde71190ef1ef587d5cc1b9d16fdfa60a9.jpg", "http://g.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd9c38927a23c01213fb80e9120.jpg"));
        list.add(new BasePhotoBean("http://f.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe09def7781469600c338744ad01.jpg", "http://f.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe09def7781469600c338744ad01.jpg"));
        list.add(new BasePhotoBean("http://g.hiphotos.baidu.com/image/pic/item/f31fbe096b63f624cd2991e98344ebf81b4ca3e0.jpg", "http://c.hiphotos.baidu.com/image/pic/item/10dfa9ec8a136327408ff2f2958fa0ec09fac794.jpg"));
        list.add(new BasePhotoBean("http://f.hiphotos.baidu.com/image/pic/item/42a98226cffc1e17af3e61e44e90f603728de966.jpg", "http://e.hiphotos.baidu.com/image/pic/item/86d6277f9e2f070814a5c7d5ed24b899a801f247.jpg"));
        list.add(new BasePhotoBean("http://g.hiphotos.baidu.com/image/pic/item/91ef76c6a7efce1be3e3f0c4ad51f3deb58f65f7.jpg", "http://a.hiphotos.baidu.com/image/pic/item/7a899e510fb30f249a52d40dca95d143ac4b03f7.jpg"));
        list.add(new BasePhotoBean("http://a.hiphotos.baidu.com/image/pic/item/b7003af33a87e9504d4a226d12385343fbf2b456.jpg", "http://c.hiphotos.baidu.com/image/pic/item/63d0f703918fa0ec421ab20c249759ee3c6ddbd6.jpg"));
        list.add(new BasePhotoBean("http://img5.duitang.com/uploads/item/201407/30/20140730152939_zPFKy.thumb.700_0.jpeg", "http://images.99pet.com/InfoImages/wm600_450/ef48d0d8e8f64172a28b9451fc5a941d.jpg"));
        list.add(new BasePhotoBean("http://img2.3lian.com/2014/f5/158/d/86.jpg", "http://img2.3lian.com/2014/f5/158/d/87.jpg"));
        list.add(new BasePhotoBean("http://images.99pet.com/InfoImages/wm600_450/1d770941f8d44c6e85ba4c0eb736ef69.jpg", "http://ww2.sinaimg.cn/mw600/c6c1d258jw1e5r59ttpkcj20gu0gsmyh.jpg"));
        list.add(new BasePhotoBean("http://images.99pet.com/InfoImages/wm600_450/1d770941f8d44c6e85ba4c0eb736ef69.jpg", "http://ww2.sinaimg.cn/mw600/c6c1d258jw1e5r59ttpkcj20gu0gsmyh.jpg"));
        list.add(new BasePhotoBean("http://a.hiphotos.baidu.com/image/pic/item/7e3e6709c93d70cfbbdea204fadcd100baa12b96.jpg", "http://a.hiphotos.baidu.com/image/pic/item/7e3e6709c93d70cfbbdea204fadcd100baa12b96.jpg"));
        list.add(new BasePhotoBean("http://f.hiphotos.baidu.com/image/pic/item/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg", "http://f.hiphotos.baidu.com/image/pic/item/8644ebf81a4c510fa42d1bf66459252dd52aa575.jpg"));
        list.add(new BasePhotoBean("http://d.hiphotos.baidu.com/image/pic/item/caef76094b36acaffd0002e37ed98d1000e99cc9.jpg", "http://d.hiphotos.baidu.com/image/pic/item/caef76094b36acaffd0002e37ed98d1000e99cc9.jpg"));

    }



    public void B(View v) {
        BaseBrowsePhotoActivity.start(mContext, list, 0);
    }

    public void C(View v) {
        StartActUtils.start(mContext, ExempleGalleryFinalActivity_.class);
    }


}
