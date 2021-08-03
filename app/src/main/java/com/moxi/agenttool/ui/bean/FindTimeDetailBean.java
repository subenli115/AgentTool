package com.moxi.agenttool.ui.bean;

import java.util.List;

/**
 * @author feng wen jun
 * @description 时光详情
 * @since 2021/5/10 0010
 */
public class FindTimeDetailBean {

    /**
     * current : 1
     * size : 10
     * total : 999
     * list : [{"avatar":"http//:1.jpg","dynamicContent":"完全不想上班","dynamicId":18671780554080256,"isLike":1,"likeNum":1,"memberId":12,"moduleName":"高光时刻","picture":"http://1.jpg","realName":"陈娇"}]
     */

    private int current;
    private int size;
    private int total;
    private List<ListBean> list;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * avatar : http//:1.jpg
         * dynamicContent : 完全不想上班
         * dynamicId : 18671780554080256
         * isLike : 1
         * likeNum : 1
         * memberId : 12
         * moduleName : 高光时刻
         * picture : http://1.jpg
         * realName : 陈娇
         */

        private String avatar;
        private String dynamicContent;
        private String dynamicId;
        private int isLike;
        private int likeNum;
        private String memberId;
        private String moduleName;
        private String picture;
        private String realName;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getDynamicContent() {
            return dynamicContent;
        }

        public void setDynamicContent(String dynamicContent) {
            this.dynamicContent = dynamicContent;
        }

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}
