package com.moxi.agenttool.ui.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.data.DemoRepository;
import com.moxi.agenttool.source.local.LocalDataSourceImpl;
import com.moxi.agenttool.ui.base.model.BaseSingleLiveEvent;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.login.respository.SignRepository;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel<DemoRepository> {
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public SingleLiveEvent<String> verifyCode = new SingleLiveEvent<>();
    public ObservableField<String> password = new ObservableField<>("");
    public BaseSingleLiveEvent isLogin = new BaseSingleLiveEvent<>();
    //用户名清除按钮的显示隐藏绑定
    public SingleLiveEvent<Boolean> isSend = new SingleLiveEvent<>();
    private SignRepository signRepository = SignRepository.getInstance(LocalDataSourceImpl.getInstance(), this,getApplication());
    public MutableLiveData<String> phone = new MutableLiveData<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public LoginViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);
        //从本地取得数据绑定到View层
        userName.set(model.getUserName());
        password.set(model.getPassword());
    }

    //获取验证码
    public SingleLiveEvent<Boolean> getCode(String type) {
        if (phone != null && phone.getValue().length() == 0) {
            ToastUtils.showLong("请输入手机号");
        } else {
            isSend = signRepository.getVerifyCode(phone.getValue(), isSend, type);
        }
        return isSend;
    }

    /**
     * 验证码登录操作
     *
     * */
    public void codeLogin() {
        signRepository.codeLogin(phone.getValue(), "dora666",isLogin);
    }

    /**
     * 获取用户信息
     *
     * */
    public void getUserInfo() {
        signRepository.getUserInfo(isLogin);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
