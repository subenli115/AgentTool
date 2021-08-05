package com.moxi.agenttool.ui.main.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.FragmentBaseCommonBinding;
import com.moxi.agenttool.decoration.GridSectionAverageGapItemDecoration;
import com.moxi.agenttool.ui.adapter.BaseMatchDetailAdapter;
import com.moxi.agenttool.ui.base.activity.BaseWebActivity;
import com.moxi.agenttool.ui.base.fragment.CommHttpFragment;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.main.activity.MatchDetailActivity;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * @author feng wen jun
 * @description 匹配详情fragment
 * @since 2021/2/22 0022
 */
public class FindContactsRecommendFragment extends CommHttpFragment<FragmentBaseCommonBinding, MainViewModel> {
    private boolean isCollectioned;
    private FilterHouseResult.DataDTO dataDTO;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        baseBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void initData() {
        super.initData();
        MatchDetailActivity activity = (MatchDetailActivity) getActivity();
        dataDTO = activity.getDataDTO();
        updateData(dataDTO.getHouseList());
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        baseBinding.recyclerview.addItemDecoration(new GridSectionAverageGapItemDecoration());
        BaseMatchDetailAdapter baseMatchDetailAdapter = new BaseMatchDetailAdapter(R.layout.item_match_fragment_view,mContext);
        baseMatchDetailAdapter.addChildClickViewIds(R.id.iv_collection);
        baseMatchDetailAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_collection){
                    viewModel.postCollectionHouse(dataDTO.getHouseList().get(position),dataDTO.getClientId(),dataDTO.getClientUpdateTime());
                    if(!isCollectioned){
                        ((ImageView)view).setImageResource(R.mipmap.ic_match_collect_select);
                    }else {
                        ((ImageView)view).setImageResource(R.mipmap.ic_match_collect);
                    }
                }
            }
        });
        return baseMatchDetailAdapter;
    }


    @Override
    public ViewBinding getHeader() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        House bean= (House) mData.get(position);
        viewModel.addHistoryHouse(bean,dataDTO.getClientId(),dataDTO.getClientUpdateTime());
        BaseWebActivity.actionStart(mContext,bean.getDetailUrl(),bean.getName());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void reload() {

    }
}