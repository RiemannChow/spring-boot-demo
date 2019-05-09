package com.riemann.springbootdemo.service;

import com.riemann.springbootdemo.model.ApiResponse;
import com.riemann.springbootdemo.model.ExportExcelData;

/**
 * @author riemann
 * @date 2019/04/28 21:58
 */
public interface ExportExcelService {

    ApiResponse exportExcel(ExportExcelData exportExcelData);

}
