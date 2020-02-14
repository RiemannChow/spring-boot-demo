package com.riemann.springbootdemo.service;

import com.riemann.springbootdemo.model.PoiShopEntity;

import java.util.List;

/**
 * @author riemann
 * @date 2020/02/14 21:13
 */
public interface PoiShopService {

    Integer updataOrInsert(List<PoiShopEntity> list);

}
