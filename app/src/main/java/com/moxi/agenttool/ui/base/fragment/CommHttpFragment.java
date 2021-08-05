package com.moxi.agenttool.ui.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.FragmentBaseCommonBinding;
import com.moxi.agenttool.ui.base.BaseView;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.utils.EmptyUtils;
import com.moxi.agenttool.wdiget.LoadingTip;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;


public abstract class CommHttpFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V, VM> implements OnRefreshLoadMoreListener, OnItemClickListener, BaseView {
    /**
     * 默认每次加载返回条数为5条
     */
    private int mLoadCount = 10;
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
    public FragmentBaseCommonBinding baseBinding;
    public ViewBinding headBinding;

    /**
     * 网络加载的状态控件
     */
    protected LoadingTip loadedTip;
    public Context mContext;
    public Bundle arguments;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_base_common;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();


    @Override
    public void initData() {
        mContext=getActivity();

         arguments = getArguments();

        baseBinding = (FragmentBaseCommonBinding) binding;
        loadedTip= getActivity().findViewById(R.id.loadedTip);
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
        if (mAdapter != null) {
            if (mData != null&& mData.size() > 0) {
                mData.clear();
                mAdapter.notifyDataSetChanged();
            }
        }
        if (mData != null) {
            mData.clear();
        }
        page = 1;
        //发起请求
        refresh=refresh1;
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
     * 获取RecyclerView的布局管理器
     *
     * @return RecyclerView的布局管理器
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
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
            stopLoading();
        } else {    //数据为空
            if (getAdapter().getHeaderLayout() != null) {
                if (mAdapter.getItemCount() == 0) {
                    baseBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
            } else {
                showEmtry();
            }
            stopLoading();
            setRefresh(false);
        }
    }


    /**
     * 显示加载结束状态
     */
    @Override
    public void stopLoading () {
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
    public void showErrorTip (String msg) {
        if (loadedTip != null) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
        }
    }

    /**
     * 显示暂无数据 状态
     */
    @Override
    public void showEmtry () {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.empty);
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
