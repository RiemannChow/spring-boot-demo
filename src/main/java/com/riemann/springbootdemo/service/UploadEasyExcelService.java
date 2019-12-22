package com.riemann.springbootdemo.service;

import com.riemann.springbootdemo.model.UploadEasyExcelData;

import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 23:31
 */
public interface UploadEasyExcelService {

    /**
     * 通过easy excel上传excel数据至mysql
     * @param ueeDatas
     */
    void saveEasyExcelMappingData(List<UploadEasyExcelData> ueeDatas);

    /**
     *查询所有
     * @return
     */
    List<UploadEasyExcelData> selectAll();

}
