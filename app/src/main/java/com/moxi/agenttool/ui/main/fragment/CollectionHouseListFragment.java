package com.moxi.agenttool.ui.main.fragment;

import android.view.View;

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
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.ui.main.activity.MatchDetailActivity;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;

import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author feng wen jun
 * @description 收藏房源fragment
 * @since 2021/2/22 0022
 */
public class CollectionHouseListFragment extends CommHttpFragment<FragmentBaseCommonBinding, MainViewModel> {
    private boolean isCollectioned;
    private FilterHouseResult.DataDTO dataDTO;
    private String clientId;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void loadData() {
        viewModel.getCollectionHouseList(page + "", clientId);
    }

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    public void initData() {
        MatchDetailActivity activity = (MatchDetailActivity) getActivity();
        dataDTO = activity.getDataDTO();
         clientId = dataDTO.getClientId();
        super.initData();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        baseBinding.recyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
        baseBinding.recyclerview.addItemDecoration(new GridSectionAverageGapItemDecoration());
        BaseMatchDetailTabAdapter baseMatchDetailAdapter = new BaseMatchDetailTabAdapter(R.layout.item_match_fragment_view2, mContext);
        baseMatchDetailAdapter.addChildClickViewIds(R.id.iv_collection);
        baseMatchDetailAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                House house = dataDTO.getHouseList().get(position);

                if (view.getId() == R.id.iv_collection) {

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
        MatchDetailHistoryResult.DataDTO.ListDTO bean = (MatchDetailHistoryResult.DataDTO.ListDTO) mData.get(position);
        BaseWebActivity.actionStart(mContext, bean.getDetailUrl(), "");
    }

    @Override
    public void initViewObservable() {

        viewModel.collectionDetailHistoryResultMutableLiveData.observe(this, new Observer<MatchDetailHistoryResult>() {
            @Override
            public void onChanged(MatchDetailHistoryResult matchDetailHistoryResult) {
                for(int i=0;i<matchDetailHistoryResult.getData().getList().size();i++){
                    matchDetailHistoryResult.getData().getList().get(i).setIsCollection(true);
                }
                if(matchDetailHistoryResult.getData().getList().size()>0){
                    updateData(matchDetailHistoryResult.getData().getList());
                }
            }
        });
        viewModel.isCollection.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtils.showLong("收藏成功");
            }
        });

    }

    @Override
    public void reload() {

    }
}