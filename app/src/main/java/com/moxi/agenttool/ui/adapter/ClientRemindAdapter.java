package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ItemRemindViewBinding;
import com.moxi.agenttool.ui.bean.House;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     提醒
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class ClientRemindAdapter
    extends BaseQuickAdapter<House, BaseViewHolder>
{
    private final Context context;

    public ClientRemindAdapter(int layoutResId, Context mContext)
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
        ItemRemindViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel( baseCustomViewModel);
            binding.executePendingBindings();
            if(baseViewHolder.getLayoutPosition()==0){
                binding.ivTime.setImageResource(R.mipmap.demo1);
            }
            if(baseViewHolder.getLayoutPosition()==1){
                binding.ivTime.setImageResource(R.mipmap.demo3);
            }

            if(baseViewHolder.getLayoutPosition()==2){
                binding.ivTime.setImageResource(R.mipmap.demo2);
            }
            if(baseViewHolder.getLayoutPosition()==3){
                binding.ivTime.setImageResource(R.mipmap.demo1);
            }
        }
    }
}