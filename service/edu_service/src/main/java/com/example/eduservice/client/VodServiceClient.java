package com.example.eduservice.client;

import com.example.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Vod服务注册管理类
 */
@FeignClient("vod-service")
@Component
public interface VodServiceClient {
    /**
     * 删除视频
     * 定义调用方法的路径
     */
    @DeleteMapping("/vod/video/delete/{videoId}")
    Result removeVideo(@PathVariable String videoId);

    /**
     * 删除多个视频
     */
    @DeleteMapping("/vod/video/delete/")
    Result removeVideos(@RequestParam List<String> videoIds);
}
