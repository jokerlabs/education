package com.example.orderservice.client;

import com.example.commonutils.orderVo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Ucenter服务注册管理类
 */
@FeignClient("ucenter-service")
@Component
public interface UcenterServiceClient {
    /**
     * 服务调用，根据id查询用户信息
     * @param id 用户id
     */
    @GetMapping("/ucenter/api/{id}")
    UcenterMemberVo getMemberInfoById(@PathVariable String id);
}
