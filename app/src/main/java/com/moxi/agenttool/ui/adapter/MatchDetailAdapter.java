package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.moxi.agenttool.R;
import com.moxi.agenttool.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;


public class MatchDetailAdapter extends FragmentPagerAdapter {
    private final Context mContext;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] strings;

    public MatchDetailAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, boolean isU) {
        super(fm);
        mContext = context;
        strings = ArrayUtils.getArray(mContext, R.array.base_match_tabs);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    //刷新fragment
    public void setFragments(FragmentManager fm, List<Fragment> fragments) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }


}
