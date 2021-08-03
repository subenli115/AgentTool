package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.databinding.ItemClientMaintenanceViewBinding;
import com.moxi.agenttool.ui.bean.FindTimeDetailBean;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     客户维护适配器
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class ClientMaintenanceListAdapter
        extends BaseQuickAdapter<FindTimeDetailBean.ListBean, BaseViewHolder> {

    private  Context mContext;

    public ClientMaintenanceListAdapter(int layoutResId, Context context) {
        super(layoutResId);
        mContext=context;
    }


    @Override
    protected void onItemViewHolderCreated( BaseViewHolder viewHolder,
                                           int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder,
                           @Nullable final FindTimeDetailBean.ListBean baseCustomViewModel) {

        if (baseCustomViewModel == null) {
            return;
        }
        ItemClientMaintenanceViewBinding binding = baseViewHolder.getBinding();

        if (binding != null) {
            binding.setViewModel(baseCustomViewModel);
            binding.executePendingBindings();
        }


    }
}