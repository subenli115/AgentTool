package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityDemoBinding;
import com.moxi.agenttool.ui.adapter.HomePagerAdapter;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.main.fragment.HomeFragment;
import com.moxi.agenttool.ui.main.fragment.RightMenuMoreFragment;
import com.moxi.agenttool.wdiget.CubePageTransformer;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.ArrayList;

/**
 * Created by goldze on 2017/7/17.
 */

public class HomeActivity extends BaseActivity<ActivityDemoBinding, BaseViewModel> implements ViewPager.OnPageChangeListener{
    private ArrayList<Fragment> list;
    private HomePagerAdapter adapter;


    public static void startAction(Context context) {
        Intent starter = new Intent(context, MatchDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initParam() {
        super.initParam();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
    }



    @Override
    public void initData() {
        super.initData();
        // 实例化手势
        //绑定点击事件
        binding.vp.setOnPageChangeListener(this) ;
        list = new ArrayList<>();
        list.add(new HomeFragment() );
        list.add(new RightMenuMoreFragment() );
        adapter = new HomePagerAdapter(getSupportFragmentManager(), list);
        binding.vp.setPageTransformer(false, new CubePageTransformer(15f));//也可自定义动画范围大小new CubePageTransformer(90f)
        binding.vp.setAdapter(adapter);
        binding.vp.setCurrentItem(0);  //初始化显示第一个页面
        binding.tvTest.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
            }
        });
        //立方体效果

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }
}
