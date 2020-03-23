package com.riemann.springbootdemo.dao;

import com.riemann.springbootdemo.model.PoiShopEntity;

import java.util.List;

/**
 * @author riemann
 * @date 2020/02/14 21:21
 */
public interface PoiShopDao {

    Integer uploadShopData(List<PoiShopEntity> list);

    Integer updateBatchShopData(List<PoiShopEntity> list);

    List<PoiShopEntity> selectSelectiveList(List<PoiShopEntity> shopEntityList);

    void batchInsertOrUpdate(List<PoiShopEntity> shopEntityList);

}
