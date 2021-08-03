package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityTextIntputBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.view.CommonAlertDialog;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

/**
 * @ClassName: TextInfoInputActivity
 * @Description: 信息输入
 * @Author: join_lu
 * @CreateDate: 2021/7/30 16:21
 */
public class TextInfoInputActivity extends BaseActivity<ActivityTextIntputBinding, BaseViewModel> {


    private int type;
    private String content;

    public static void startAction(Context context, int type,String content) {
        Intent starter = new Intent(context, TextInfoInputActivity.class);
        starter.putExtra("type",type);
        starter.putExtra("content",content);
        context.startActivity(starter);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_text_intput;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        if(getIntent()!=null){
             type = getIntent().getIntExtra("type", 0);
             content = getIntent().getStringExtra("content");

        }
        if(type==0){
            binding.tvHint.setText("姓名");
            binding.ntb.setNewTitleText("姓名");
            binding.cet.setText(content);
        }else if(type==1){
            binding.tvHint.setText("电话");
            binding.ntb.setNewTitleText("电话");
            binding.rlPhone.setVisibility(View.VISIBLE);
            binding.cet.setText(content);
            binding.tvAseSave.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    CommonAlertDialog commonAlertDialog = new CommonAlertDialog(mContext, "设置密钥？", new CommonAlertDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if(confirmed){

                            }else {


                            }
                            finish();
                        }
                    });
                    commonAlertDialog.show();
                }
            });
            binding.tvSave.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    LiveDataBus.get().with(AppConstans.BusTag.UPDATE+type).postValue(binding.cet.getText().toString());
                    finish();
                }
            });
        }else {
            binding.ntb.setNewTitleText("备注");
            binding.tvHint.setText("备注");
            binding.cet.setText(content);
        }
        binding.ntb.setOnRightTextListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LiveDataBus.get().with(AppConstans.BusTag.UPDATE+type).postValue(binding.cet.getText().toString());
                finish();
            }
        });

    }

}
