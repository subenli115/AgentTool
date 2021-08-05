package com.moxi.agenttool.ui.main.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.FragmentBaseCommonBinding;
import com.moxi.agenttool.decoration.GridSectionAverageGapItemDecoration;
import com.moxi.agenttool.ui.adapter.BaseMatchDetailTabAdapter;
import com.moxi.agenttool.ui.base.activity.BaseWebActivity;
import com.moxi.agenttool.ui.base.fragment.CommHttpFragment;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.ui.main.activity.MatchDetailActivity;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;

/**
 * @author feng wen jun
 * @description 历史房源fragment
 * @since 2021/2/22 0022
 */
public class HistoryHouseListFragment extends CommHttpFragment<FragmentBaseCommonBinding, MainViewModel> {
    private boolean isCollectioned;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void loadData() {
        MatchDetailActivity activity = (MatchDetailActivity) getActivity();
        String clientId = activity.getDataDTO().getClientId();
        viewModel.getHistoryHouse(page+"",clientId);
    }




    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        baseBinding.recyclerview.addItemDecoration(new GridSectionAverageGapItemDecoration());
        BaseMatchDetailTabAdapter baseMatchDetailAdapter = new BaseMatchDetailTabAdapter(R.layout.item_match_fragment_view2,mContext);
        baseMatchDetailAdapter.addChildClickViewIds(R.id.iv_collection);
        baseMatchDetailAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_collection){
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
        MatchDetailHistoryResult.DataDTO.ListDTO bean= (MatchDetailHistoryResult.DataDTO.ListDTO) mData.get(position);
        BaseWebActivity.actionStart(mContext,bean.getDetailUrl(),"");
    }

    @Override
    public void initViewObservable() {
        viewModel.matchDetailHistoryResultMutableLiveData.observe(this, new Observer<MatchDetailHistoryResult>() {
            @Override
            public void onChanged(MatchDetailHistoryResult matchDetailHistoryResult) {
                    updateData(matchDetailHistoryResult.getData().getList());
            }
        });

    }

    @Override
    public void reload() {

    }
}