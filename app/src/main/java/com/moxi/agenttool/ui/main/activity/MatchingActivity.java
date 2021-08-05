package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.adapter.MatchCenterListAdapter;
import com.moxi.agenttool.ui.base.activity.CommHttpActivity;
import com.moxi.agenttool.ui.bean.ClientListBean;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.utils.CommonUtils;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.moxi.agenttool.wdiget.SwipeItemLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MatchingActivity
 * @Description: 匹配中心
 * @Author: 冯文俊
 * @CreateDate: 2021/7/20 14:58
 */
public class MatchingActivity extends CommHttpActivity<ActivityBaseCommonBinding, MainViewModel> {


    private List<FilterHouseResult.DataDTO> matchList;
    private ClientListBean.DataDTO.ListDTO mListDTO;
    private List<FilterHouseResult.DataDTO> dataDTOList;
    private boolean first = true;

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
//        if(!first){
            viewModel.getKeyCustomers();
//        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        first = false;
        super.onRefresh(refreshLayout);

    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent() != null) {
            matchList = (List<FilterHouseResult.DataDTO>) getIntent().getSerializableExtra("matchList");
        }
        updateData(matchList);
        baseBinding.ntb.setRightImagSrc(R.mipmap.ic_match_add);
        baseBinding.ntb.setOnRightImagListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                AddClientActivity.startAction(mContext, false);
            }
        });
        baseBinding.ntb.setNewTitleText("匹配中心");
        baseBinding.ntb.setLineVisiable(true);
        LiveDataBus.get().with(AppConstans.BusTag.EDIT).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                Log.e("onChangededit","123");
            }
        });
        LiveDataBus.get().with(AppConstans.BusTag.UPDATE).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                Log.e("onChanged12","123");
                ClientListBean.DataDTO.ListDTO bean = (ClientListBean.DataDTO.ListDTO) o;
                FilterHouseResult.DataDTO dataDTO = new FilterHouseResult.DataDTO();
                dataDTO.setName(bean.getName());
                dataDTO.setAvatar(bean.getAvatar());
                dataDTO.setHouseList(new ArrayList<House>());
                dataDTO.setPhone(bean.getPhone());
                dataDTO.setRemark(bean.getRemark());
                matchList.add(dataDTO);

                updateData(matchList);
            }
        });
        viewModel.importTrantMutableLiveData.observe(this, new Observer<List<ImportantBean.DataDTO>>() {
            @Override
            public void onChanged(final List<ImportantBean.DataDTO> dataDTOS) {
//                if(!first){
//                    MatchUtils.getMatchData(dataDTOS,viewModel);
//                }else {
//                }
//
                for (int i = 0; i < dataDTOS.size(); i++) {
                    for (int k = 0; k < matchList.size(); k++) {
                        if (dataDTOS.get(i).getId().equals(matchList.get(k).getClientId())) {
                        } else {
                            FilterHouseResult.DataDTO dataDTO = new FilterHouseResult.DataDTO();
                            dataDTO.setName(dataDTOS.get(i).getName());
                            dataDTO.setAvatar(dataDTOS.get(i).getAvatar());
                            dataDTO.setHouseList(new ArrayList<House>());
                            dataDTO.setPhone(dataDTOS.get(i).getPhone());
                            dataDTO.setRemark(dataDTOS.get(i).getRemark());
                        }
                    }
                }
            }
        });
        viewModel.filterHouseResultMutableLiveData.observe(this, new Observer<List<FilterHouseResult.DataDTO>>() {
            @Override
            public void onChanged(List<FilterHouseResult.DataDTO> dataDTOS) {
                dataDTOList = dataDTOS;
                updateData(dataDTOList);
                binding.refreshLayout.finishRefresh(true);//传入false表示刷新失败
                LiveDataBus.get().with(AppConstans.BusTag.ADDBEAN).setValue(dataDTOList);
            }
        });
    }


    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        final MatchCenterListAdapter matchCenterListAdapter = new MatchCenterListAdapter(R.layout.item_match_center_view, this);
        matchCenterListAdapter.addChildClickViewIds(R.id.delete, R.id.container, R.id.iv_call);
        matchCenterListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FilterHouseResult.DataDTO dataDTO = matchList.get(position);
                if (view.getId() == R.id.delete) {
                    matchCenterListAdapter.remove(position);
                    viewModel.removeClientMatch(dataDTO.getClientId());
                } else if (view.getId() == R.id.container) {
                    MatchDetailActivity.startAction(mContext, dataDTO);
                } else if (view.getId() == R.id.iv_call) {
                    CommonUtils.callPhone(mContext, dataDTO.getPhone());
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
