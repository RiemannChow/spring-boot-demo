package com.riemann.springbootdemo.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author riemann
 * @date 2020/01/06 23:55
 */
public class LocatorData {
    // 分类
    private String categories;
    // 商圈
    private String area;
    // 商圈id
    private String areaId;
    // 省份
    private String province;
    // 省份code
    private String provinceCode;
    // 城市
    private String city;
    // 城市code
    private String cityCode;
    // 区县
    private String district;
    // 区县code
    private String districtCode;
    // 楼层
    private Integer floor;
    // 经度
    private Double latitude;
    // 维度
    private Double longitude;
    // 店铺名称
    private String name;
    // 店铺地址
    private String address;
    // 店铺电话
    private String phone;
    // 店铺id
    private String poiId;

    public LocatorData() {
    }

    public LocatorData(LocatorDataJsonToListEntity entity) {
        this.categories = StringUtils.isEmpty(entity.getCategories()) ? "" : entity.getCategories();
        this.area = StringUtils.isEmpty(entity.getArea()) ? "" : entity.getArea();
        this.areaId = StringUtils.isEmpty(entity.getArea_id()) ? "" : entity.getArea_id();
        this.province = StringUtils.isEmpty(entity.getProvince()) ? "" : entity.getProvince();
        this.provinceCode = StringUtils.isEmpty(entity.getProvince_code()) ? "" : entity.getProvince_code();
        this.city = StringUtils.isEmpty(entity.getCity()) ? "" : entity.getCity();
        this.cityCode = StringUtils.isEmpty(entity.getCity_code()) ? "" : entity.getCity_code();
        this.district = StringUtils.isEmpty(entity.getDistrict()) ? "" : entity.getDistrict();
        this.districtCode = StringUtils.isEmpty(entity.getDistrict_code()) ? "" : entity.getDistrict_code();
        if (StringUtils.isBlank(entity.getFloor().toString())) {
            this.floor = 0;
        } else {
            if (Integer.getInteger(entity.getFloor().toString()) instanceof Integer) {
                this.floor = Integer.getInteger(entity.getFloor().toString());
            } else {
                this.floor = 0;
            }
        }
        if (StringUtils.isBlank(entity.getLatitude().toString())) {
            this.latitude = 0.00000;
        } else {
            if (Double.valueOf(entity.getLatitude().toString()) instanceof Double) {
                this.latitude = Double.valueOf(entity.getLatitude().toString());
            } else {
                this.latitude = 0.00000;
            }
        }
        if (StringUtils.isBlank(entity.getLongitude().toString())) {
            this.longitude = 0.00000;
        } else {
            if (Double.valueOf(entity.getLongitude().toString()) instanceof Double) {
                this.longitude = Double.valueOf(entity.getLongitude().toString());
            } else {
                this.longitude = 0.00000;
            }
        }
        this.name = StringUtils.isEmpty(entity.getName()) ? "" : entity.getName();
        this.address = StringUtils.isEmpty(entity.getAddress()) ? "" : entity.getAddress();
        this.phone = StringUtils.isEmpty(entity.getPhone()) ? "" : entity.getPhone();
        this.poiId = StringUtils.isEmpty(entity.getPoiId()) ? "" : entity.getPoiId();
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }
}
