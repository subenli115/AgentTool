package com.moxi.agenttool.ui.bean;

import java.util.List;

/**
 * @ClassName: AddCollectionBean
 * @Description: 添加收藏实体
 * @Author: join_lu
 * @CreateDate: 2021/7/28 14:19
 */
public class AddCollectionBean {


    private String clientId;
    private String clientUpdateTime;
    private List<House> houseList;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientUpdateTime() {
        return clientUpdateTime;
    }

    public void setClientUpdateTime(String clientUpdateTime) {
        this.clientUpdateTime = clientUpdateTime;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

}
