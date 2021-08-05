package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ItemMatchFragmentViewBinding;
import com.moxi.agenttool.ui.bean.House;

import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     匹配详情
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class BaseMatchDetailAdapter
    extends BaseQuickAdapter<House, BaseViewHolder>
{
    private final Context context;

    public BaseMatchDetailAdapter(int layoutResId, Context mContext)
    {
        super(layoutResId);
        context=mContext;
    }
    
    @Override
    protected void onItemViewHolderCreated( BaseViewHolder viewHolder,
        int viewType)
    {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert( BaseViewHolder baseViewHolder,
        @Nullable final House baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        ItemMatchFragmentViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel( baseCustomViewModel);
            binding.executePendingBindings();
            Glide.with(context).load(baseCustomViewModel.getImgUrl()).into(binding.ivBg);
            if(StringUtils.isEmpty(baseCustomViewModel.getPriceSecond())){
                binding.tvPrice.setText("价格待定");
            }else {

                binding.tvPrice.setText(baseCustomViewModel.getPriceSecond());
            }
            binding.tvArea.setText(baseCustomViewModel.getArea());
            binding.tvTitle.setText(baseCustomViewModel.getName());
            binding.tvUnitPrice.setText(baseCustomViewModel.getPriceFirst());
            binding.tvRoom.setText(baseCustomViewModel.getLocation()+" | "+baseCustomViewModel.getType());
            if(baseCustomViewModel.isCollection()){
                binding.ivCollection.setImageResource(R.mipmap.ic_match_collect_select);
            }else {
                binding.ivCollection.setImageResource(R.mipmap.ic_match_collect);
            }
        }
    }
}