package com.moxi.agenttool.ui.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.moxi.agenttool.R;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.receiver.NetUtil;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.utils.NetWorkUtils;
import com.moxi.agenttool.view.ViewPagerForScrollView;
import com.moxi.agenttool.wdiget.LoadingTip;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.goldze.mvvmhabit.base.ContainerActivity;
import me.goldze.mvvmhabit.base.IBaseView;
import me.goldze.mvvmhabit.bus.Messenger;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseView, LoadingTip.onReloadListener {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;
    private boolean isFragmentVisible;
    private boolean hasCreateView;

    /**
     * 网络加载的状态控件
     */
    public LoadingTip loadedTip;
    private ViewPagerForScrollView mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        initParam();
    }
    public void setViewpager(ViewPagerForScrollView viewPager){
        mViewPager=viewPager;
    }

    // onCreateAnimation() 是 Fragment 的一个方法，配合 setTransition() 使用
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                // 表示普通的进入的动作，比如add、show、attach等
                return AnimationUtils.loadAnimation(getContext(), R.anim.fragment_enter_in);
            } else {
                // 比如一个Fragmen已经被另一个replace，被replace的那个就是false
                return AnimationUtils.loadAnimation(getContext(), R.anim.fragment_enter_in);
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
            if (enter) {
                // 之前被replace的重新进入到界面或者Fragment回到栈顶
                return AnimationUtils.loadAnimation(getContext(), R.anim.fragment_enter_in);
            } else {
                // 表示一个退出动作，比如出栈、remove、hide、detach等
                return AnimationUtils.loadAnimation(getContext(), R.anim.fragment_enter_in);
            }
        }
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 显示加载中状态
     *
     * @param title 显示加载的标题
     */
    public void showLoading(String title) {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    /**
     * 显示加载结束状态
     */
    public void stopLoading() {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        //页面数据初始化方法
        if (loadedTip == null) {
            loadedTip = (LoadingTip) binding.getRoot().findViewById(R.id.loadedTip);
        }
        //设置重新加载监听
        if (loadedTip != null) {
            loadedTip.setOnReloadListener(this);
        }
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
        //订阅网络状态发生变化的事件
        boolean netConnected = NetWorkUtils.isNetConnected(getContext());
        if (!netConnected) {
            setLoadedTipState(LoadingTip.LoadStatus.noWifi);
        }
        LiveDataBus.get().with("netWorkState", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e("netWorkState", "" + integer);
                switch (integer) {
                    case NetUtil.NETWORK_NONE: //没有网络
                        setLoadedTipState(LoadingTip.LoadStatus.noWifi);
                        break;
                    case NetUtil.NETWORK_MOBILE:
                    case NetUtil.NETWORK_WIFI:
                        break;
                }
            }
        });
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            initData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    /**
     * 设置加载状态
     *
     * @param loadStatus 加载状态
     */
    public void setLoadedTipState(LoadingTip.LoadStatus loadStatus) {
        if (loadedTip == null)
            return;
        loadedTip.setLoadingTip(loadStatus);
    }


    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
//        if (isVisibleToUser) {
//            isUIVisible = true;
//            lazyLoad();
//        } else {
//            isUIVisible = false;
//        }
//    }

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * =====================================================================
     **/

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    public abstract void initData();

    @Override
    public void initViewObservable() {

    }

    public boolean isBackPressed() {
        return false;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }
}

