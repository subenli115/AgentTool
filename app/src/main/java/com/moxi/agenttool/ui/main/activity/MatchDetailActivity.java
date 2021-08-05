package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityMatchDetailBinding;
import com.moxi.agenttool.ui.adapter.MatchDetailAdapter;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.main.fragment.CollectionHouseListFragment;
import com.moxi.agenttool.ui.main.fragment.FindContactsRecommendFragment;
import com.moxi.agenttool.ui.main.fragment.HistoryHouseListFragment;
import com.moxi.agenttool.utils.ArrayUtils;
import com.moxi.agenttool.wdiget.HXLinePagerIndicator;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.io.Serializable;
import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * @author feng wen jun
 * @description 匹配详情
 * @since 2021/2/19 0019
 */
public class MatchDetailActivity extends BaseActivity<ActivityMatchDetailBinding, BaseViewModel> {


    private ArrayList<Fragment> mFragments;
    private MatchDetailAdapter adapter;
    private String[] strings;
    private FilterHouseResult.DataDTO dataDTO;

    public FilterHouseResult.DataDTO getDataDTO() {
        return dataDTO;
    }


    public static void startAction(Context context, FilterHouseResult.DataDTO dataDTO) {
        Intent starter = new Intent(context, MatchDetailActivity.class);
        starter.putExtra("result", (Serializable) dataDTO);
        context.startActivity(starter);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_match_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent() != null) {
            dataDTO = (FilterHouseResult.DataDTO) getIntent().getSerializableExtra("result");
        }

        initView();
        mFragments = new ArrayList<>();
        Bundle args = new Bundle();
        args.putSerializable("data", (Serializable) dataDTO.getHouseList());
        FindContactsRecommendFragment fragment = new FindContactsRecommendFragment();
        fragment.setArguments(args);
        mFragments.add(fragment);
        mFragments.add(new CollectionHouseListFragment());
        mFragments.add(new HistoryHouseListFragment());
        binding.ntb.setLineVisiable(true);
        strings = ArrayUtils.getArray(mContext, R.array.base_match_tabs);
        adapter = new MatchDetailAdapter(getSupportFragmentManager(), mContext, mFragments, false);
        binding.ivCall.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
//                CommonUtils.callPhone(mContext,dataDTO.getPhone());
            }
        });
        binding.clOther.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ClientDetailsActivity.startAction(mContext,dataDTO.getClientId(),false);
            }
        });
        binding.vp.setAdapter(adapter);
        binding.vp.setOffscreenPageLimit(3);
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return strings == null ? 0 : strings.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(mContext, R.color.textColor_999));
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(mContext, R.color.textColor_333));
                colorTransitionPagerTitleView.setText(strings[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.vp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                HXLinePagerIndicator indicator = new HXLinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 32));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2.75));

                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));

                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, binding.vp);
    }

    private void initView() {
        if(dataDTO!=null){
            binding.tvName.setText(dataDTO.getName());
            binding.tvPhone.setText(dataDTO.getPhone());
            binding.tvNum.setText("匹配" + dataDTO.getHouseList().size() + "套");
            binding.tvMark.setText(dataDTO.getRemark());
            binding.tvName.setText(dataDTO.getName());

            if (!StringUtils.equals("0",dataDTO.getIsprivatephone())) {
                binding.tvEncryption.setVisibility(View.VISIBLE);
            }else {
                binding.tvEncryption.setVisibility(View.GONE);
            }
            Glide.with(mContext).load(dataDTO.getAvatar()).into(binding.ivCover);
        }
    }
}
