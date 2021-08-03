package com.moxi.agenttool.ui.base;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
public interface BaseView {
    /*******内嵌加载*******/
    void stopLoading();
    void showErrorTip(String msg);
    void showEmtry();
}
