package com.moxi.agenttool.ui.bean;

/**
 * @ClassName: ClientDetailsBean
 * @Description: 客户资料
 * @Author: join_lu
 * @CreateDate: 2021/8/4 11:08
 */
public class ClientDetailsBean {

    private String code;
    private DataDTO data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        private ClientDTO client;
        private ClientExtDTO clientExt;

        public ClientDTO getClient() {
            return client;
        }

        public void setClient(ClientDTO client) {
            this.client = client;
        }

        public ClientExtDTO getClientExt() {
            return clientExt;
        }

        public void setClientExt(ClientExtDTO clientExt) {
            this.clientExt = clientExt;
        }

        public static class ClientDTO {
            private String area;
            private String avatar;
            private String city;
            private String clientType;
            private String createTime;
            private String deleted;
            private String id;
            private String isPrivatePhone;
            private String labelId;
            private String name;
            private String phone;
            private String remark;
            private String sex;
            private String updateTime;
            private String userId;

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

            public String getClientType() {
                return clientType;
            }

            public void setClientType(String clientType) {
                this.clientType = clientType;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsprivatephone() {
                return isPrivatePhone;
            }

            public void setIsprivatephone(String isPrivatePhone) {
                this.isPrivatePhone = isPrivatePhone;
            }

            public String getLabelId() {
                return labelId;
            }

            public void setLabelId(String labelId) {
                this.labelId = labelId;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class ClientExtDTO {
            private String age;
            private String budget;
            private String builtArea;
            private String checkIn;
            private String clientId;
            private String direction;
            private String feature;
            private String fitUp;
            private String houseType;
            private String lift;
            private String openDate;
            private String priceType;
            private String property;
            private String rentMax;
            private String rentMin;
            private String status;
            private String storey;
            private String structure;
            private String tenancy;
            private String uses;

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getBudget() {
                return budget;
            }

            public void setBudget(String budget) {
                this.budget = budget;
            }

            public String getBuiltArea() {
                return builtArea;
            }

            public void setBuiltArea(String builtArea) {
                this.builtArea = builtArea;
            }

            public String getCheckIn() {
                return checkIn;
            }

            public void setCheckIn(String checkIn) {
                this.checkIn = checkIn;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
            }

            public String getFitUp() {
                return fitUp;
            }

            public void setFitUp(String fitUp) {
                this.fitUp = fitUp;
            }

            public String getHouseType() {
                return houseType;
            }

            public void setHouseType(String houseType) {
                this.houseType = houseType;
            }

            public String getLift() {
                return lift;
            }

            public void setLift(String lift) {
                this.lift = lift;
            }

            public String getOpenDate() {
                return openDate;
            }

            public void setOpenDate(String openDate) {
                this.openDate = openDate;
            }

            public String getPriceType() {
                return priceType;
            }

            public void setPriceType(String priceType) {
                this.priceType = priceType;
            }

            public String getProperty() {
                return property;
            }

            public void setProperty(String property) {
                this.property = property;
            }

            public String getRentMax() {
                return rentMax;
            }

            public void setRentMax(String rentMax) {
                this.rentMax = rentMax;
            }

            public String getRentMin() {
                return rentMin;
            }

            public void setRentMin(String rentMin) {
                this.rentMin = rentMin;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStorey() {
                return storey;
            }

            public void setStorey(String storey) {
                this.storey = storey;
            }

            public String getStructure() {
                return structure;
            }

            public void setStructure(String structure) {
                this.structure = structure;
            }

            public String getTenancy() {
                return tenancy;
            }

            public void setTenancy(String tenancy) {
                this.tenancy = tenancy;
            }

            public String getUses() {
                return uses;
            }

            public void setUses(String uses) {
                this.uses = uses;
            }
        }
    }
}
