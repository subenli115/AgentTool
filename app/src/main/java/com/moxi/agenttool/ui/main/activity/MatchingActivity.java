package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.ui.adapter.MatchCenterListAdapter;
import com.moxi.agenttool.ui.base.activity.CommHttpActivity;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.moxi.agenttool.wdiget.SwipeItemLayout;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: MatchingActivity
 * @Description: 匹配中心
 * @Author: 冯文俊
 * @CreateDate: 2021/7/20 14:58
 */
public class MatchingActivity extends CommHttpActivity<ActivityBaseCommonBinding, MainViewModel>  {


    private List<FilterHouseResult.DataDTO> matchList;

    public static void actionStart(Context context, List<FilterHouseResult.DataDTO> list) {
        Intent intent = new Intent(context, MatchingActivity.class);
        intent.putExtra("matchList", (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void initData() {
        super.initData();
        if(getIntent()!=null){
            matchList = (List<FilterHouseResult.DataDTO>) getIntent().getSerializableExtra("matchList");
        }
        baseBinding.ntb.setRightImagSrc(R.mipmap.ic_match_add);
        baseBinding.ntb.setOnRightImagListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                AddClientActivity.startAction(mContext);
            }
        });
        baseBinding.ntb.setNewTitleText("匹配中心");
        baseBinding.ntb.setLineVisiable(true);

        updateData(matchList);
    }


    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        final MatchCenterListAdapter matchCenterListAdapter = new MatchCenterListAdapter(R.layout.item_match_center_view, this);
        matchCenterListAdapter.addChildClickViewIds(R.id.delete,R.id.container,R.id.iv_call);
        matchCenterListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FilterHouseResult.DataDTO dataDTO = matchList.get(position);
                if(view.getId()==R.id.delete){
                    matchCenterListAdapter.remove(position);
                }else if(view.getId()==R.id.container){
                    MatchDetailActivity.startAction(mContext,dataDTO);
                }else if(view.getId()==R.id.iv_call){
//                    CommonUtils.callPhone(mContext,dataDTO.getPhone());
                }
            }
        });
        return matchCenterListAdapter;
    }

    @Override
    public ViewBinding getHeader() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {





    }
}
