package com.moxi.agenttool.ui.main.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.ui.base.model.BaseSingleLiveEvent;
import com.moxi.agenttool.ui.base.model.BaseViewModel;
import com.moxi.agenttool.ui.bean.AddClientBean;
import com.moxi.agenttool.ui.bean.AddCollectionBean;
import com.moxi.agenttool.ui.bean.ClientDetailsBean;
import com.moxi.agenttool.ui.bean.ClientListBean;
import com.moxi.agenttool.ui.bean.ClientQuestionBean;
import com.moxi.agenttool.ui.bean.FilterHouseBean;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.ui.bean.UserTagBean;
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
    public BaseSingleLiveEvent isAdd = new BaseSingleLiveEvent<>();
    public BaseSingleLiveEvent isDelete = new BaseSingleLiveEvent<>();
    public MutableLiveData<List<ImportantBean.DataDTO>> importTrantMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<UserTagBean> userTagBeanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<FilterHouseResult.DataDTO>> filterHouseResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ClientListBean> clientListBeanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<ClientDetailsBean> clientDetailsBeanMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MatchDetailHistoryResult> matchDetailHistoryResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MatchDetailHistoryResult> collectionDetailHistoryResultMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> filterSize = new MutableLiveData<>();
    private MainRepository mainRepository = MainRepository.getInstance();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取匹配
     */
    public void getKeyCustomers() {
        mainRepository.getKeyCustomers(importTrantMutableLiveData);

    }

    /**
     * 过滤客户房源
     * @param list
     */
    public void getFilterClientHouse(List<FilterHouseBean> list) {
        Log.e("",""+list);
        mainRepository.getFilterClientHouse(filterSize,list,filterHouseResultMutableLiveData);

    }

    /**
     * 添加客户
     */
    public void addClient(AddClientBean bean) {
        mainRepository.addClient(bean,isAdd);

    }


    /**
     * 获取标签列表
     */
    public void getTagList() {
        mainRepository.getTagList(userTagBeanMutableLiveData);

    }

    /**
     * 添加匹配中心
     */
    public void addClientMatch(String id) {
        mainRepository.addClientMatch(id,isAdd);

    }

    /**
     * 移除匹配中心
     */
    public void removeClientMatch(String id) {
        mainRepository.removeClientMatch(id,isDelete);

    }


    /**
     * 删除客户
     */
    public void removeClient(String id) {
        ArrayList<String> ids = new ArrayList<>();
        ids.add(id);
        mainRepository.removeClient(ids,isDelete);

    }

    /**
     * 获取客户资料
     */
    public void getClientDetails(String id ) {
        mainRepository.getClientDetails(id,clientDetailsBeanMutableLiveData);

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
    public void getClientList(String page, ClientQuestionBean clientQuestionBean) {
        mainRepository.getClientList(page,clientQuestionBean,clientListBeanMutableLiveData);

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
