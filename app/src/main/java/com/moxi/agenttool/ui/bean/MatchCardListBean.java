package com.moxi.agenttool.ui.bean;

import java.util.List;

/**
 * @author feng wen jun
 * @description 城市人脉
 * @since 2021/2/19 0019
 */
public class MatchCardListBean {

    /**
     * current : 1
     * size : 10
     * total : 999
     * list : [{"imagePhoto":"http://123.jgp","industry":"软件开发","positionName":"员工","realName":"张茸茸"}]
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
         * imagePhoto : http://123.jgp
         * industry : 软件开发
         * positionName : 员工
         * realName : 张茸茸
         */

        private String imagePhoto;
        private String industry;
        private String positionName;
        private String position;
        private String realName;
        private String memberId;

        /**
         * memberId : 100002
         * mobile : 15923042732
         * position : 经理
         */


        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getImagePhoto() {
            return imagePhoto;
        }

        public void setImagePhoto(String imagePhoto) {
            this.imagePhoto = imagePhoto;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}
