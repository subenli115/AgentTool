package com.moxi.agenttool.ui.main.respository;

import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.entity.StringDataBean;
import com.moxi.agenttool.global.ApiKey;
import com.moxi.agenttool.ui.bean.AddClientBean;
import com.moxi.agenttool.ui.bean.AddCollectionBean;
import com.moxi.agenttool.ui.bean.FilterHouseBean;
import com.moxi.agenttool.ui.bean.FilterHouseResult;
import com.moxi.agenttool.ui.bean.House;
import com.moxi.agenttool.ui.bean.ImportantBean;
import com.moxi.agenttool.ui.bean.MatchDetailHistoryResult;
import com.moxi.agenttool.utils.GsonUtils;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.HttpManager;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @ClassName: MainRepository
 * @Description: 主页网络仓库
 * @Author: join_lu
 * @CreateDate: 2021/7/23 14:27
 */
public class MainRepository {

    private volatile static MainRepository instance = null;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (instance == null) {
            synchronized (MainRepository.class) {
                if (instance == null) {
                    instance = new MainRepository();
                }
            }
        }
        return instance;
    }


    public void getKeyCustomers(final MutableLiveData<List<ImportantBean.DataDTO>> importTrantMutableLiveData) {
        HttpManager.get(ApiKey.CILENT_QUERY_IMPORTANT)
                .accessToken()
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        ImportantBean bean = GsonUtils.fromLocalJson(result, ImportantBean.class);
                        if(bean.getCode().equals("200")){
                            importTrantMutableLiveData.setValue(bean.getData());
                        }

                    }
                });
    }
    public void getFilterClientHouse(final MutableLiveData<Integer> integerMutableLiveData, List<FilterHouseBean> list, final MutableLiveData<List<FilterHouseResult.DataDTO>> filterHouseResultMutableLiveData) {
        HttpManager.post(ApiKey.SEARCH_FILTER_CLIENT_HOUSE_HISTORY)
                .accessToken()
                .upJson(GsonUtils.toJson(list))
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        FilterHouseResult bean = GsonUtils.fromLocalJson(result, FilterHouseResult.class);
                        List<FilterHouseResult.DataDTO> data = bean.getData();
                        ArrayList<House> tempList = new ArrayList<>();
                        for(int i=0;i<data.size();i++){
                            tempList.addAll(data.get(i).getHouseList());
                        }
                        if(bean.getData()!=null&&bean.getData().size()>0){
                            List<FilterHouseResult.DataDTO> data1 = bean.getData();

                            filterHouseResultMutableLiveData.setValue(data1);
                            integerMutableLiveData.setValue(tempList.size());
                        }

                    }
                });
    }



    public void getCollectionHouseList(String pageNum,String clientId,final MutableLiveData<MatchDetailHistoryResult> collectionHoseBeanMutableLiveData) {
        HttpManager.get(ApiKey.HOUSE_COLLOECTION_QUERY)
                .accessToken()
                .params("pageNum",pageNum)
                .params("clientId",clientId)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        MatchDetailHistoryResult bean = GsonUtils.fromLocalJson(result, MatchDetailHistoryResult.class);
                        if(bean.getCode().equals("200")){
                            collectionHoseBeanMutableLiveData.setValue(bean);
                        }else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void addClient(AddClientBean bean, final SingleLiveEvent<String> isAdd) {
        HttpManager.post(ApiKey.HOUSE_COLLOECTION_QUERY)
                .accessToken()
                .upJson(GsonUtils.toJson(bean))
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if(bean.getCode().equals("200")){
                            isAdd.call();
                        }else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }


    public void getHistoryHouseList(String pageNum,String clientId,final MutableLiveData<MatchDetailHistoryResult> collectionHoseBeanMutableLiveData) {
        HttpManager.get(ApiKey.SEARCH_HOUSE_HISTORY)
                .accessToken()
                .params("pageNum",pageNum)
                .params("clientId",clientId)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        MatchDetailHistoryResult bean = GsonUtils.fromLocalJson(result, MatchDetailHistoryResult.class);
                        if(bean.getCode().equals("200")){
                            collectionHoseBeanMutableLiveData.setValue(bean);
                        }else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void addHistoryHouse(AddCollectionBean dto, final SingleLiveEvent<String> isCollection) {
        HttpManager.post(ApiKey.HOUSE_HISTORY_INSERT)
                .accessToken()
                .upJson(GsonUtils.toJson(dto))
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if(bean.getCode().equals("200")){
                            isCollection.setValue(result);
                        }else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }

    public void postCollectionHouse(AddCollectionBean dto, final SingleLiveEvent<String> isCollection) {
        HttpManager.post(ApiKey.HOUSE_COLLOECTION_INSERT)
                .accessToken()
                .upJson(GsonUtils.toJson(dto))
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if(bean.getCode().equals("200")){
                            isCollection.setValue(result);
                        }else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }


    public void getHtmlCode(String url,final MutableLiveData<String> htmlCodeLiveEvent) {
        HttpManager.get("")
                .accessToken()
                .baseUrl(url)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        htmlCodeLiveEvent.setValue(result);

                    }
                });
    }

}
