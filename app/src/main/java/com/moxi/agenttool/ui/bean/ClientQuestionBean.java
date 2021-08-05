package com.moxi.agenttool.ui.bean;

/**
 * @ClassName: ClientQuestionBean
 * @Description: 客户列表请求
 * @Author: join_lu
 * @CreateDate: 2021/8/4 9:57
 */
public class ClientQuestionBean {


    private String area;
    private String budgets;

    private String city;
    private String string;

    private String houseTypes;

    private String keyword;

    private String labelIds;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBudgets() {
        return budgets;
    }

    public void setBudgets(String budgets) {
        this.budgets = budgets;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getHouseTypes() {
        return houseTypes;
    }

    public void setHouseTypes(String houseTypes) {
        this.houseTypes = houseTypes;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds;
    }
}
