package com.moxi.agenttool.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.moxi.agenttool.BR;
import com.moxi.agenttool.R;
import com.moxi.agenttool.contract.AppConstans;
import com.moxi.agenttool.databinding.ActivityAddClientBinding;
import com.moxi.agenttool.livedatas.LiveDataBus;
import com.moxi.agenttool.ui.base.activity.BaseActivity;
import com.moxi.agenttool.ui.bean.ASRresponse;
import com.moxi.agenttool.ui.bean.AddClientBean;
import com.moxi.agenttool.ui.bean.TagBean;
import com.moxi.agenttool.ui.main.viewmodel.MainViewModel;
import com.moxi.agenttool.utils.ACache;
import com.moxi.agenttool.utils.GsonUtils;
import com.moxi.agenttool.view.RecordAlertDialog;
import com.moxi.agenttool.wdiget.OnNoDoubleClickListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.StringUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author feng wen jun
 * @description ????????????
 * @since 2021/2/19 0019
 */
public class AddClientActivity extends BaseActivity<ActivityAddClientBinding, MainViewModel> implements View.OnClickListener, EventListener {


    private String[] tags = new String[]{"budget", "type", "area", "trait", "orientation", "floor", "age", "renovation", "purpose"};
    private String[] newHouseTags = new String[]{"budget", "type", "opening", "Property", "status", "renovation"};
    private String[] newTagsName = new String[]{"??????(??????)", "??????(??????)", "??????", "??????", "??????", "??????"};
    private int type;
    private EventManager asr;
    private ASRresponse asRresponse;
    private String result="";
    private RecordAlertDialog commonAlertDialog;
    //????????????
    CityPickerView mPicker=new CityPickerView();
    private String areaName;
    private String cityName;
    private ArrayList<TagFlowLayout> views;
    private String tagId;
    private boolean isEdit;
    private String mKey;

