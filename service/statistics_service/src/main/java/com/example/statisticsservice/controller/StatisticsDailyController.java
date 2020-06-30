package com.example.statisticsservice.controller;


import com.example.commonutils.Result;
import com.example.statisticsservice.client.UcenterServiceClient;
import com.example.statisticsservice.service.StatisticsDailyService;
import org.bouncycastle.asn1.bc.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/sta/")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService dailyService;

    @Autowired
    UcenterServiceClient ucenterServiceClient;

    /** 统一某天的统计人数
     *
     * @param day 某天
     */
    @PostMapping("/register/{day}")
    public Result countRegister(@PathVariable String day) {
        dailyService.countRegister(day);
        return Result.ok();
    }

    /**
     * 图表显示，返回数据， 日期和对应的数据
     *
     * @param type
     * @param begin
     * @param end
     */
    @GetMapping("/data/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end){
        Map<String, Object> map = dailyService.getData(type, begin, end);
        return Result.ok().data(map);
    }
}

