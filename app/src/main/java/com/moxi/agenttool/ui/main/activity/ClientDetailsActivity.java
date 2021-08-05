package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.databinding.ActivityClientDetailsBinding;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.bean.ClientDetailsBean;
import com.moxi.agenttool.ui.bean.TagBean;
import com.moxi.agenttool.ui.bean.UserTagBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.utils.ACache;
import com.moxi.agenttool.utils.CommonUtils;
import com.moxi.agenttool.utils.GsonUtils;
import com.moxi.agenttool.view.ItemGroupView;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * @ClassName: ClientDetailsActivity
 * @Description: 客户详细资料
 * @Author: join_lu
 * @CreateDate: 2021/8/4 11:05
 */
public class ClientDetailsActivity extends BaseActivity<ActivityClientDetailsBinding, MainViewModel> {




    private String id;
    private String[] tags = new String[]{"clientType","budget", "type", "area", "feature", "orientation", "floor", "age", "renovation", "purpose","lift","structure"};
    private ArrayList<ItemGroupView> views;
    private ArrayList<String> types;
    private List<UserTagBean.DataDTO> data;
    private boolean isEdit;

    public static void startAction(Context context, String id, boolean isEdit) {
        Intent starter = new Intent(context, ClientDetailsActivity.class);
        starter.putExtra("id",id);
        starter.putExtra("isEdit",isEdit);
        context.startActivity(starter);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_client_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        if(intent!=null){
             id = intent.getStringExtra("id");
             isEdit = intent.getBooleanExtra("isEdit",false);
        }
        binding.ntb.setNewTitleText("详细资料");
        if(isEdit){
            binding.ntb.setRightImagSrc(R.mipmap.ic_personal_edit);
            binding.ntb.setOnRightImagListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
//                    AddClientActivity.startAction(mContext,isEdit);
                }
            });
        }
        initView();
        viewModel.getTagList();
        viewModel.getClientDetails(id);
    }

    private void initView() {
        String json = ACache.get(mContext).getAsString("lift");
        TagBean tagBean = GsonUtils.fromLocalJson(json, TagBean.class);
        Log.e("tagBean",""+tagBean.getList().get(0).getTopicTitle());
        views = new ArrayList<>();
        types = new ArrayList<>();
        views.add(binding.igvType);
        views.add(binding.igvBudget);
        views.add(binding.igvHousetype);
        views.add(binding.igvBuiltArea);
        views.add(binding.igvTrait);
        views.add(binding.igvOrientation);
        views.add(binding.igvFloor);
        views.add(binding.igvAge);
        views.add(binding.igvRenovation);
        views.add(binding.igvPurpose);
        views.add(binding.igvLift);
        views.add(binding.igvStructure);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.userTagBeanMutableLiveData.observe(this, new Observer<UserTagBean>() {
            @Override
            public void onChanged(UserTagBean userTagBean) {
                 data = userTagBean.getData();
            }
        });
        viewModel.clientDetailsBeanMutableLiveData.observe(this, new Observer<ClientDetailsBean>() {
            @Override
            public void onChanged(ClientDetailsBean clientDetailsBean) {
                final ClientDetailsBean.DataDTO.ClientDTO client = clientDetailsBean.getData().getClient();
                ClientDetailsBean.DataDTO.ClientExtDTO clientExt = clientDetailsBean.getData().getClientExt();
                binding.igvLocation.setRigthText(client.getArea());
                binding.tvContent.setText(client.getRemark());
                Glide.with(mContext).load(client.getAvatar()).into(binding.ivCover);
                binding.tvName.setText(client.getName());
                binding.tvPhone.setText(client.getPhone());
                if(!StringUtils.equals(client.getIsprivatephone(),"0")){
                    binding.tvEncryption.setVisibility(View.VISIBLE);
                }else {
                    binding.tvEncryption.setVisibility(View.GONE);
                }
                binding.ivCall.setOnClickListener(new OnNoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        CommonUtils.callPhone(mContext,client.getPhone());
                    }
                });
                for(int i=0;i<data.size();i++){
                    if(StringUtils.equals(data.get(i).getId(),client.getLabelId())){
                        binding.igvTag.setRigthText(data.get(i).getName());
                    }
                }
                types.add(client.getClientType());
                types.add(clientExt.getBudget());
                types.add(clientExt.getHouseType());
                types.add(clientExt.getBuiltArea());
                types.add(clientExt.getFeature());
                types.add(clientExt.getDirection());
                types.add(clientExt.getStorey());
                types.add(clientExt.getAge());
                types.add(clientExt.getFitUp());
                types.add(clientExt.getUses());
                types.add(clientExt.getLift());
                types.add(clientExt.getStructure());
                for(int i=0;i<tags.length;i++){
                        String json = ACache.get(mContext).getAsString(tags[i]);
                        TagBean tagBean = GsonUtils.fromLocalJson(json, TagBean.class);
                        views.get(i).setRigthText(tagBean.getList().get(Integer.parseInt(types.get(i))).getTopicTitle());
                }

            }
        });
    }
}
