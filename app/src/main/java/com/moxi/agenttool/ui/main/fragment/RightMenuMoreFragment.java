package com.moxi.agenttool.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.chad.library.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.FragmentMenuMoreBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.fragment.BaseFragment;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.login.LoginActivity;
import com.moxi.agenttool.ui.main.activity.ClientMaintenanceActivity;
import com.moxi.agenttool.ui.main.activity.MatchingActivity;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.List;


/**
 * @ClassName: RightMenuMoreFragment
 * @Description: 侧滑菜单
 * @Author: join_lu
 * @CreateDate: 2021/7/20 18:11
 */
public class RightMenuMoreFragment extends BaseFragment<FragmentMenuMoreBinding, BaseViewModel> implements View.OnClickListener {


    private List<FilterHouseResult.DataDTO> bean;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return R.layout.fragment_menu_more;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        binding.tvLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LoginActivity.actionStart(getActivity());
            }
        });
        HomeFragment fragment = (HomeFragment) getActivity().getSupportFragmentManager().getFragments().get(0);
        bean = fragment.getDataDTOList();
//        binding.tvNick.setText(PreferenceManager.getInstance().getCurrentUserNick());
        binding.tvClient.setOnClickListener(this);
        binding.tvCenter.setOnClickListener(this);
        LiveDataBus.get().with(AppConstans.BusTag.ADDBEAN).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                bean = (List<FilterHouseResult.DataDTO>) o;


            }
        });
    }

    @Override
    public void reload() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_client:
                ClientMaintenanceActivity.startAction(getContext());
                break;
            case R.id.tv_center:
                    MatchingActivity.actionStart(getContext(), bean);
                break;
        }
    }
}