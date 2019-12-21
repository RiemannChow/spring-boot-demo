package com.riemann.springbootdemo.dao;

import com.riemann.springbootdemo.model.UploadEasyExcelData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 23:42
 */
public interface EasyExcelDao {

    void saveEasyExcelMappingData(@Param("ueeDatas") List<UploadEasyExcelData> ueeDatas);

    List<UploadEasyExcelData> selectAll();
}
