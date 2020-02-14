package com.riemann.springbootdemo.model;

/**
 * @author riemann
 * @date 2020/02/14 21:19
 */
public class PoiShopEntity {
    private String poiId;
    private Integer source;

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
