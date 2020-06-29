package com.example.statisticsservice.schedule;

import com.example.statisticsservice.service.StatisticsDailyService;
import com.example.statisticsservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    StatisticsDailyService staDailyService;

    // 每天凌晨执行操作，cron表达式，6位，最后一位第七位默认
    @Scheduled(cron = "0 0 1 * * ?")
    public void update() {
        System.out.println("-------");
        staDailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task() {
//        System.out.println("............task执行");
//    }
}
