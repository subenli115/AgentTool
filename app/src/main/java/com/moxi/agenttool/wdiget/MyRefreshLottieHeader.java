package com.moxi.agenttool.wdiget;

/**
 * @ClassName: MyRefreshLottieHeader
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/22 11:13
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.moxi.agenttool.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Lottie动画
 *
 * @author wapchief
 * @date 2018 /1/30
 */
public class MyRefreshLottieHeader extends LinearLayout implements RefreshHeader {

    /**
     * The M animation view.
     */
    LottieAnimationView mAnimationView;

    /**
     * Instantiates a new My refresh lottie header.
     *
     * @param context the context
     */
    public MyRefreshLottieHeader(Context context) {
        super(context);
        initView(context);
    }



    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    /**
     * 注意不能为null
     * @return
     */
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }


    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        mAnimationView.playAnimation();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        mAnimationView.cancelAnimation();
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.loading_lottie, this);
        mAnimationView = (LottieAnimationView) view.findViewById(R.id.loading_lottie);
        ImageView  iv = (ImageView) view.findViewById(R.id.iv_gif);
        Glide.with(this).load(R.drawable.loading).into(iv);
    }

    /**
     * Set animation view json.
     *
     * @param animName json文件名
     */
    public void setAnimationViewJson(String animName){
        mAnimationView.setAnimation(animName);
    }

    /**
     * Set animation view json.
     *
     * @param anim the anim
     */
    public void setAnimationViewJson(Animation anim){
        mAnimationView.setAnimation(anim);
    }

}