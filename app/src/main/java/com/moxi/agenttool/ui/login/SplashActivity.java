package com.moxi.agenttool.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.moxi.agenttool.global.SPKeyGlobal;
import com.moxi.agenttool.ui.main.activity.HomeActivity;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * Created by feng wen jun on 2020/10/15.
 * 冷启动
 */

public class SplashActivity extends AppCompatActivity implements CancelAdapt {

    private boolean isFirstUse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(SPKeyGlobal.USE_FIRST,MODE_PRIVATE);
        isFirstUse = preferences.getBoolean(SPKeyGlobal.USE_FIRST, true);
        /**
         *如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
         */
        if (isFirstUse) {
            //延迟两秒进入主页
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }, 1000);
        }

    }



    //    /**
//     * 进入主页面
//     */
//    private void inMain() {
//        //采用ARouter+RxBus实现组件间通信
//        MainActivity.startAction(mContext);
//        finish();
//    }
//
//    /**
//     * 进入登录页面
//     */
//    private void inLogin() {
//        //采用ARouter+RxBus实现组件间通信
//        ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
//        finish();
//    }


}
