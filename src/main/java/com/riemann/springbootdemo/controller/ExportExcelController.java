package com.riemann.springbootdemo.controller;

/*import com.riemann.springbootdemo.model.ApiResponse;
import com.riemann.springbootdemo.model.ExportExcelData;
import com.riemann.springbootdemo.service.ExportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


*//**
 * Excel导出 Controller
 *
 * @author riemann
 * @date 2019/04/28 21:37
 *//*
@RestController
@RequestMapping(value = "/export")
public class ExportExcelController {

    @Autowired
    private ExportExcelService exportExcelService;

    @RequestMapping(value = "/exportExcel", method= RequestMethod.POST)
    public ApiResponse exportExcel(@RequestBody ExportExcelData eeData) {
        ApiResponse apiResponse = new ApiResponse();
        if (eeData == null) {
            return null;
        } else {
            apiResponse = exportExcelService.exportExcel(eeData);
        }
        return apiResponse;
    }

}*/
