package com.moxi.agenttool.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.app.AppViewModelFactory;
import com.moxi.agenttool.databinding.ActivityLoginBinding;
import com.moxi.agenttool.global.IConstants;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.login.viewmodel.LoginViewModel;
import com.moxi.agenttool.wdiget.LoadingDialog;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.moxi.agenttool.wdiget.SmsCodeView;

import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements TextWatcher {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
               XXPermissions.with(this)
                .permission(Permission.CALL_PHONE)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean never) {
                    }
                });
        binding.etPhone.addTextChangedListener(this);
        binding.smsCodeView.getEtitTextCode().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    binding.tvLogin.setBackgroundResource(R.drawable.normal_bg_orange40);
                    binding.tvLogin.setTextColor(Color.WHITE);
                }else {
                    binding.tvLogin.setBackgroundResource(R.drawable.normal_bg_oranage_fe);
                    binding.tvLogin.setTextColor(getResources().getColor(R.color.textColor_999));
                }
            }
        });
        binding.smsCodeView.setEvPhone(binding.etPhone);
        binding.smsCodeView.setOnSmsCallBack(new SmsCodeView.OnSmsCallBack() {
            @Override
            public void call(int type) {
                viewModel.getCode(IConstants.LOGIN);
            }

        });
        binding.smsCodeView.setTvPhone(binding.etPhone);
        viewModel.isSend.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean code) {

//                binding.smsCodeView.startDjs();

            }
        });
        binding.tvLogin.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (TextUtils.isEmpty(viewModel.phone.getValue())) {
                    ToastUtils.showShort("请输入手机号！");
                    return;
                }
                if (TextUtils.isEmpty(viewModel.verifyCode.getValue())) {
                    ToastUtils.showShort("请输入验证码！");
                    return;
                }
                LoadingDialog.showDialogForLoading(LoginActivity.this, "正在登录..", true);
                viewModel.codeLogin();
            }
        });
    }


    @Override
    public void initViewObservable() {
        viewModel.verifyCode.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtils.showLong("发送成功");
            }
        });
        viewModel.isLogin.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ToastUtils.showLong("登录成功");

            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().length()>=11){
            binding.smsCodeView.getTvSmsGetCode().setBackgroundResource(R.drawable.normal_bg_orange40);
            binding.smsCodeView.getTvSmsGetCode().setTextColor(Color.WHITE);
        }else {
            binding.smsCodeView.getTvSmsGetCode().setBackgroundResource(R.drawable.normal_bg_oranage_fe);
            binding.smsCodeView.getTvSmsGetCode().setTextColor(getResources().getColor(R.color.textColor_999));
        }
    }
}
