package com.moxi.agenttool.ui.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.moxi.agenttool.R;

import java.util.List;


public class HomePagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;


    public HomePagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
        setFragments(fm, list);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }


    //刷新fragment
    public void setFragments(FragmentManager fm, List<Fragment> fragments) {
        if (this.mlist != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mlist) {
                ft.remove(f);
            }
            ft.setCustomAnimations(R.animator.cube_right_in, R.animator.cube_left_out, R.animator.cube_left_in, R.animator.cube_right_out);
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mlist = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }

}
