package com.example.eduservice.client;

import com.example.commonutils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 熔断实现类，调用服务出错后会执行
 */
@Component
public class OrderDegradeFeignClient implements OrderServiceClient {
    /**
     * 查询用户是否拥有课程
     * @param courseId 课程id
     * @param memberId 用户id
     */
    @GetMapping("/order/api/isGet/{courseId}/{memberId}")
    public boolean getStatus(@PathVariable String courseId, @PathVariable String memberId){
        return false;
    };
}
