package com.moxi.agenttool.ui.login.respository;


import android.app.Application;

import androidx.annotation.NonNull;

import com.moxi.agenttool.entity.StringDataBean;
import com.moxi.agenttool.global.ApiKey;
import com.moxi.agenttool.source.LocalDataSource;
import com.moxi.agenttool.ui.base.model.BaseSingleLiveEvent;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.UserInfoBean;
import com.moxi.agenttool.ui.bean.UserLoginBean;
import com.moxi.agenttool.utils.GsonUtils;
import com.moxi.agenttool.utils.PreferenceManager;
import com.moxi.agenttool.wdiget.LoadingDialog;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.HttpManager;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @Package: com.benwunet.sign.ui.respository
 * @ClassName: SignRepository
 * @Description: 登录模块仓库
 * @Author: feng wen jun
 * @Since: 2020/10/28 0028 11:20
 * @Version: 1.0
 */

public class SignRepository  implements LocalDataSource {
    private volatile static SignRepository instance = null;
    private final LocalDataSource mLocalDataSource;
    private final BaseViewModel viewModel;
    private final Application application;

    public SignRepository(@NonNull LocalDataSource localDataSource, BaseViewModel viewModel, Application application) {
        this.mLocalDataSource = localDataSource;
        this.viewModel = viewModel;
        this.application = application;
    }


    public static SignRepository getInstance(LocalDataSource localDataSource, BaseViewModel viewModel, Application application) {
        if (instance == null) {
            synchronized (SignRepository.class) {
                if (instance == null) {
                    instance = new SignRepository(localDataSource, viewModel, application);
                }
            }
        }
        return instance;
    }


    public SingleLiveEvent<Boolean> getVerifyCode(String phone, final SingleLiveEvent<Boolean> verifyCode, String type) {
        HttpManager.get(ApiKey.USER_SMS_SEND)
                .append("mobile", phone)
                .append("smsType", type)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        ToastUtils.showLong(bean.getMessage());
                        if (StringUtils.equals(bean.getCode(),"200")) {
                            verifyCode.setValue(true);
                        }


                    }
                });
        return verifyCode;
    }


    public void codeLogin(final String phone, String verifyCode, final BaseSingleLiveEvent isLogin) {
        HttpManager.get( ApiKey.USER_lOGIN_CODE)
                .cacheMode(CacheMode.NO_CACHE)
                .cacheKey(this.getClass().getSimpleName())
                .params("code",verifyCode)
                .params("username",phone)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        UserLoginBean bean = GsonUtils.fromLocalJson(result, UserLoginBean.class);
                        if (bean != null) {
                            if(bean.getData()!=null){
                                isLogin.call();
                            }
                            saveUserName(phone);
                            saveToken(bean.getData().getToken());
                            getUserInfo();
                        }
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }


    public void getUserInfo() {
        HttpManager.get( ApiKey.USER_USERIFO)
                .cacheMode(CacheMode.NO_CACHE)
                .accessToken()
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        UserInfoBean bean = GsonUtils.fromLocalJson(result, UserInfoBean.class);
                        UserInfoBean.DataDTO data = bean.getData();
                        if (data!= null) {
                            PreferenceManager.getInstance().setCurrentUserAvatar(data.getAvatar());
                            PreferenceManager.getInstance().setCurrentUserNick(data.getNickName());
                        }else {
                            ToastUtils.showLong(bean.getMsg());
                        }
                        LoadingDialog.cancelDialogForLoading();
                    }
                });
    }



    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public void saveToken(String token) {
        mLocalDataSource.saveToken(token);
    }

    @Override
    public void saveRefreshToken(String reToken) {
        mLocalDataSource.saveRefreshToken(reToken);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public String getToken() {
        return mLocalDataSource.getToken();
    }
}
