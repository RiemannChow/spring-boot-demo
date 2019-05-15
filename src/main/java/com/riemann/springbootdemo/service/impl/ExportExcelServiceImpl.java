package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.model.ApiResponse;
import com.riemann.springbootdemo.model.ExportExcelData;
import com.riemann.springbootdemo.model.SheetData;
import com.riemann.springbootdemo.model.TabularData;
import com.riemann.springbootdemo.service.ExportExcelService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author riemann
 * @date 2019/04/28 22:05
 */
@Service
public class ExportExcelServiceImpl implements ExportExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExportExcelServiceImpl.class);

    @Override
    public ApiResponse exportExcel(ExportExcelData exportExcelData) {

        ApiResponse apiResponse = new ApiResponse();
        String fileName = exportExcelData.getFileName();
        int templateType = exportExcelData.getTemplateType();
        List<SheetData> sheetDataList = exportExcelData.getSheetData();

        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        //    HSSFSheet sheet = wb.createSheet();
        //创建字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");//使用宋体
        font.setFontHeightInPoints((short) 12);//字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        //创建单元格样式style
        HSSFCellStyle style = wb.createCellStyle();
        style.setFont(font);//将字体注入
        style.setWrapText(true);//自动换行
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setBorderTop((short) 1);//设置边框大小
        style.setBorderBottom((short) 1);
        style.setBorderLeft((short) 1);
        style.setBorderRight((short) 1);

        //循环遍历有多少个sheeet页
        for (int i = 0; i < sheetDataList.size(); i++) {
            SheetData sheetData = sheetDataList.get(i);
            String sheetName = sheetData.getSheetName();
            List<TabularData> tabularData = sheetData.getTabularData();
            HSSFSheet sheet = wb.createSheet();//创建工作表
            wb.setSheetName(i, sheetName);

            for(TabularData tab : tabularData) {
                HSSFRow row = sheet.getRow(tab.getFirstRow() - 1);
                if (row == null) {
                    row = sheet.createRow(tab.getFirstRow() - 1);//创建所需的行数
                }
                HSSFCell cell = row.getCell(tab.getFirstCol() - 1);//设置单元格的数据
                if (cell == null) {
                    cell = row.createCell(tab.getFirstCol() - 1);
                    cell.setCellValue(tab.getTabularContent());
                }
                //合并单元格
                /*sheet.addMergedRegion(new CellRangeAddress(tab.getFirstRow() - 1, tab.getLastRow() - 1,
                        tab.getFirstCol() - 1, tab.getLastCol() -1));*/

            }
        }

        if (downloadExcel(wb)){
            apiResponse.setStatusCode("200");
            apiResponse.setMessage("export excel success");
        } else {
            apiResponse.setStatusCode("600");
            apiResponse.setMessage("export excel failed");
        }
        return apiResponse;
    }

    public boolean downloadExcel(HSSFWorkbook wb) {
        boolean flag = true;
        String filePath = "D:\\excel";
        File file = new File(filePath);
        if (!file.exists()) {
            flag = false;
            logger.info("The file is not exists.");
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
