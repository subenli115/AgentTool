package com.moxi.agenttool.ui.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.ui.base.model.BaseViewModel;


/**
 * Created by feng wen jun on 2020/10/15.
 */

public class BaseHttpViewModel extends BaseViewModel {

    public BaseHttpViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 刷新liveData，绑定相应数据的界面会更新
     */
    public <T> void refreshLiveData(MutableLiveData<T> liveData) {
        liveData.setValue(liveData.getValue());
    }






}
