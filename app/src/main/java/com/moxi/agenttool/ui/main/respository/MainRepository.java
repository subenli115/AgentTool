package com.moxi.agenttool.ui.main.respository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.moxi.agenttool.entity.StringDataBean;
import com.moxi.agenttool.global.ApiKey;
import com.moxi.agenttool.ui.base.model.BaseSingleLiveEvent;
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
        HttpManager.get(ApiKey.CLIENT_QUERY_MATCH)
                .accessToken()
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String result) {
                        ImportantBean bean = GsonUtils.fromLocalJson(result, ImportantBean.class);
                        if (bean.getCode().equals("200")) {
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
                    }

                    @Override
                    public void onSuccess(String result) {
                        FilterHouseResult bean = GsonUtils.fromLocalJson(result, FilterHouseResult.class);
                        List<FilterHouseResult.DataDTO> data = bean.getData();
                        ArrayList<House> tempList = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            tempList.addAll(data.get(i).getHouseList());
                        }
                        List<FilterHouseResult.DataDTO> data1 = bean.getData();
                        Log.e("data1", "" + data1.size());
                        filterHouseResultMutableLiveData.setValue(data1);
                        integerMutableLiveData.setValue(tempList.size());

                    }
                });
    }


    public void getCollectionHouseList(String pageNum, String clientId, final MutableLiveData<MatchDetailHistoryResult> collectionHoseBeanMutableLiveData) {
        HttpManager.get(ApiKey.HOUSE_COLLOECTION_QUERY)
                .accessToken()
                .params("pageNum", pageNum)
                .params("pageSize", "10")
                .params("clientId", clientId)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        MatchDetailHistoryResult bean = GsonUtils.fromLocalJson(result, MatchDetailHistoryResult.class);
                        if (bean.getCode().equals("200")) {
                            collectionHoseBeanMutableLiveData.setValue(bean);
                        } else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void addClient(AddClientBean bean, final BaseSingleLiveEvent isAdd) {
        HttpManager.post(ApiKey.CLIENT_INSERT)
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
                        if (bean.getCode().equals("200")) {
                            isAdd.call();
                        } else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }


    public void getClientDetails(String id, final MutableLiveData<ClientDetailsBean> clientDetailsBeanMutableLiveData) {
        HttpManager.get(ApiKey.CLIENT_QUERY_DETAIL)
                .accessToken()
                .params("id", id)
                .params("containExt", "true")
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        ClientDetailsBean bean = GsonUtils.fromLocalJson(result, ClientDetailsBean.class);
                        if (bean.getCode().equals("200")) {
                            clientDetailsBeanMutableLiveData.setValue(bean);
                        } else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void getTagList(final MutableLiveData<UserTagBean> userTagBeanMutableLiveData) {
        HttpManager.get(ApiKey.USER_LABEL_QUERY)
                .accessToken()
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        UserTagBean bean = GsonUtils.fromLocalJson(result, UserTagBean.class);
                        if (bean.getCode().equals("200")) {
                            userTagBeanMutableLiveData.setValue(bean);
                        } else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void removeClient(List ids, final BaseSingleLiveEvent isDelete) {
        HttpManager.post(ApiKey.CLIENT_DELETE)
                .accessToken()
                .upJson(GsonUtils.toJson(ids))
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if (bean.getCode().equals("200")) {
                            isDelete.call();
                        } else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }


    public void removeClientMatch(String id, final BaseSingleLiveEvent isDelete) {
        HttpManager.post(ApiKey.CLIENT_MATCH_REMOVE)
                .accessToken()
                .params("id", id)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if (bean.getCode().equals("200")) {
                            isDelete.call();
                        } else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }

    public void addClientMatch(String id, final BaseSingleLiveEvent isAdd) {
        HttpManager.post(ApiKey.CLIENT_MATCH)
                .accessToken()
                .params("id", id)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        StringDataBean bean = GsonUtils.fromLocalJson(result, StringDataBean.class);
                        if (bean.getCode().equals("200")) {
                            isAdd.call();
                        } else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }

    public void getHistoryHouseList(String pageNum, String clientId, final MutableLiveData<MatchDetailHistoryResult> collectionHoseBeanMutableLiveData) {
        HttpManager.get(ApiKey.SEARCH_HOUSE_HISTORY)
                .accessToken()
                .params("pageSize", "10")
                .params("pageNum", pageNum)
                .params("clientId", clientId)
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        MatchDetailHistoryResult bean = GsonUtils.fromLocalJson(result, MatchDetailHistoryResult.class);
                        if (bean.getCode().equals("200")) {
                            collectionHoseBeanMutableLiveData.setValue(bean);
                        } else {
                            ToastUtils.showLong(bean.getMsg());
                        }

                    }
                });
    }

    public void getClientList(String pageNum, ClientQuestionBean clientQuestionBean, final MutableLiveData<ClientListBean> clientListBeanMutableLiveData) {
        HttpManager.get(ApiKey.CILENT_QUERY)
                .accessToken()
                .params("pageNum", pageNum)
                .params("pageSize", "10")
                .cacheKey(this.getClass().getSimpleName())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showLong(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String result) {
                        ClientListBean bean = GsonUtils.fromLocalJson(result, ClientListBean.class);
                        if (bean.getCode().equals("200")) {
                            clientListBeanMutableLiveData.setValue(bean);
                        } else {
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
                        if (bean.getCode().equals("200")) {
                            isCollection.setValue(result);
                        } else {
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
                        if (bean.getCode().equals("200")) {
                            isCollection.setValue(result);
                        } else {
                            ToastUtils.showLong(bean.getMessage());
                        }

                    }
                });
    }


    public void getHtmlCode(String url, final MutableLiveData<String> htmlCodeLiveEvent) {
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
