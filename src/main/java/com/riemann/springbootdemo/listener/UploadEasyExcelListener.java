package com.riemann.springbootdemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.riemann.springbootdemo.model.UploadEasyExcelData;
import com.riemann.springbootdemo.service.UploadEasyExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传Excel的监听类
 * @author riemann
 * @date 2019/12/19 23:26
 */
public class UploadEasyExcelListener extends AnalysisEventListener<UploadEasyExcelData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadEasyExcelListener.class);

    // 每隔1000条存储数据库，然后清理list，方便内存回收
    private static final int BATCH_COUNT = 1000;

    // 存储解析excel条数
    private int count = 0;

    @Autowired
    private UploadEasyExcelService ueeService;

    List<UploadEasyExcelData> ueeDatas = new ArrayList<>();

    public UploadEasyExcelListener(UploadEasyExcelService ueeService) {
        super();
        this.ueeService = ueeService;
    }

    @Override
    public void invoke(UploadEasyExcelData ueeData, AnalysisContext analysisContext) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("One record was Parsed, uploadEasyExcelData:{}", ueeData);
        }
        count += 1;
        ueeDatas.add(ueeData);
        if (ueeDatas.size() >= BATCH_COUNT) {
            ueeService.saveEasyExcelMappingData(ueeDatas);
            ueeDatas.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ueeService.saveEasyExcelMappingData(ueeDatas);
        LOGGER.info("all excel data parsed, count:{}", count);
    }
}
