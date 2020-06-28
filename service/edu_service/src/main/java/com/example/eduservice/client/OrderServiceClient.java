package com.example.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", fallback = OrderDegradeFeignClient.class)
@Component
public interface OrderServiceClient {
    /**
     * 查询用户是否拥有课程
     * @param courseId 课程id
     * @param memberId 用户id
     */
    @GetMapping("/order/api/isGet/{courseId}/{memberId}")
    boolean getStatus(@PathVariable String courseId, @PathVariable String memberId);
}
