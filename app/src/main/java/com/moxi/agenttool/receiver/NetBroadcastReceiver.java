package com.moxi.agenttool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.moxi.agenttool.livedatas.LiveDataBus;


/**
 * 监听网络状态发生变化广播
 * Created by Administrator on 2017/3/1.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            LiveDataBus.get().with("netWorkState").setValue(NetUtil.getNetWorkState(context));
    }

}