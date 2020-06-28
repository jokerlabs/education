package com.example.orderservice.client;

import com.example.commonutils.orderVo.EduCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Edu服务注册管理类
 */
@FeignClient("edu-service")
@Component
public interface EduServiceClient {
    /**
     * 服务调用，根据id查询课程信息
     */
    @GetMapping("/edu/course/api/{courseId}")
    EduCourseVo getInfo(@PathVariable String courseId);
}
