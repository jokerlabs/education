package com.example.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ucenter-service")
@Component
public interface UcenterServiceClient {
    @GetMapping("/ucenter/api/count/{day}")
    Integer countRegister(@PathVariable String day);
}
