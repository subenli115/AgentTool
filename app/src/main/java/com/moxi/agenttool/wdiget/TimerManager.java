package com.moxi.agenttool.wdiget;

/**
 * @ClassName: TimerManager
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/8/3 10:31
 */

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务管理器
 * @author dyh
 *
 */
public class TimerManager {

    /**
     * 单例模式
     */
    private static TimerManager timerManager = null;
    private TimerManager(){}
    public static TimerManager getInstance(){
        if(timerManager == null){
            timerManager = new TimerManager();
        }
        return timerManager;
    }

    /**
     * 定时器
     */
    private Timer timer = new Timer("homePageTimer");

    /**
     * 定时任务
     */
    private Timer timerTask = null;

    /**
     * 启动定时任务
     */
    public void startTimerTask(){
        timer.purge();
        if(timerTask==null){
            timerTask = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


            }
        }, 1000, 1000);
    }

    /**
     * 定时任务取消
     */
    public void stopTimerTask(){
        timerTask.cancel();
        timerTask = null;//如果不重新new，会报异常
    }

}
