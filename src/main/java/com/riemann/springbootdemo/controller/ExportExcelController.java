package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.model.ApiResult;
import com.riemann.springbootdemo.model.ExportExcelData;
import com.riemann.springbootdemo.service.ExportExcelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * Excel导出 Controller
 *
 * @author riemann
 * @date 2019/04/28 21:37
 */
@RestController
@RequestMapping(value = "/export")
public class ExportExcelController {

    @Resource
    private ExportExcelService exportExcelService;

    @RequestMapping(value = "/exportExcel", method= RequestMethod.POST)
    public ApiResult exportExcel(@RequestBody ExportExcelData eeData) {
        ApiResult apiResult = new ApiResult();
        if (eeData == null) {
            return null;
        } else {
            apiResult = exportExcelService.exportExcel(eeData);
        }
        return apiResult;
    }

}
