package com.riemann.springbootdemo.model;

import java.util.List;

/**
 * @author riemann
 * @date 2019/04/28 21:31
 */
public class SheetData {

    //每个sheet页的名字
    private String sheetName;

    //单元格的数据
    private List<TabularData> tabularData;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<TabularData> getTabularData() {
        return tabularData;
    }

    public void setTabularData(List<TabularData> tabularData) {
        this.tabularData = tabularData;
    }

}
