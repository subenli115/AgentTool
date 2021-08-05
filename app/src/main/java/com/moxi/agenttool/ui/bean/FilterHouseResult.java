package com.moxi.agenttool.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: FilterHouseResult
 * @Description: java类作用描述
 * @Author: join_lu
 * @CreateDate: 2021/7/27 9:56
 */
public class FilterHouseResult implements Serializable{


    private Integer code;
    private List<DataDTO> data;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO implements Serializable {
        private String area;
        private String avatar;
        private String city;
        private String clientId;
        private String clientUpdateTime;
        private String createTime;
        public List<House> houseList;
        private String isPrivatePhone;
        private String name;
        private String phone;
        private String remark;
        private Integer sex;
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
            return isPrivatePhone;
        }

        public void setIsprivatephone(String isprivatephone) {
            this.isPrivatePhone = isPrivatePhone;
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

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public static class HouseListDTO {
            private String area;
            private String detailUrl;
            private String imgUrl;
            private String location;
            private String name;
            private String priceFirst;
            private String priceSecond;
            private String type;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
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

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
        }
    }
}
