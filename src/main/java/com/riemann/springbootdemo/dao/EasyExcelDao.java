package com.riemann.springbootdemo.dao;

import com.riemann.springbootdemo.model.EasyExcelData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 23:42
 */
public interface EasyExcelDao {

    void saveEasyExcelMappingData(@Param("ueeDatas") List<EasyExcelData> ueeDatas);

    List<EasyExcelData> selectAll();

    int uploadFileFromJson(@Param("eeDataList") List<EasyExcelData> eeDataList);
}
