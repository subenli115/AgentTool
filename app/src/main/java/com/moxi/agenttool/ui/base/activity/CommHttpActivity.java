package com.moxi.agenttool.ui.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.ui.base.BaseView;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.utils.EmptyUtils;
import com.moxi.agenttool.wdiget.LoadingTip;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;


/**
 * 应用模块: activity
 * <p>
 * 类描述: activity抽象基类
 * <p>
 *
 * @author feng wen jun
 * @Since 2020-01-27
 */
public abstract class CommHttpActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> implements OnRefreshLoadMoreListener, OnItemClickListener, BaseView {
    /**
     * 默认每次加载返回条数为5条
     */
    private int mLoadCount =10;
    /**
     * 基类数据源
     */
    public List mData;
    /**
     * 当前请求的页数
     */
    public int page = 1;
    private boolean refresh = true;
    public BaseQuickAdapter mAdapter;
    public ActivityBaseCommonBinding baseBinding;
    public ViewBinding headBinding;


    /**
     * 网络加载的状态控件
     */
    protected LoadingTip loadedTip;
    public Context mContext;
    public Intent intent;

    //第一次请求数据
    private boolean firstRequest = true;
    private View loadedTip1;
    private boolean first;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_base_common;
    }


    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    @Override
    public void initData() {
        super.initData();
        intent = getIntent();
        mContext = this;
        first=true;
        baseBinding = (ActivityBaseCommonBinding) binding;
        if (loadedTip == null) {
            loadedTip = ((ActivityBaseCommonBinding) binding).loadedTip;
        }
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        loadData();
        initRecyclerView();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        refresh = false;
        loadData();
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        onRefreshBy(true);
        refreshLayout.setNoMoreData(false);
        refreshLayout.finishRefresh();
    }

    public void onRefreshBy(boolean refresh1) {
        page = 1;
        //发起请求
        refresh = refresh1;
        //网络检查
        loadData();
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 构建适配器
     *
     * @return 适配器
     */
    public abstract BaseQuickAdapter getAdapter();


    /**
     * 添加头部
     *
     * @return 适配器
     */
    public abstract ViewBinding getHeader();


    public void setHeader() {
        View root = headBinding.getRoot();
        mAdapter.addHeaderView(root);
    }

    /**
     * 初始化列表控件和适配器
     */
    protected void initRecyclerView() {
        baseBinding.refreshLayout.setOnRefreshLoadMoreListener(this);
        baseBinding.recyclerview.setHasFixedSize(true);
        baseBinding.recyclerview.setLayoutManager(getLayoutManager());
        mAdapter = getAdapter();
        if (baseBinding.recyclerview.getAdapter() == null) {
            baseBinding.recyclerview.setAdapter(mAdapter);

        }
        mAdapter.setOnItemClickListener(this);
        headBinding = getHeader();
        if (headBinding != null) {
            initHeadView();
            setHeader();
        }
    }

    public void initHeadView() {

    }

    ;

    /**
     * 显示加载结束状态
     */
    @Override
    public void stopLoading() {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    /**
     * 显示加载错误提示状态
     *
     * @param msg 错误的信息
     */
    @Override
    public void showErrorTip(String msg) {
        if (loadedTip != null) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
        }
    }

    /**
     * 显示暂无数据 状态
     */
    @Override
    public void showEmtry() {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.empty);
    }

    /**
     * 获取RecyclerView的布局管理器
     *
     * @return RecyclerView的布局管理器
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    /**
     * 更新数据
     *
     * @param data 数据源
     */
    public void updateData(List data) {
        if (baseBinding.recyclerview == null) {
            return;
        }
        if (EmptyUtils.isNotEmpty(data)) {
            page++;
            setRefresh(false);
            if (refresh) { //如果是下拉刷新则替换旧数据
                refresh = false;
                mAdapter.replaceData(data);
            } else {
                mAdapter.addData(data);
                if (data.size() < mLoadCount) {
                    baseBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
            mData=mAdapter.getData();
//            stopLoading();
        } else {    //数据为空
            if (getAdapter().getHeaderLayout() != null) {
                if (mAdapter.getItemCount() == 0) {
                    baseBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
            } else {

                showEmtry();
            }
//            stopLoading();
            loadedTip.setVisibility(View.GONE);
            setRefresh(false);
        }
        if (firstRequest){
            firstRequest = false;
            stopLoading();
        }
    }

    /**
     * 设置刷新状态
     *
     * @param refresh true 启动刷新  FALSE 刷新结束
     */
    public void setRefresh(boolean refresh) {
        if (baseBinding.recyclerview == null) {
            baseBinding.refreshLayout.setEnableRefresh(false);
        }
    }
}
