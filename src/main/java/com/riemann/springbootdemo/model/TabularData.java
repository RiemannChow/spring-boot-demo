package com.riemann.springbootdemo.model;

/**
 * @author riemann
 * @date 2019/04/28 21:33
 */
public class TabularData {

    //每个单元格的内容
    private String tabularContent;

    //开始行
    private Integer firstRow;

    //结束行
    private Integer lastRow;

    //开始列
    private Integer firstCol;

    //结束列
    private Integer lastCol;

    public String getTabularContent() {
        return tabularContent;
    }

    public void setTabularContent(String tabularContent) {
        this.tabularContent = tabularContent;
    }

    public Integer getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Integer firstRow) {
        this.firstRow = firstRow;
    }

    public Integer getLastRow() {
        return lastRow;
    }

    public void setLastRow(Integer lastRow) {
        this.lastRow = lastRow;
    }

    public Integer getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(Integer firstCol) {
        this.firstCol = firstCol;
    }

    public Integer getLastCol() {
        return lastCol;
    }

    public void setLastCol(Integer lastCol) {
        this.lastCol = lastCol;
    }

}
