package com.moxi.agenttool.ui.network.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.FragmentDetailBinding;
import com.moxi.agenttool.entity.DemoEntity;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by goldze on 2017/7/17.
 * 详情界面
 */

public class DetailFragment extends BaseFragment<FragmentDetailBinding, DetailViewModel> {

    private DemoEntity.ItemsEntity entity;

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setDemoEntity(entity);
    }
}
