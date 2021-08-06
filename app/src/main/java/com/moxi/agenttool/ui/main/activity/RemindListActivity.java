package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.chad.library.BR;
import com.google.android.material.tabs.TabLayout;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityRemindListBinding;
import com.moxi.agenttool.ui.adapter.RemindTabPagerAdapter;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.main.fragment.ClientRemindFragment;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * @ClassName: RemindListActivity
 * @Description: 提醒activity
 * @Author: join_lu
 * @CreateDate: 2021/8/6 17:39
 */
public class RemindListActivity extends BaseActivity<ActivityRemindListBinding, MainViewModel> {


    private ArrayList<Fragment> mFragments;
    private RemindTabPagerAdapter adapter;


    public static void startAction(Context context) {
        Intent starter = new Intent(context, RemindListActivity.class);
        context.startActivity(starter);
    }



    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_remind_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        mFragments = new ArrayList<>();
        mFragments.add(new ClientRemindFragment());
        mFragments.add(new ClientRemindFragment());
        adapter = new RemindTabPagerAdapter(getSupportFragmentManager(), mContext, mFragments,false);
        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = binding.tl.getTabAt(i);
            tab.setCustomView(R.layout.tab_title);
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                TextView tv = tab.getCustomView().findViewById(android.R.id.text1);
                tv.setTextColor(ContextCompat.getColor(mContext, R.color.orange_7730));
            }
        }
        binding.tl.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 获取 tab 组件
                View view = tab.getCustomView().findViewById(android.R.id.text1);
                if (null != view) {
                    // 改变 tab 选择状态下的字体大小
                    // 改变 tab 选择状态下的字体颜色
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.orange_7730));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView().findViewById(android.R.id.text1);
                if (null != view) {
                    // 改变 tab 未选择状态下的字体大小
                    // 改变 tab 未选择状态下的字体颜色
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.textColor_333));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
