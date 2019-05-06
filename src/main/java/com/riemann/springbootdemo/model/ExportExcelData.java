package com.riemann.springbootdemo.model;

import java.util.List;

/**
 * @author riemann
 * @date 2019/04/28 21:28
 */
public class ExportExcelData {

    //导出Excel的文件名
    private String fileName;

    //模板类型
    private Integer templateType;

    //每个sheet页的数据
    private List<SheetData> sheetData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public List<SheetData> getSheetData() {
        return sheetData;
    }

    public void setSheetData(List<SheetData> sheetData) {
        this.sheetData = sheetData;
    }

}
