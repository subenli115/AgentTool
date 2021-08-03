package com.moxi.agenttool.ui.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.AddClientBean;
import com.moxi.agenttool.ui.bean.AddCollectionBean;
import com.moxi.agenttool.ui.bean.FilterHouseBean;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.ui.main.respository.MainRepository;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2017/7/17.
 */

public class MainViewModel extends BaseViewModel {
    //使用LiveData
    public SingleLiveEvent<String> loadUrlEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<String> isCollection = new SingleLiveEvent<>();
    public MutableLiveData<String> htmlCodeLiveEvent = new MutableLiveData<>();
    public SingleLiveEvent<String> isAdd = new SingleLiveEvent<>();
    public MutableLiveData<List<ImportantBean.DataDTO>> importTrantMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<FilterHouseResult.DataDTO>> filterHouseResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MatchDetailHistoryResult> matchDetailHistoryResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MatchDetailHistoryResult> collectionDetailHistoryResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> filterSize = new MutableLiveData<>();
    private MainRepository mainRepository = MainRepository.getInstance();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取重点客户
     */
    public void getKeyCustomers() {
        mainRepository.getKeyCustomers(importTrantMutableLiveData);

    }

    /**
     * 过滤客户房源
     * @param list
     */
    public void getFilterClientHouse(List<FilterHouseBean> list) {
        mainRepository.getFilterClientHouse(filterSize,list,filterHouseResultMutableLiveData);

    }

    /**
     * 添加客户
     */
    public void addClient(AddClientBean bean) {
        mainRepository.addClient(bean,isAdd);

    }



    /**
     * 获取收藏的房源
     */
    public void getCollectionHouseList(String page,String clientId) {
        mainRepository.getCollectionHouseList(page,clientId,collectionDetailHistoryResultMutableLiveData);

    }

    /**
     * 收藏房源
     */
    public void postCollectionHouse(House house, String clientId,String upTime) {
        AddCollectionBean addCollectionBean = new AddCollectionBean();
        List<House> houseList=new ArrayList<>();
        houseList.add(house);
        addCollectionBean.setHouseList(houseList);
        addCollectionBean.setClientUpdateTime(upTime);
        addCollectionBean.setClientId(clientId);
        mainRepository.postCollectionHouse(addCollectionBean,isCollection);

    }


    /**
     * 新增历史房源
     */
    public void addHistoryHouse(House house, String clientId,String upTime) {
        AddCollectionBean addCollectionBean = new AddCollectionBean();
        List<House> houseList=new ArrayList<>();
        houseList.add(house);
        addCollectionBean.setHouseList(houseList);
        addCollectionBean.setClientUpdateTime(upTime);
        addCollectionBean.setClientId(clientId);
        mainRepository.addHistoryHouse(addCollectionBean,isCollection);

    }





    /**
     * 获取历史房源
     */
    public void getHistoryHouse(String page,String clientId) {
        mainRepository.getHistoryHouseList(page,clientId,matchDetailHistoryResultMutableLiveData);

    }


    /**
     * 获取html代码
     */
    public void getHtmlCode(String url) {
        mainRepository.getHtmlCode(url,htmlCodeLiveEvent);

    }
}
