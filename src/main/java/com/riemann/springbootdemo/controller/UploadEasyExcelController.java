package com.riemann.springbootdemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.riemann.springbootdemo.listener.UploadEasyExcelListener;
import com.riemann.springbootdemo.model.ReturnT;
import com.riemann.springbootdemo.model.UploadEasyExcelData;
import com.riemann.springbootdemo.service.UploadEasyExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author riemann
 * @date 2019/12/19 22:10
 */
@Api(description = "上传EasyExcel接口")
@RestController
public class UploadEasyExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadEasyExcelController.class);

    @Autowired
    private UploadEasyExcelService ueeService;

    @ApiOperation(value = "上传excel", notes = "通过easy excel上传excel到db")
    @ApiImplicitParam(name = "serviceFile", value = "excel文件", paramType = "save", required = true)
    @PostMapping(value = "/uploadEasyExcel",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ReturnT<String>> UploadEasyExcel(@RequestParam(value = "serviceFile") MultipartFile serviceFile) {
        if (serviceFile == null) {
            return new ResponseEntity<>(new ReturnT<>(ReturnT.BAD_REQUEST, "Params can not be null"), HttpStatus.BAD_REQUEST);
        }
        ExcelReader excelReader = null;
        InputStream in = null;
        try {
            in = serviceFile.getInputStream();
            excelReader = EasyExcel.read(in, UploadEasyExcelData.class,
                    new UploadEasyExcelListener(ueeService)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } catch (IOException ex) {
            LOGGER.error("import excel to db fail", ex);
        } finally {
            close(in);
            // 这里一定别忘记关闭，读的时候会创建临时文件，到时磁盘会崩
            if (excelReader != null) {
                excelReader.finish();
            }
        }
        return new ResponseEntity<>(new ReturnT<>("upload easyexcel success"), HttpStatus.OK);
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.error("Close io stream error", e);
            }
        }
    }
}

