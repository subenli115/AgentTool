package com.moxi.agenttool.ui.bean;

/**
 * @description: 地区对象
 * @author: YangYong
 * @sence: 2021/1/21
 * @version: 2.0
 */
public class AreaBean {

    /**
     * areaCode	代码	string
     * areaLevel	级别 1-省 2-市 3-区 4-街道/镇	string
     * areaName	名称	string
     * backImg	背景图	string
     * description	描述	string
     * shortName	简称	string
     * supCode	上级代码	string
     */

    private String areaCode;
    private String areaLevel;
    private String areaName;
    private String backImg;
    private String description;
    private String shortName;
    private String supCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSupCode() {
        return supCode;
    }

    public void setSupCode(String supCode) {
        this.supCode = supCode;
    }
}
