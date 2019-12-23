package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.dao.EasyExcelDao;
import com.riemann.springbootdemo.model.EasyExcelData;
import com.riemann.springbootdemo.service.EasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 23:40
 */
@Service
public class EasyExcelServiceImpl implements EasyExcelService {

    @Autowired
    private EasyExcelDao easyExcelDao;

    @Override
    public void saveEasyExcelMappingData(List<EasyExcelData> ueeDatas) {
        easyExcelDao.saveEasyExcelMappingData(ueeDatas);
    }

    @Override
    public List<EasyExcelData> selectAll() {
        return easyExcelDao.selectAll();
    }
}