    public static void startAction(Context context, boolean isEdit) {
        Intent starter = new Intent(context, AddClientActivity.class);
        starter.putExtra("isEdit",isEdit);
        context.startActivity(starter);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_client;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        initView();
        Intent intent = getIntent();

        if(intent!=null){
             isEdit = intent.getBooleanExtra("isEdit", false);
        }
        if(isEdit){
            binding.ntb.setNewTitleText("??????");
        }
        //???????????????iOS???????????????????????????
        mPicker.init(this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveDataBus.get().with(AppConstans.BusTag.UPDATE+2, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    binding.igvRemark.setRigthText(s);
            }
        });
        LiveDataBus.get().with(AppConstans.BusTag.UPDATE+0, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    binding.igvName.setRigthText(s);
            }
        });   LiveDataBus.get().with(AppConstans.BusTag.UPDATE+1, String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    binding.igvPhone.setRigthText(s);
            }
        });
        LiveDataBus.get().with("selectString",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String select) {
                if(select!=null){
                    binding.igvTag.setRigthText(select);
                }

            }
        });
        LiveDataBus.get().with("selectId",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String selectId) {
                if(selectId!=null){
                    tagId=selectId;
                }

            }
        });

        LiveDataBus.get().with(AppConstans.BusTag.KEY,String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String key) {
                mKey=key;
            }
        });
        viewModel.isAdd.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if(!StringUtils.isEmpty(mKey)){
                    SPUtils.getInstance().put("key",mKey);
                }
                ToastUtils.showLong("????????????");
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ??????SDK??????4.2 ??????????????????
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        // ??????SDK??????5.2 ?????????????????????
        // ?????????registerListener?????????????????????????????????????????????
        asr.unregisterListener(this);
    }

    private void initView() {
        //?????????EventManager??????
        asr = EventManagerFactory.create(this, "asr");
        //??????????????????????????????
        asr.registerListener(this); //  EventListener ??? onEvent??????
        binding.igvName.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                type = 0;
                TextInfoInputActivity.startAction(mContext, type,binding.igvName.getRightText());
            }
        });
        binding.igvPhone.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                type = 1;
                TextInfoInputActivity.startAction(mContext, type,binding.igvPhone.getRightText(),mKey);
            }
        });
        binding.igvRemark.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                type = 2;
                TextInfoInputActivity.startAction(mContext, type,binding.igvRemark.getRightText());
            }
        });
        binding.igvTag.setOnClickListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                AddClientTagActivity.startAction(mContext,binding.igvTag.getRightText());
            }
        });
        binding.igvLocation.setOnClickListener(this);
        binding.ntb.setOnRightTextListener(new OnNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                AddClientBean addClientBean = new AddClientBean();
                addClientBean.setName(binding.igvName.getRightText());
                addClientBean.setCity(cityName);
                addClientBean.setArea(areaName);
                addClientBean.setPhone(binding.igvPhone.getRightText());
                
                addClientBean.setRemark(binding.igvRemark.getRightText());
                addClientBean.setLabelId(tagId);
                addClientBean.setAvatar("http://n.sinaimg.cn/photo/700/w1000h500/20210603/57a6-kracxep9657657.png");
                for(int i=0;i<newHouseTags.length;i++){
                    if(newHouseTags[i].equals("budget")){
                        Set<Integer> selectedList = views.get(i).getSelectedList();
                        for (Integer integer : selectedList) {
                            addClientBean.setBudget(integer+1+"");
                        }

                    }
                    if(newHouseTags[i].equals("type")){
                        Set<Integer> selectedList = views.get(i).getSelectedList();
                        for (Integer integer : selectedList) {
                            addClientBean.setHouseType(integer+1+"");
                        }

                    }
                }
                addClientBean.setClientType("2");
                if(StringUtils.isEmpty(addClientBean.getName())||StringUtils.isEmpty(addClientBean.getArea())||StringUtils.isEmpty(addClientBean.getCity())
                ||StringUtils.isEmpty(addClientBean.getAvatar())||StringUtils.isEmpty(addClientBean.getPhone())||StringUtils.isEmpty(addClientBean.getHouseType())||
                        StringUtils.isEmpty(addClientBean.getBudget())){
                    ToastUtils.showLong("???????????????????????????");
                }else {
                    viewModel.addClient(addClientBean);

                }

            }
        });
        binding.ivDef01.setOnClickListener(this);
        binding.ivDef02.setOnClickListener(this);
        binding.ivDef03.setOnClickListener(this);
        binding.ivVoice.setOnClickListener(this);
        binding.tvOther.setOnClickListener(this);
        binding.tvTotalPrice.setOnClickListener(this);
        binding.tvUnitPrice.setOnClickListener(this);
        binding.tvSb.setOnClickListener(this);
         views = new ArrayList<>();
        views.add(binding.tflBudget);
        views.add(binding.tflType);
        views.add(binding.tflArea);
        views.add(binding.tflTrait);
        views.add(binding.tflOrientation);
        views.add(binding.tflFloor);
        views.add(binding.tflAge);
        views.add(binding.tflRenovation);
        views.add(binding.tflPurpose);
        ArrayList<TextView> titles = new ArrayList<>();
        titles.add(binding.tvBudget);
        titles.add(binding.tvType);
        titles.add(binding.tvArea);
        titles.add(binding.tvTrait);
        titles.add(binding.tvOrientation);
        titles.add(binding.tvFloor);
        titles.add(binding.tvAge);
        titles.add(binding.tvRenovation);
        titles.add(binding.tvPurpose);
        for(int k=0; k<titles.size();k++){
            titles.get(k).setVisibility(View.GONE);
        }
        for (int i = 0; i < newHouseTags.length; i++) {
            changeData(newHouseTags[i],  views.get(i));
            titles.get(i).setText(newTagsName[i]);
            titles.get(i).setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_def01:
                binding.ivSelct1.setVisibility(View.VISIBLE);
                binding.ivSelct2.setVisibility(View.GONE);
                binding.ivSelct3.setVisibility(View.GONE);
                break;
            case R.id.iv_def02:
                binding.ivSelct1.setVisibility(View.GONE);
                binding.ivSelct2.setVisibility(View.VISIBLE);
                binding.ivSelct3.setVisibility(View.GONE);

                break;
            case R.id.iv_def03:
                binding.ivSelct1.setVisibility(View.GONE);
                binding.ivSelct2.setVisibility(View.GONE);
                binding.ivSelct3.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_voice:
                initPermission();
                Map<String,Object> params = new LinkedHashMap<>();
                String event = SpeechConstant.ASR_START;
                params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,false);
                params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT,0);
                params.put(SpeechConstant.DISABLE_PUNCTUATION,true);
                String json = new JSONObject(params).toString();
                asr.send(event, json, null, 0, 0);
                commonAlertDialog = new RecordAlertDialog(mContext, new RecordAlertDialog.AlertDialogUser() {

                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            binding.tvDiscern.setText(result);
                        }else {
                            result="";
                        }

                        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
                    }
                });
                commonAlertDialog.show();
                break;
            case R.id.tv_other:
                binding.tvOther.setVisibility(View.GONE);
                binding.clOther.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_sb:
                result=binding.tvDiscern.getText().toString();
                if (result != null && result.length() > 0) {
                Pattern p = Pattern.compile("1[345678]\\d{9}");
                Matcher m = p.matcher(result);
                if (m.find()) {
                    binding.igvPhone.setRigthText(m.group());
                }
                    int indexName = result.indexOf("??????");
                    int indexPhone = result.indexOf("??????");
                    int indexLocation = result.indexOf("??????");
                    int indexRemark = result.indexOf("??????");
                    Log.e("tvDiscern", "" + indexName + "    " + indexPhone);
                    if (indexPhone > 0 && indexName >= 0) {
                        String name = result.substring(indexName + 2, indexPhone);
                        Log.e("tvDiscern1", "" + name);
                        binding.igvName.setRigthText(name);
                    }
                    if (indexLocation > 0 && indexRemark > 0) {
                        String location = result.substring(indexLocation + 2, indexRemark);
                        binding.igvLocation.setRigthText(location);
                    }else if(indexLocation>0){
                        String location = result.substring(indexLocation+2);
                        binding.igvLocation.setRigthText(location);
                    }
                    if(indexRemark>0){
                        String remark = result.substring(indexRemark+2);
                        binding.igvRemark.setRigthText(remark);
                    }
                }
                break;
            case R.id.tv_unit_price:

                changeData("budget_unit", binding.tflBudget);
                binding.tvUnitPrice.setTextColor(Color.parseColor("#FF7730"));
                binding.tvTotalPrice.setTextColor(Color.parseColor("#999999"));
                binding.tvMaxPrice.setText("????????????");
                binding.tvMinPrice.setText("????????????");
                break;
            case R.id.tv_total_price:
                changeData("budget", binding.tflBudget);
                binding.tvTotalPrice.setTextColor(Color.parseColor("#FF7730"));
                binding.tvUnitPrice.setTextColor(Color.parseColor("#999999"));
                binding.tvMaxPrice.setText("????????????");
                binding.tvMinPrice.setText("????????????");
                break;
            case R.id.igv_location:
                JDCityPicker cityPicker = new JDCityPicker();
                JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();

                jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
                cityPicker.init(this);
                cityPicker.setConfig(jdCityConfig);
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        areaName = district.getName();
                        if(province.getName().equals("?????????")||province.getName().equals("?????????")||province.getName().equals("?????????")||province.getName().equals("?????????")){
                            cityName = province.getName();
                        }else {
                            cityName = city.getName();
                        }
                        binding.igvLocation.setRigthText(cityName+areaName);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                cityPicker.showCityPicker();
                break;
        }
    }

    private void changeData(String type, TagFlowLayout p2) {
        String json = ACache.get(this).getAsString(type);
        TagBean tagBean = GsonUtils.fromLocalJson(json, TagBean.class);
        if(tagBean!=null){
            List<TagBean.ListBean> list = tagBean.getList();
            TagAdapter mAdapter = new TagAdapter<TagBean.ListBean>(list) {
                @Override
                public View getView(FlowLayout parent, int position, TagBean.ListBean listBean) {
                    final LayoutInflater mInflater = LayoutInflater.from(AddClientActivity.this);
                    TextView tv = (TextView) mInflater.inflate(R.layout.item_tag_bg_other,
                            parent, false);
                    tv.setText(listBean.getTopicTitle());
                    return tv;
                }
            };
            TagFlowLayout view = p2;
            view.setAdapter(mAdapter);
        }
    }

    /**
     * android 6.0 ??????????????????????????????
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // ?????????????????????????????????.
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
        String nativeLibraryDir = getApplicationInfo().nativeLibraryDir;
        Log.e("nativeLibraryDir", "" + nativeLibraryDir);
    }


    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // ?????????????????????????????????
            Log.i("onEvent12", params);
            if (params == null || params.isEmpty()) {
                return;
            }
            if (params.contains("\"final_result\"")) {
                // ??????????????????????????????
                Log.i("ars.event", params);

                Gson gson = new Gson();
                asRresponse = gson.fromJson(params, ASRresponse.class);//?????????????????????bean

                if (asRresponse == null) return;
                //?????????????????????Best_result??????????????????????????????????????????????????????????????????????????????
                result += asRresponse.getBest_result();

                commonAlertDialog.setTvHint(result);
            }
        }

    }
}
