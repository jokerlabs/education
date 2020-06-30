package com.example.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.statisticsservice.client.UcenterServiceClient;
import com.example.statisticsservice.entity.StatisticsDaily;
import com.example.statisticsservice.mapper.StatisticsDailyMapper;
import com.example.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    UcenterServiceClient ucenterServiceClient;

    @Override
    public void countRegister(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        StatisticsDaily daily = baseMapper.selectOne(wrapper);

        // 存在相同的日期，更新，没有则添加
        Integer countRegister = ucenterServiceClient.countRegister(day);

        if (daily == null) {
            StatisticsDaily temp = new StatisticsDaily();
            temp.setRegisterNum(countRegister);
            temp.setDateCalculated(day);

            temp.setVideoViewNum(0);
            temp.setCourseNum(0);
            temp.setLoginNum(0);
            baseMapper.insert(temp);
        } else {
            daily.setRegisterNum(countRegister);
            baseMapper.update(daily, wrapper);
        }
    }

    @Override
    public Map<String, Object> getData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily>  wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        // 选择要查询的字段
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);
        // 返回两部分数据
        List<String> dateList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();
        for (StatisticsDaily daily: statisticsDailies) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":  dataList.add(daily.getLoginNum()); break;
                case "register_num": dataList.add(daily.getRegisterNum()); break;
                case "video_view_num": dataList.add(daily.getVideoViewNum()); break;
                case "course_num": dataList.add(daily.getCourseNum()); break;
                default: break;
            }
        }
        // 封装数据返回
        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("dataList", dataList);
        return map;
    }
}
