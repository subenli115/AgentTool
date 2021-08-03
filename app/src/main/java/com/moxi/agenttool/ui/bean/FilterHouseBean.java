package com.moxi.agenttool.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: FilterHouseBean
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/27 9:54
 */
public class FilterHouseBean implements Serializable {


    private String area;
    private String avatar;
    private String city;
    private String clientId;
    private String clientUpdateTime;
    private String createTime;
    private List<House> houseList;
    private String isprivatephone;
    private String name;
    private String phone;
    private String remark;
    private String sex;
    private String updateTime;



    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

    public String getIsprivatephone() {
        return isprivatephone;
    }

    public void setIsprivatephone(String isprivatephone) {
        this.isprivatephone = isprivatephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
