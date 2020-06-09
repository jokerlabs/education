package com.example.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelData> {
    /**
     * 逐行读取数据
     */
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println("***" + excelData);
    }

    /**
     * 读取表头
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    /**
     * 读取完成之后的操作
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
