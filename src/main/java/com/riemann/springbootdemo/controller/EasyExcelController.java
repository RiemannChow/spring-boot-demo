package com.riemann.springbootdemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author riemann
 * @date 2019/12/19 22:10
 */
@Api(description = "上传、下载EasyExcel接口")
@RestController
public class EasyExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelController.class);

    @Autowired
    private UploadEasyExcelService ueeService;

    @ApiOperation(value = "上传excel", notes = "通过easy excel上传excel到db")
    @ApiImplicitParam(name = "serviceFile", value = "excel文件", paramType = "save", required = true)
    @PostMapping(value = "/uploadEasyExcel",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ReturnT<String>> uploadEasyExcel(@RequestParam(value = "serviceFile") MultipartFile serviceFile) {
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
        } catch (IOException e) {
            LOGGER.error("import excel to db fail", e);
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

    @ApiOperation(value = "下载excel", notes = "通过easy excel从db下载数据到excel")
    @GetMapping(value = "/downloadEasyExcel",produces = "application/json;charset=UTF-8")
    public void downloadEasyExcel(HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(new Date());
        String fileName = URLEncoder.encode("下载excel服务", "UTF-8") + datetime;

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // excel头策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteFont.setBold(false);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // excel内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 设置handler
        HorizontalCellStyleStrategy styleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        List<UploadEasyExcelData> uploadEasyExcelData = ueeService.selectAll();

        EasyExcel.write(response.getOutputStream(), UploadEasyExcelData.class)
                .sheet("下载excel服务")
                .registerWriteHandler(styleStrategy)
                .doWrite(uploadEasyExcelData);
    }
}

