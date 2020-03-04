package com.riemann.springbootdemo.service.impl;

import com.google.common.collect.Maps;
import com.riemann.springbootdemo.dao.PoiShopDao;
import com.riemann.springbootdemo.model.PoiShopEntity;
import com.riemann.springbootdemo.service.PoiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author riemann
 * @date 2020/02/14 21:00
 */
public class PoiShopServiceImpl implements PoiShopService {

    @Autowired
    private PoiShopDao poiShopDao;

    @Override
    public Integer updataOrInsert(List<PoiShopEntity> shopList) {
        int amount = 0;
        Map<String, List> dataMap = validateShopData(shopList);
        for (String key : dataMap.keySet()) {
            if ("insert".endsWith(key)) {
                amount = poiShopDao.uploadShopData(dataMap.get(key));
            } else {
                amount = poiShopDao.updateBatchShopData(dataMap.get(key));
            }
        }
        return amount;
    }

    private Map<String, List> validateShopData(List<PoiShopEntity> shopEntityList) {
        HashMap<String, List> result = Maps.newHashMap();
        List<PoiShopEntity> shopList = poiShopDao.selectSelectiveList(shopEntityList);
        // 取差集
        List<PoiShopEntity> shopInsertList = shopList.stream().
                filter(item -> findShopData(item.getPoiId(), item.getSource(), shopList) == -1)
                .collect(Collectors.toList());
        // 取并集
        List<PoiShopEntity> shopUpdataList = shopList.stream().
                filter(item -> findShopData(item.getPoiId(), item.getSource(), shopList) > -1)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(shopInsertList)) {
            result.put("insert", shopInsertList);
        } else {
            result.put("update", shopUpdataList);
        }
        return result;
    }

    private int findShopData(String poiId, Integer source, List<PoiShopEntity> shopList) {
        int res = -1;
        for (int i = 0; i < shopList.size(); i++) {
            if (poiId.equals(shopList.get(i).getPoiId()) && source.intValue() == shopList.get(i).getSource()) {
                res = i;
                break;
            }
        }
        return res;
    }
}
