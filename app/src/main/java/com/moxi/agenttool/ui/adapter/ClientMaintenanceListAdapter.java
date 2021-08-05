package com.moxi.agenttool.ui.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.databinding.ItemClientMaintenanceViewBinding;
import com.moxi.agenttool.ui.bean.ClientListBean;
import com.moxi.agenttool.utils.CommonUtils;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import me.goldze.mvvmhabit.utils.StringUtils;


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
        extends BaseQuickAdapter<ClientListBean.DataDTO.ListDTO, BaseViewHolder> {

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
                           @Nullable final ClientListBean.DataDTO.ListDTO baseCustomViewModel) {

        if (baseCustomViewModel == null) {
            return;
        }
        ItemClientMaintenanceViewBinding binding = baseViewHolder.getBinding();

        if (binding != null) {
            binding.setViewModel(baseCustomViewModel);
            binding.executePendingBindings();
            if (!StringUtils.equals("0",baseCustomViewModel.getIsprivatephone())) {
                binding.tvEncryption.setVisibility(View.VISIBLE);
            }else {
                binding.tvEncryption.setVisibility(View.GONE);
            }
            binding.ivCall.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    CommonUtils.callPhone(mContext,baseCustomViewModel.getPhone());
                }
            });
            if(StringUtils.equals("0",baseCustomViewModel.getIsMatch())){
                binding.tvAddMatch.setText("添加到匹配中心");
                binding.tvAdd.setText("添加匹配");
            }else {
                binding.tvAddMatch.setText("移除匹配中心");
                binding.tvAdd.setText("移除匹配");
            }
        }


    }
}