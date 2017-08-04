package com.tanlifei.exemple.photo.galleryfinal;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tanlifei.common.ui.activity.actionbar.BaseActionBarActivity;
import com.tanlifei.common.ui.activity.photoview.BaseDeletePhotoActivity;
import com.tanlifei.common.ui.activity.photoview.PhotoChooseListApdater;
import com.tanlifei.framework.R;
import com.tlf.basic.photoview.galleryfinal.FunctionConfig;
import com.tlf.basic.photoview.galleryfinal.GalleryFinal;
import com.tlf.basic.photoview.galleryfinal.model.PhotoInfo;
import com.tlf.basic.uikit.dialog.listener.OnOperItemClickL;
import com.tlf.basic.uikit.dialog.widget.ActionSheetDialog;
import com.tlf.basic.utils.ListUtils;
import com.tlf.basic.utils.Logger;
import com.tlf.basic.utils.ToastUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@EActivity(R.layout.exemple_gallery_final_activity)
public class ExempleGalleryFinalActivity extends BaseActionBarActivity {

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;
    private int count = 8;


    @ViewById(R.id.lv_photo)
    GridView mLvPhoto;
    private List<PhotoInfo> mPhotoList;
    private PhotoChooseListApdater mChoosePhotoListAdapter;
    private FunctionConfig.Builder builder;

    @AfterViews
    void init() {
        initActionBar();
        actionBarView.setActionbarTitle("GalleryFinal 特效");
        mLvPhoto = (GridView) findViewById(R.id.lv_photo);
        mPhotoList = new ArrayList<>();
        mChoosePhotoListAdapter = new PhotoChooseListApdater(mContext, mPhotoList, count);
        mLvPhoto.setAdapter(mChoosePhotoListAdapter);
        builder = new FunctionConfig.Builder();
        builder.setEnableCamera(true)
                .setEnableEdit(false)//开启编辑功能
                .setCameraEditPhoto(true)//拍照完成后开启编辑功能
                .setEnableCrop(false)//开启裁剪功能
                .setEnableRotate(false)//开启旋转功能
                .setEnableCamera(false)//开启相机功能
                .setEnableCrop(true)
                .setEnableRotate(false)
                .setCropSquare(true)
                .setMutiSelectMaxSize(count)
                .setSelected(mPhotoList)
                .setCropReplaceSource(true)
                .setForceCrop(true);//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
        mLvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mPhotoList.size() && PhotoChooseListApdater.isDisplayAddbtn(view)) {
                    final String[] stringItems = {"相册多选", "相册单选", "拍照"};
                    final ActionSheetDialog dialog = new ActionSheetDialog(ExempleGalleryFinalActivity.this, stringItems, null);
                    dialog.isTitleShow(false).show();
                    dialog.setOnOperItemClickL(new OnOperItemClickL() {
                        @Override
                        public void onOperItemClick(Dialog dialog, AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 0:
                                    if (!ListUtils.isEmpty(mPhotoList)) {
                                        builder.setSelected(mPhotoList);
                                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, builder.build(), mOnHanlderResultCallback);
                                    } else {
                                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, count, mOnHanlderResultCallback);
                                    }
                                    break;
                                case 1:
                                    GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, builder.build(), mOnHanlderResultCallback);
                                    break;
                                case 2:
                                    GalleryFinal.openCamera(REQUEST_CODE_CAMERA, builder.build(), mOnHanlderResultCallback);
                                    break;
                                default:
                                    break;
                            }
                            dialog.dismiss();
                        }
                    });
                } else if (position != mPhotoList.size()) {// 点击放大
                    BaseDeletePhotoActivity.start(mContext, mPhotoList, position, 1000);
                }
            }
        });
    }



    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            for (int i = 0; i < resultList.size(); i++) {
                Logger.d(resultList.get(i).getPhotoPath());
            }
            if (resultList != null) {
                if (reqeustCode == REQUEST_CODE_GALLERY) {//选择
                    mPhotoList.clear();
                    mPhotoList.addAll(resultList);
                } else if (reqeustCode == REQUEST_CODE_CAMERA) {//照像
                    mPhotoList.addAll(resultList);
                }
                mChoosePhotoListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtils.show(ExempleGalleryFinalActivity.this, errorMsg);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1000) {
            mPhotoList.clear();
            mPhotoList.addAll((Collection<? extends PhotoInfo>) data.getSerializableExtra(BaseDeletePhotoActivity.INTENT_PARAMS_LIST));
            mChoosePhotoListAdapter.notifyDataSetChanged();
        }
    }
}
