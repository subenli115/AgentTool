package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityBaseCommonBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.adapter.ClientTagAdapter;
import com.moxi.agenttool.ui.base.activity.CommHttpActivity;
import com.moxi.agenttool.ui.bean.UserTagBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.Arrays;
import java.util.List;

import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * @ClassName: AddClientTagActitivy
 * @Description: 用户标签
 * @Author: join_lu
 * @CreateDate: 2021/8/3 17:38
 */
public class AddClientTagActivity extends CommHttpActivity<ActivityBaseCommonBinding, MainViewModel> {

    private String selectString;
    private ClientTagAdapter clientTagAdapter;

    public static void startAction(Context context, String rightText) {
        Intent starter = new Intent(context, AddClientTagActivity.class);
        starter.putExtra("selectString", rightText);
        context.startActivity(starter);
    }


    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent() != null) {
            selectString = getIntent().getStringExtra("selectString");
        }
        baseBinding.ntb.setNewTitleText("标签");
        baseBinding.ntb.setRightTitle("保存");
        baseBinding.rlMain.setBackgroundColor(Color.parseColor("#F5F5F5"));
        baseBinding.recyclerview.setBackgroundColor(Color.parseColor("#F5F5F5"));
        binding.ntb.setLineVisiable(true);
        baseBinding.ntb.setOnRightTextListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LiveDataBus.get().with("selectString").postValue(clientTagAdapter.getProfession());
                LiveDataBus.get().with("selectId").postValue(clientTagAdapter.getSelectStringId());
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        viewModel.getTagList();
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        clientTagAdapter = new ClientTagAdapter(R.layout.item_tag_client_view, mContext);
        return clientTagAdapter;
    }

    @Override
    public ViewBinding getHeader() {
        return null;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.userTagBeanMutableLiveData.observe(this, new Observer<UserTagBean>() {
            @Override
            public void onChanged(UserTagBean userTagBean) {
                clientTagAdapter.setSelectString(selectString);
                List<UserTagBean.DataDTO> data = userTagBean.getData();

                if (!StringUtils.isEmpty(selectString)) {
                    List<String> strings = Arrays.asList(selectString.split(","));
                    clientTagAdapter.setSelectList(strings);
                }
                if (clientTagAdapter.selectList.size() > 0) {
                    for (int i = 0; i < userTagBean.getData().size(); i++) {
                        UserTagBean.DataDTO bean = data.get(i);
                        bean.setSelect(clientTagAdapter.selectList.contains(bean.getName()));
                    }
                }
                updateData(data);

            }

        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
