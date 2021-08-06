package com.moxi.agenttool.contract;



/**
 * author feng wen jun
 * create  2017/3/31 10
 * des     基本常量
 * <p>
 * updateAuthor
 * updateDate
 * updateDes
 */

public interface AppConstans {

    //卡片详情
    String cardDetailURL = "http://10.10.0.45:8888/#/pages/H5/card?cardId=";

    //动态详情
    String dynamicDetailURL = "http://10.10.0.80:8080/#/dynamicDetails?id=";

    //企业详情
    String companyDetailURL = "http://10.10.0.80:8080/#/businessCard?id=";

    interface BusTag {

        String DELETE = "delete";
        String QUIT = "quit";
        String CLOSE = "close";
        String UPDATE = "update";
        String KEY = "key";
        String UPDATE_CARD = "update_card";
        String FOLLOW = "follow";
        String UPDATE_FRAGMENT2 = "update_fragment2";
        String UPDATE_FRAGMENT = "update_fragment";
        String EDIT = "EDIT";
        String EDIT_COMPANY = "edit_company";
        String UPDATE_NUM = "update_num";
        String ADDBEAN = "addbean";
        String UPDATE_JOB = "updateJob";
        String UPDATE_JOB_ID = "updateJobid";
        String CHANGE = "change";
        String FACEAUTH = "auth";
        String UPDATE_COMPANY = "updateCompany";
        String SEARCH_DATA = "searchData";

    }
}
