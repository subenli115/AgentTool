package com.moxi.agenttool.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityTextIntputBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.utils.AESCipher;
import com.moxi.agenttool.view.CommonAlertDialog;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;

import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @ClassName: TextInfoInputActivity
 * @Description: 信息输入
 * @Author: join_lu
 * @CreateDate: 2021/7/30 16:21
 */
public class TextInfoInputActivity extends BaseActivity<ActivityTextIntputBinding, BaseViewModel> {


    private int type;
    private String content;
    private String AesPwd;
    private String key;

    public static void startAction(Context context, int type, String content) {
        Intent starter = new Intent(context, TextInfoInputActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("content", content);
        context.startActivity(starter);
    }

    public static void startAction(Context context, int type, String content,String key) {
        Intent starter = new Intent(context, TextInfoInputActivity.class);
        starter.putExtra("type", type);
        starter.putExtra("key", key);
        starter.putExtra("content", content);
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
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
            content = getIntent().getStringExtra("content");
            key = getIntent().getStringExtra("key");
        }

        if (type == 0) {
            binding.tvHint.setText("姓名");
            binding.ntb.setNewTitleText("姓名");
            binding.cet.setText(content);
        } else if (type == 1) {
            Log.e("SPUtils",key+"");

            if(StringUtils.isEmpty(key)){
                binding.rlPhone.setVisibility(View.VISIBLE);
            }else {
                binding.rlAse.setVisibility(View.VISIBLE);
            }
            binding.tvHint.setText("电话");
            binding.ntb.setNewTitleText("电话");
            binding.cet.setText(content);
            binding.tvAseSave.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (StringUtils.isEmpty(binding.cet.getText().toString())) {
                        ToastUtils.showLong("手机号不能为空");
                        return;
                    }
                    CommonAlertDialog commonAlertDialog = new CommonAlertDialog(mContext, "设置密钥？", new CommonAlertDialog.AlertDialogUser() {

                        @Override
                        public void onResult(boolean confirmed, String result) {
                            key = result;
                            try {
                                String phone = binding.cet.getText().toString();
                                String key = AESCipher.md5Encode(result).substring(0, 16);
                                String aesPwd = AESCipher.aesEncryptString(phone, key);
                                binding.cet.setText(aesPwd);
                                binding.rlPhone.setVisibility(View.GONE);
                                binding.rlAse.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    commonAlertDialog.show();
                }
            });
            binding.tvAseCancel.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    String md5kdy = null;
                    try {
                        md5kdy = AESCipher.md5Encode(key).substring(0, 16);
                        String result = AESCipher.aesDecryptString(binding.cet.getText().toString(), md5kdy);
                        binding.cet.setText(result);
                        binding.rlPhone.setVisibility(View.VISIBLE);
                        binding.rlAse.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            binding.tvSave.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    LiveDataBus.get().with(AppConstans.BusTag.UPDATE + type).postValue(binding.cet.getText().toString());
                    finish();
                }
            });
        } else {
            binding.ntb.setNewTitleText("备注");
            binding.tvHint.setText("备注");
            binding.cet.setText(content);
        }
        binding.ntb.setOnRightTextListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                LiveDataBus.get().with(AppConstans.BusTag.KEY).postValue(key);
                LiveDataBus.get().with(AppConstans.BusTag.UPDATE + type).postValue(binding.cet.getText().toString());
                finish();
            }
        });

    }

}
