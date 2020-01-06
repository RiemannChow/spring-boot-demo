package com.riemann.springbootdemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.riemann.springbootdemo.dao.EasyExcelDao;
import com.riemann.springbootdemo.listener.EasyExcelListener;
import com.riemann.springbootdemo.model.EasyExcelData;
import com.riemann.springbootdemo.model.LocatorData;
import com.riemann.springbootdemo.model.ReturnT;
import com.riemann.springbootdemo.service.EasyExcelService;
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
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private EasyExcelService ueeService;

    @Autowired
    private EasyExcelDao easyExcelDao;

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
            excelReader = EasyExcel.read(in, EasyExcelData.class,
                    new EasyExcelListener(ueeService)).build();
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

        List<EasyExcelData> easyExcelData = ueeService.selectAll();

        EasyExcel.write(response.getOutputStream(), EasyExcelData.class)
                .sheet("下载excel服务")
                .registerWriteHandler(styleStrategy)
                .doWrite(easyExcelData);
    }

    @PostMapping(value = "/uploadFileFromJson",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ReturnT<String>> uploadFileFromJson(@RequestParam(value = "mappingFile") MultipartFile mappingFile) {
        if (mappingFile == null) {
            return new ResponseEntity<>(new ReturnT<>(ReturnT.BAD_REQUEST, "Params can not be null"), HttpStatus.BAD_REQUEST);
        }
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        String str = null;

        try {
            is = mappingFile.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<EasyExcelData> eeDataList = JSONObject.parseArray(jsonArray.toJSONString(), EasyExcelData.class);

        int count = 0;
        try {
            count = easyExcelDao.uploadFileFromJson(eeDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count <= 0) {
            LOGGER.warn("no data to upload");
            return new ResponseEntity<>(new ReturnT<>(ReturnT.BAD_REQUEST, "no data to upload"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ReturnT<>("successfully imported " + count + " pieces of data"), HttpStatus.OK);
    }

    @PostMapping(value = "/arrayObjectNestingParse",produces = "application/json;charset=UTF-8")
    public ReturnT<String> arrayObjectNestingParse(@RequestParam(value = "jsonFile") MultipartFile jsonFile) {
        if (jsonFile == null) {
            return new ReturnT<>(ReturnT.BAD_REQUEST, "Params can not be null");
        }
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        String str = null;

        try {
            is = jsonFile.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<LocatorData> locatorDataList = new ArrayList<>();

        JSONArray jsonArray = JSONArray.parseArray(sb.toString());
        for (int i = 0; i < jsonArray.size(); i++) {
            LocatorData locatorData = new LocatorData();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categories = jsonObject.getString("categories");

            String coordinate = jsonObject.getString("coordinate");
            JSONObject coordinateObj = JSONObject.parseObject(coordinate);

            String address = coordinateObj.getString("address");
            String area = coordinateObj.getString("area");
            String areaId = coordinateObj.getString("area_id");
            String city = coordinateObj.getString("city");
            String cityCode = coordinateObj.getString("city_code");
            String district = coordinateObj.getString("district");
            String districtCode = coordinateObj.getString("district_code");
            String floor = coordinateObj.getString("floor");
            String latitude = coordinateObj.getString("latitude");
            String longitude = coordinateObj.getString("longitude");
            String province = coordinateObj.getString("province");
            String provinceCode = coordinateObj.getString("province_code");

            String name = jsonObject.getString("name");
            String phone = jsonObject.getString("phone");
            String poiId = jsonObject.getString("poi_id");

            locatorData.setCategories(categories);
            locatorData.setAddress(address);
            locatorData.setArea(area);
            locatorData.setAreaId(areaId);
            locatorData.setProvince(province);
            locatorData.setProvinceCode(provinceCode);
            locatorData.setCity(city);
            locatorData.setCityCode(cityCode);
            locatorData.setDistrict(district);
            locatorData.setDistrictCode(districtCode);
            locatorData.setFloor(floor);
            locatorData.setLatitude(Double.parseDouble(latitude));
            locatorData.setLongitude(Double.parseDouble(longitude));
            locatorData.setName(name);
            locatorData.setPhone(phone);
            locatorData.setPoiId(poiId);
            locatorDataList.add(locatorData);
        }
        LOGGER.info("locatorDataList: " + JSON.toJSONString(locatorDataList));
        return new ReturnT<>(ReturnT.SUCCESS, JSON.toJSONString(locatorDataList));
    }
}

