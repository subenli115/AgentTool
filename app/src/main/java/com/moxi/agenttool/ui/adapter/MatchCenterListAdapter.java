package com.moxi.agenttool.ui.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.databinding.ItemMatchCenterViewBinding;
import com.moxi.agenttool.ui.bean.FilterHouseResult;

import me.goldze.mvvmhabit.utils.StringUtils;


/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     匹配中心适配器
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class MatchCenterListAdapter
        extends BaseQuickAdapter<FilterHouseResult.DataDTO, BaseViewHolder> {

    private  Context mContext;

    public MatchCenterListAdapter(int layoutResId, Context context) {
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
                           @Nullable final FilterHouseResult.DataDTO baseCustomViewModel) {

        if (baseCustomViewModel == null) {
            return;
        }
        ItemMatchCenterViewBinding binding = baseViewHolder.getBinding();

        if (binding != null) {
            binding.setViewModel(baseCustomViewModel);
            binding.executePendingBindings();
            binding.tvMatchNum.setText("匹配"+baseCustomViewModel.getHouseList().size()+"套");
            binding.tvName.setText(baseCustomViewModel.getName());
            if(!StringUtils.isEmpty(baseCustomViewModel.getAvatar())){
                Glide.with(mContext).load(baseCustomViewModel.getAvatar()).into(binding.ivCover);
            }
            if(!baseCustomViewModel.getIsprivatephone().equals("0")){
                binding.tvEncryption.setVisibility(View.VISIBLE);
            }else {
                binding.tvEncryption.setVisibility(View.GONE);
            }
            binding.tvNum.setText(baseCustomViewModel.getPhone());
            binding.tvMark.setText(baseCustomViewModel.getRemark());

        }


    }
}