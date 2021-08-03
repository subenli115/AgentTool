package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ItemMatchFragmentView2Binding;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.utils.GsonUtils;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     匹配详情分类
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class BaseMatchDetailTabAdapter
    extends BaseQuickAdapter<MatchDetailHistoryResult.DataDTO.ListDTO, BaseViewHolder>
{
    private final Context context;

    public BaseMatchDetailTabAdapter(int layoutResId, Context mContext)
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
        @Nullable final MatchDetailHistoryResult.DataDTO.ListDTO baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        ItemMatchFragmentView2Binding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel( baseCustomViewModel);
            binding.executePendingBindings();
            House house = GsonUtils.fromLocalJson(baseCustomViewModel.getDetail(), House.class);
            Glide.with(context).load(house.getImgUrl()).into(binding.ivBg);
            binding.tvPrice.setText(house.getPriceSecond());
            binding.tvArea.setText(house.getArea());
            binding.tvTitle.setText(house.getName());
            if(baseCustomViewModel.getIsCollection()){
                binding.ivCollection.setImageResource(R.mipmap.ic_match_collect_select);
            }else {
                binding.ivCollection.setImageResource(R.mipmap.ic_match_collect);
            }
            binding.tvUnitPrice.setText(house.getPriceFirst());
            binding.tvRoom.setText(house.getLocation()+" | "+house.getType());
        }
    }
}