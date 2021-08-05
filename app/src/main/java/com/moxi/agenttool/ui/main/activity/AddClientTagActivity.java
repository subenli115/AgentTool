package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityClientTagBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.bean.UserTagBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

/**
 * @ClassName: AddClientTagActitivy
 * @Description: 用户标签
 * @Author: join_lu
 * @CreateDate: 2021/8/3 17:38
 */
public class AddClientTagActivity  extends BaseActivity<ActivityClientTagBinding, MainViewModel> {

    private UserTagBean mUserTagBean;
    private UserTagBean.DataDTO dataDTO;

    public static void startAction(Context context) {
        Intent starter = new Intent(context, AddClientTagActivity.class);
        context.startActivity(starter);
    }




    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_client_tag;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.ntb.setLineVisiable(true);
        viewModel.getTagList();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.userTagBeanMutableLiveData.observe(this, new Observer<UserTagBean>() {
            @Override
            public void onChanged(UserTagBean userTagBean) {
                 dataDTO = userTagBean.getData().get(0);

            }

        });
        binding.tvName.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LiveDataBus.get().with("userTagBean").postValue(dataDTO);
                finish();
            }
        });
    }
}
