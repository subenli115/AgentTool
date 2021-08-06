package com.moxi.agenttool.ui.main.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.viewbinding.ViewBinding;

import com.chad.library.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.databinding.ItemClientMaintenanceHeadViewBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.adapter.ClientMaintenanceListAdapter;
import com.moxi.agenttool.ui.base.activity.CommHttpActivity;
import com.moxi.agenttool.ui.bean.ClientListBean;
import com.moxi.agenttool.ui.bean.ClientQuestionBean;
import com.moxi.agenttool.ui.bean.UserTagBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.utils.CommonUtils;
import com.moxi.agenttool.view.SearchTabLeft;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.moxi.agenttool.wdiget.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @ClassName: ClientMaintenanceActivity
 * @Description: 客户维护列表
 * @Author: join_lu
 * @CreateDate: 2021/7/23 9:47
 */
public class ClientMaintenanceActivity extends CommHttpActivity<ActivityBaseCommonBinding, MainViewModel> {


    private SearchTabLeft searchTabLeft;
    private PopupWindow pop;
    private ArrayList<TextView> tvList;
    private ObjectAnimator objectAnimatorX;
    private int mPosition;
    private ClientListBean.DataDTO.ListDTO data;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        baseBinding.ntb.setLineVisiable(true);
        baseBinding.ntb.setNewTitleText("客户维护");
        baseBinding.loadedTip.setVisibility(View.GONE);
        searchTabLeft = new SearchTabLeft(this);
        viewModel.getTagList();
    }

    @Override
    protected void loadData() {
        ClientQuestionBean clientQuestionBean = new ClientQuestionBean();
        viewModel.getClientList(page + "", clientQuestionBean);
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));

        ClientMaintenanceListAdapter clientMaintenanceListAdapter = new ClientMaintenanceListAdapter(R.layout.item_client_maintenance_view, mContext);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
        animation.start();
        clientMaintenanceListAdapter.addChildClickViewIds(R.id.tv_delete, R.id.container, R.id.iv_call, R.id.tv_add, R.id.tv_add_match);
        clientMaintenanceListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition=position;
                data = (ClientListBean.DataDTO.ListDTO) adapter.getData().get(mPosition);
                View viewByPosition = baseBinding.recyclerview.getLayoutManager().findViewByPosition(mPosition+1);

                View rlContent = baseBinding.recyclerview.getLayoutManager().findViewByPosition(mPosition+1).findViewById(R.id.rl_content);
                View tvAddMatch = baseBinding.recyclerview.getLayoutManager().findViewByPosition(mPosition+1).findViewById(R.id.tv_add_match);
                if (view.getId() == R.id.tv_delete) {
                    viewModel.removeClient(data.getId());
                } else if (view.getId() == R.id.container) {
                    ClientDetailsActivity.startAction(mContext, data.getId(), true);
                } else if (view.getId() == R.id.iv_call) {
                    CommonUtils.callPhone(mContext, data.getPhone());
                } else if (view.getId() == R.id.tv_add) {
                    rlContent.setAlpha(0);
                    tvAddMatch.setClickable(false);
                    startPopsAnimTrans(tvAddMatch, viewByPosition);
                } else if (view.getId() == R.id.tv_add_match) {
                    if (data.getIsMatch().equals("0")) {
                        viewModel.addClientMatch(data.getId());
                    } else {
                        viewModel.removeClientMatch(data.getId());
                    }
                    tvAddMatch.setAlpha(0);
                    rlContent.setAlpha(1);
                }
            }
        });
        return clientMaintenanceListAdapter;
    }

    // 属性动画-平移
    public void startPopsAnimTrans(final View scaleAnimView, final View main) {
        objectAnimatorX = ObjectAnimator.ofFloat(scaleAnimView, "alpha", 0f, 1f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                scaleAnimView.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        objectAnimatorX.start();
    }

    @Override
    public ViewBinding getHeader() {
        tvList = new ArrayList<>();
        final ItemClientMaintenanceHeadViewBinding headBinding = ItemClientMaintenanceHeadViewBinding.inflate(LayoutInflater.from(this));
        tvList.add(headBinding.tvTag);
        tvList.add(headBinding.tvPosition);
        tvList.add(headBinding.tvPrice);
        tvList.add(headBinding.tvRemeber);
        for (int i = 0; i < tvList.size(); i++) {
            final int finalI = i;
            tvList.get(i).setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    for (int k = 0; k < tvList.size(); k++) {
                        setRightDefaultDrawable(tvList.get(k));
                    }
                    setRightDrawable(tvList.get(finalI));
                    showPopWindow();
                }
            });

        }
        return headBinding;
    }

    private void setRightDrawable(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.orange_7730));
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_clientlist_arrow_up);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    private void setRightDefaultDrawable(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.textColor_333));
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_clientlist_arrow_down);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.userTagBeanMutableLiveData.observe(this, new Observer<UserTagBean>() {
            @Override
            public void onChanged(UserTagBean userTagBean) {
                List<UserTagBean.DataDTO> data = userTagBean.getData();
                searchTabLeft.upData(data);
            }
        });
        viewModel.clientListBeanMutableLiveData.observe(this, new Observer<ClientListBean>() {
            @Override
            public void onChanged(ClientListBean clientListBean) {
                updateData(clientListBean.getData().getList());
            }
        });
        viewModel.isAdd.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ToastUtils.showLong("加入成功");
                data.setIsMatch("1");
                LiveDataBus.get().with(AppConstans.BusTag.EDIT).setValue("");
                LiveDataBus.get().with(AppConstans.BusTag.UPDATE).postValue(data);
                mAdapter.notifyItemChanged(mPosition+1);
            }
        });
        viewModel.isDelete.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                ToastUtils.showLong("移除成功");
                data.setIsMatch("0");
                LiveDataBus.get().with(AppConstans.BusTag.DELETE).postValue(data.getId());
                mAdapter.notifyItemChanged(mPosition+1);
            }
        });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    public static void startAction(Context context) {
        Intent starter = new Intent(context, ClientMaintenanceActivity.class);
        context.startActivity(starter);
    }

    public void showPopWindow() {
        pop = new PopupWindow(searchTabLeft, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        pop.setBackgroundDrawable(new ColorDrawable());
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);// 设置同意在外点击消失
        pop.setFocusable(true);// 点击空白处时，隐藏掉pop窗口
        pop.showAsDropDown(headBinding.getRoot(), 0, 0, Gravity.LEFT);
        searchTabLeft.findViewById(R.id.view_bg).setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                pop.dismiss();
            }
        });
        searchTabLeft.findViewById(R.id.tv_qd).setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                pop.dismiss();
            }
        });
        searchTabLeft.setOnSelectListener(new SearchTabLeft.OnSelectListener() {
            @Override
            public void getValue(String itemCode, String showText) {
                pop.dismiss();
            }
        });
    }
}
