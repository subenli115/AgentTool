package com.moxi.agenttool.libraryfragmenttransactionextended;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.moxi.agenttool.R;


public class FragmentTransactionExtended implements FragmentManager.OnBackStackChangedListener, android.app.FragmentManager.OnBackStackChangedListener {
    private boolean mDidSlideOut = false;
    private boolean mIsAnimating = false;
    private FragmentTransaction mFragmentTransaction;
    private Context mContext;
    private Fragment mFirstFragment, mSecondFragment;
    private int mContainerID;
    private int mTransitionType;
    public static final int SCALEX = 0;
    public static final int SCALEY = 1;
    public static final int SCALEXY = 2;
    public static final int FADE = 3;
    public static final int FLIP_HORIZONTAL = 4;
    public static final int FLIP_VERTICAL = 5;
    public static final int SLIDE_VERTICAL = 6;
    public static final int SLIDE_HORIZONTAL = 7;
    public static final int SLIDE_HORIZONTAL_PUSH_TOP = 8;
    public static final int SLIDE_VERTICAL_PUSH_LEFT = 9;
    public static final int GLIDE = 10;
    public static final int SLIDING = 11;
    public static final int STACK = 12;
    public static final int CUBE = 13;
    public static final int ROTATE_DOWN = 14;
    public static final int ROTATE_UP = 15;
    public static final int ACCORDION = 16;
    public static final int TABLE_HORIZONTAL = 17;
    public static final int TABLE_VERTICAL = 18;
    public static final int ZOOM_FROM_LEFT_CORNER = 19;
    public static final int ZOOM_FROM_RIGHT_CORNER = 20;



    public FragmentTransactionExtended(Context context, FragmentTransaction fragmentTransaction, Fragment firstFragment, Fragment secondFragment, int containerID) {
        this.mFragmentTransaction = fragmentTransaction;
        this.mContext = context;
        this.mFirstFragment = firstFragment;
        this.mSecondFragment = secondFragment;
        this.mContainerID = containerID;
    }

    public void addTransition(int transitionType) {
        this.mTransitionType = transitionType;
        switch (transitionType) {
            case FADE:
                transitionFade();
                break;
            case CUBE:
                transitionCube();
                break;
        }
        mFragmentTransaction.replace(mContainerID, mSecondFragment);
    }

    private void transitionFade() {
        mFragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
    }


    private void transitionCube() {
        mFragmentTransaction.setCustomAnimations(R.animator.cube_right_in, R.animator.cube_left_out, R.animator.cube_left_in, R.animator.cube_right_out);
    }





    public void slideBack(Animator.AnimatorListener listener) {
        View movingFragmentView = mFirstFragment.getView();
        movingFragmentView.setPivotY(movingFragmentView.getHeight()/2);
        movingFragmentView.setPivotX(movingFragmentView.getWidth() / 2);

        PropertyValuesHolder rotateX = PropertyValuesHolder.ofFloat("rotationX", 40f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.8f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.8f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.5f);
        ObjectAnimator movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY, alpha);

        ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
        movingFragmentRotator.setStartDelay(mContext.getResources().getInteger(R.integer.half_slide_up_down_duration));

        AnimatorSet s = new AnimatorSet();
        s.playTogether(movingFragmentAnimator, movingFragmentRotator);
        s.addListener(listener);
        s.start();
    }

    public void slideForward() {
        View movingFragmentView = mFirstFragment.getView();
        PropertyValuesHolder rotateX = PropertyValuesHolder.ofFloat("rotationX", 40f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f);
        ObjectAnimator movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY, alpha);

        ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
        movingFragmentRotator.setStartDelay(mContext.getResources().getInteger(R.integer.half_slide_up_down_duration));

        AnimatorSet s = new AnimatorSet();
        s.playTogether(movingFragmentAnimator, movingFragmentRotator);
        s.setStartDelay(mContext.getResources().getInteger(R.integer.slide_up_down_duration));
        s.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating = false;
                mDidSlideOut = true;
            }
        });
        s.start();
        ((Activity) this.mContext).getFragmentManager().removeOnBackStackChangedListener(this);
    }


    public void commit(){
        switch (mTransitionType){
            case SLIDING:
                break;
            default:
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        switch (mTransitionType){
            case SLIDING:
                if (!mDidSlideOut) {
                        slideForward();
                }else{
                    mDidSlideOut= false;
                }
                break;
        }
    }
}
