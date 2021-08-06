package com.moxi.agenttool.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ItemMyHouseViewBinding;
import com.moxi.agenttool.ui.bean.House;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>     我的房源
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class MyHouseAdapter
    extends BaseQuickAdapter<House, BaseViewHolder>
{
    private final Context context;

    public MyHouseAdapter(int layoutResId, Context mContext)
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
        ItemMyHouseViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel( baseCustomViewModel);
            binding.executePendingBindings();
            if(baseViewHolder.getLayoutPosition()==0){
                binding.ivBg.setImageResource(R.mipmap.img_listings_01);
                binding.tvPrice.setText("265万");
                binding.tvName.setText("国浩18T");
            }
            if(baseViewHolder.getLayoutPosition()==1){
                binding.ivBg.setImageResource(R.mipmap.img_listings_02);
                binding.tvPrice.setText("1200元/月");
                binding.tvName.setText("庐州月");
            }

            if(baseViewHolder.getLayoutPosition()==2){
                binding.ivBg.setImageResource(R.mipmap.img_listings_03);
                binding.tvPrice.setText("265万");
                binding.tvName.setText("下里巴人");
            }
            if(baseViewHolder.getLayoutPosition()==3){
                binding.ivBg.setImageResource(R.mipmap.img_listings_04);
                binding.tvPrice.setText("1400万");
                binding.tvName.setText("阳春白雪醉美天");
            }
        }
    }
}