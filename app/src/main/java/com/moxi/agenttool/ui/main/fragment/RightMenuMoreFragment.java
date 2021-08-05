package com.moxi.agenttool.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
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
import com.moxi.agenttool.ui.main.activity.UserInfoActivity;
import com.moxi.agenttool.utils.PreferenceManager;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.List;

import me.goldze.mvvmhabit.utils.StringUtils;


/**
 * @ClassName: RightMenuMoreFragment
 * @Description: 侧滑菜单
 * @Author: join_lu
 * @CreateDate: 2021/7/20 18:11
 */
public class RightMenuMoreFragment extends BaseFragment<FragmentMenuMoreBinding, BaseViewModel> implements View.OnClickListener {


    private List<FilterHouseResult.DataDTO> bean;
    private boolean currentUserIsLogin;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        LiveDataBus.get().with(AppConstans.BusTag.QUIT,String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                initView();
            }
        });
        initView();
        binding.tvClient.setOnClickListener(this);
        binding.tvCenter.setOnClickListener(this);
        LiveDataBus.get().with(AppConstans.BusTag.ADDBEAN).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                bean = (List<FilterHouseResult.DataDTO>) o;


            }
        });
    }

    private void initView() {
        if (!StringUtils.isEmpty(PreferenceManager.getInstance().getCurrentUserNick())) {
            binding.ivHead.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    UserInfoActivity.startAction(getActivity());
                }
            });
            binding.tvLogin.setVisibility(View.INVISIBLE);
            binding.tvNick.setText(PreferenceManager.getInstance().getCurrentUserNick());
            if (!StringUtils.isEmpty(PreferenceManager.getInstance().getCurrentUserAvatar())) {
                Glide.with(this).load(PreferenceManager.getInstance().getCurrentUserAvatar()).into(binding.ivHead);
            }
            binding.ivBg.setImageResource(R.mipmap.img_home_header_bg);
        }else {
            binding.ivBg.setImageResource(R.mipmap.ic_menu_head_bg);
            binding.ivHead.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    LoginActivity.actionStart(getActivity());
                }
            });
            binding.ivHead.setImageResource(R.mipmap.ic_home_avatar_def_ard);
            binding.tvNick.setText("");
            binding.tvLogin.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void reload() {

    }

    @Override
    public void onClick(View v) {
        currentUserIsLogin = PreferenceManager.getInstance().getCurrentUserIsLogin();
        if(!currentUserIsLogin){
            LoginActivity.actionStart(getActivity());
            return;
        }
        switch (v.getId()) {
            case R.id.tv_client:
                ClientMaintenanceActivity.startAction(getContext());
                break;
            case R.id.tv_center:
                MatchingActivity.actionStart(getContext(), bean);
                break;
        }
    }
}