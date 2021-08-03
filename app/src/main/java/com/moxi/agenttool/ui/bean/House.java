package com.moxi.agenttool.ui.bean;

import java.io.Serializable;

/**
 * @ClassName: House
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/27 9:38
 */
public class House implements Serializable {
    // 房屋面积
    String houseArea;
    // 详情地址
    String detailUrl;
    // 图片地址
    String imgUrl;
    // 楼盘名
    String name;
    // 楼盘地址
    String location;
    String priceFirst;
    // 楼盘总价
    String priceSecond;
    // 楼盘户型
    String type;
    // 房屋面积
    String area;
    boolean isCollection;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPriceFirst() {
        return priceFirst;
    }

    public void setPriceFirst(String priceFirst) {
        this.priceFirst = priceFirst;
    }

    public String getPriceSecond() {
        return priceSecond;
    }

    public void setPriceSecond(String priceSecond) {
        this.priceSecond = priceSecond;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "House{" +
                "houseArea='" + houseArea + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", priceFirst='" + priceFirst + '\'' +
                ", priceSecond='" + priceSecond + '\'' +
                ", type='" + type + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
