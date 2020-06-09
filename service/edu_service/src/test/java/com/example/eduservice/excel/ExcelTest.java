package com.example.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel的测试类
 */
@SpringBootTest
public class ExcelTest {
    /**
     * 向excel中写数据
     */
    @Test
    void writeExcel() {
        // 实现写操作
        String filename = "demo.xlsx";
        // 调用方法实现写操作
        EasyExcel.write(filename, ExcelData.class).sheet("学生列表").doWrite(getData());
    }

    /**
     * 从excel中读取数据
     */
    @Test
    void readExcel(){
        String filename = "demo.xlsx";
        EasyExcel.read(filename, ExcelData.class, new ExcelListener()).sheet().doRead();
    }

    /**
     * 生成学生列表数据
     */
    List<ExcelData> getData() {
        List<ExcelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData data = new ExcelData();
            data.setSno(i);
            data.setSname("学生" + i);
            list.add(data);
        }
        return list;
    }
}
