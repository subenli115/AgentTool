package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.chad.library.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.databinding.ItemClientMaintenanceHeadViewBinding;
import com.moxi.agenttool.ui.adapter.ClientMaintenanceListAdapter;
import com.moxi.agenttool.ui.base.activity.CommHttpActivity;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.FindTimeDetailBean;
import com.moxi.agenttool.view.SearchTabLeft;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.ArrayList;

/**
 * @ClassName: ClientMaintenanceActivity
 * @Description: 客户维护列表
 * @Author: join_lu
 * @CreateDate: 2021/7/23 9:47
 */
public class ClientMaintenanceActivity extends CommHttpActivity<ActivityBaseCommonBinding, BaseViewModel> {


    private SearchTabLeft searchTabLeft;
    private PopupWindow pop;
    private ArrayList<TextView> tvList;

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
    }

    @Override
    protected void loadData() {

    }

    @Override
    public BaseQuickAdapter getAdapter() {
        return new ClientMaintenanceListAdapter(R.layout.item_client_maintenance_view, mContext);
    }


    @Override
    public ViewBinding getHeader() {
        tvList = new ArrayList<>();
        final ItemClientMaintenanceHeadViewBinding headBinding = ItemClientMaintenanceHeadViewBinding.inflate(LayoutInflater.from(this));
        tvList.add(headBinding.tvTag);
        tvList.add(headBinding.tvPosition);
        tvList.add(headBinding.tvPrice);
        tvList.add(headBinding.tvRemeber);
        for(int i=0;i<tvList.size();i++){
            final int finalI = i;
            tvList.get(i).setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    for(int k=0;k<tvList.size();k++){
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
        ArrayList<FindTimeDetailBean.ListBean> objects = new ArrayList<>();
        FindTimeDetailBean.ListBean listBean = new FindTimeDetailBean.ListBean();
        objects.add(listBean);
        objects.add(listBean);
        objects.add(listBean);
        updateData(objects);
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
        searchTabLeft.setOnSelectListener(new SearchTabLeft.OnSelectListener() {
            @Override
            public void getValue(String itemCode, String showText) {
                pop.dismiss();
            }
        });
    }
}
