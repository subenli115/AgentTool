package com.moxi.agenttool.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.moxi.agenttool.databinding.ItemTagClientViewBinding;
import com.moxi.agenttool.ui.bean.UserTagBean;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>    用户标签
 *
 * @author feng wen jun
 * @Since 2020-02-23
 */
public class ClientTagAdapter
    extends BaseQuickAdapter<UserTagBean.DataDTO, BaseViewHolder>
{
    private final Context context;
    public List<String> selectList = new ArrayList<>();
    public List<String> selectListId = new ArrayList<>();
    public ClientTagAdapter(int layoutResId, Context mContext)
    {
        super(layoutResId);
        context=mContext;
    }
    private String selectString;
    private String selectStringId;

    public void setSelectString(String selectString) {
        this.selectString = selectString;
    }

    public String getProfession() {
        if (selectList.size() > 0) {
            selectString = String.join(",", selectList);
        }
        return selectString;

    }

    public String getSelectStringId() {
        if (selectListId.size() > 0) {
            selectStringId = String.join(",", selectListId);
        }
        return selectStringId;

    }

    public void setSelectList(List<String> list){
        for(int i=0;i<list.size();i++){
            String temp = list.get(i);
            if(!selectList.contains(temp)){
                selectList.add(temp);
            }
        }
    }
    
    @Override
    protected void onItemViewHolderCreated( BaseViewHolder viewHolder,
        int viewType)
    {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder,
                           @Nullable final UserTagBean.DataDTO baseCustomViewModel)
    {
        if (baseCustomViewModel == null)
        {
            return;
        }
        final ItemTagClientViewBinding binding = baseViewHolder.getBinding();
        if (binding != null)
        {
            binding.setViewModel( baseCustomViewModel);
            binding.executePendingBindings();
            if(baseCustomViewModel.isSelect()){
                binding.ivSelect.setVisibility(View.VISIBLE);
                binding.tvName.setTextColor(Color.parseColor("#FF7730"));
            }else {
                binding.ivSelect.setVisibility(View.GONE);
                binding.tvName.setTextColor(Color.parseColor("#333333"));
            }
            binding.getRoot().setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    singleCheckPosition(baseViewHolder.getLayoutPosition(),binding.tvName,binding.ivSelect);
                }
            });
        }
    }

    public void singleCheckPosition(int pos, TextView check, ImageView ivSelect) {
        UserTagBean.DataDTO bean = getData().get(pos);
        if(!bean.isSelect()){
            bean.setSelect(true);
            selectList.add(bean.getName());
            selectListId.add(bean.getId());
            ivSelect.setVisibility(View.VISIBLE);
            check.setTextColor(Color.parseColor("#FF7730"));
        }else if(bean.isSelect()){
            check.setTextColor(Color.parseColor("#333333"));
            bean.setSelect(false);
            selectList.remove(bean.getName());
            selectListId.remove(bean.getId());
            ivSelect.setVisibility(View.GONE);
        }
    }
}