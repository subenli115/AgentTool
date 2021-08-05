package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityUserInfoBinding;
import com.moxi.agenttool.global.SPKeyGlobal;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.utils.PreferenceManager;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.zhouyou.http.EasyHttp;

import me.goldze.mvvmhabit.utils.SPUtils;

/**
 * @ClassName: UserInfoActivity
 * @Description: 个人中心
 * @Author: join_lu
 * @CreateDate: 2021/8/5 9:42
 */
public class UserInfoActivity extends BaseActivity<ActivityUserInfoBinding, MainViewModel> {


    public static void startAction(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        context.startActivity(starter);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_user_info;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
//        Glide.with(mContext).load(PreferenceManager.getInstance().getCurrentUserAvatar()).into(binding.ivHead);
        binding.tvName.setText(PreferenceManager.getInstance().getCurrentUserNick());
        binding.tvPhone.setVisibility(View.GONE);
        binding.tvQuit.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                PreferenceManager.getInstance().removeCurrentUserInfo();
                SPUtils.getInstance().remove(SPKeyGlobal.TOKEN);
                SPUtils.getInstance().remove(SPKeyGlobal.R_TOKEN);
                PreferenceManager.getInstance().setIsLogin(false);
                EasyHttp.clearCache();
                finish();
                LiveDataBus.get().with(AppConstans.BusTag.QUIT).postValue("");
            }
        });
    }
}
