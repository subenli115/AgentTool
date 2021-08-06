package com.moxi.agenttool.ui.main.fragment;

import android.graphics.Color;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.FragmentBaseCommonBinding;
import com.moxi.agenttool.ui.adapter.MyHouseAdapter;
import com.moxi.agenttool.ui.base.fragment.CommHttpFragment;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * @author feng wen jun
 * @description  过期房源
 * @since 2021/2/22 0022
 */
public class ExpireHouseFragment extends CommHttpFragment<FragmentBaseCommonBinding, MainViewModel> {
    private FilterHouseResult.DataDTO dataDTO;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initParam() {
        super.initParam();
    }


    @Override
    public void initData() {
        super.initData();
        baseBinding.recyclerview.setBackgroundColor(Color.parseColor("#f5f5f5"));
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new MyHouseAdapter(R.layout.item_my_house_view,mContext);
    }


    @Override
    public ViewBinding getHeader() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void initViewObservable() {
        ArrayList<House> houses = new ArrayList<>();
        houses.add(new House());
        houses.add(new House());
        houses.add(new House());
        houses.add(new House());
        updateData(houses);

    }

    @Override
    public void reload() {

    }
}