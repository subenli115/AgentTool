package com.moxi.agenttool.utils;

import android.app.Activity;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.moxi.agenttool.contract.GlideEngine;

import java.util.List;

/**
 * @author feng wen jun
 * @description 从本地选择图片以及拍照工具类
 * @since 2021/2/22 0022
 */
public class PhotoUtils {


    /**
     * PhotoUtils对象
     **/
    private OnPhotoResultListener onPhotoResultListener;


    public PhotoUtils(OnPhotoResultListener onPhotoResultListener) {
        this.onPhotoResultListener = onPhotoResultListener;
    }



    /**
     * 拍照
     *
     * @param
     * @return
     */
    public void takePicture(Activity activity, final boolean isEnable) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .isEnableCrop(isEnable)
                .maxSelectNum(1)
                .selectionMode(PictureConfig.SINGLE)
                .withAspectRatio(1, 1)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if(isEnable){
                            onPhotoResultListener.onPhotoResult(result.get(0).getCutPath());
                        }else {
                            onPhotoResultListener.onPhotoResult(result.get(0).getRealPath());
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


    /**
     * 从相册中选择
     *
     * @param
     * @return
     */
    public void selectPicture(Activity activity, final boolean isEnable) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .isEnableCrop(isEnable)
                .withAspectRatio(1, 1)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if(isEnable){
                            onPhotoResultListener.onPhotoResult(result.get(0).getCutPath());
                        }else {
                            onPhotoResultListener.onPhotoResult(result.get(0).getRealPath());
                        }

                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


    /**
     * [回调监听类]
     **/
    public interface OnPhotoResultListener {
        void onPhotoResult(String uri);
    }
}
