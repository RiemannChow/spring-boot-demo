package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.dao.EasyExcelDao;
import com.riemann.springbootdemo.model.UploadEasyExcelData;
import com.riemann.springbootdemo.service.UploadEasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 23:40
 */
@Service
public class UploadEasyExcelServiceImpl implements UploadEasyExcelService {

    @Autowired
    private EasyExcelDao easyExcelDao;

    @Override
    public void saveEasyExcelMappingData(List<UploadEasyExcelData> ueeDatas) {
        easyExcelDao.saveEasyExcelMappingData(ueeDatas);
    }

    @Override
    public List<UploadEasyExcelData> selectAll() {
        return easyExcelDao.selectAll();
    }
}
