package com.moxi.agenttool.ui.bean;

import java.util.List;

/**
 * @ClassName: ImportantBean
 * @Description: 重点客户查询
 * @Author: join_lu
 * @CreateDate: 2021/7/23 14:19
 */
public class ImportantBean {

    private String code;
    private String msg;
    private List<DataDTO> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String id;
        private String name;
        private String avatar;
        private String sex;
        private String remark;
        private String city;
        private String area;
        private String phone;
        private String isPrivatePhone;
        private String createTime;
        private String type;
        private String budget;
        private String houseType;
        private String builtArea;
        private String direction;
        private String storey;
        private String age;
        private String fitUp;
        private String uses;
        private String lift;
        private String structure;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIsprivatephone() {
            return isPrivatePhone;
        }

        public void setIsprivatephone(String isprivatephone) {
            this.isPrivatePhone = isprivatephone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getHouseType() {
            return houseType;
        }

        public void setHouseType(String houseType) {
            this.houseType = houseType;
        }

        public String getBuiltArea() {
            return builtArea;
        }

        public void setBuiltArea(String builtArea) {
            this.builtArea = builtArea;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getStorey() {
            return storey;
        }

        public void setStorey(String storey) {
            this.storey = storey;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getFitUp() {
            return fitUp;
        }

        public void setFitUp(String fitUp) {
            this.fitUp = fitUp;
        }

        public String getUses() {
            return uses;
        }

        public void setUses(String uses) {
            this.uses = uses;
        }

        public String getLift() {
            return lift;
        }

        public void setLift(String lift) {
            this.lift = lift;
        }

        public String getStructure() {
            return structure;
        }

        public void setStructure(String structure) {
            this.structure = structure;
        }
    }
}
