package com.example.eduservice.client;

import com.example.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断实现类，调用服务出错后会执行
 */
@Component
public class VodFileDegradeFeignClient implements VodServiceClient {
    @Override
    public Result removeVideo(String videoId) {
        return Result.error().message("删除视频出错！");
    }

    @Override
    public Result removeVideos(List<String> videoIds) {
        return Result.error().message("删除多个视频出错！");
    }
}
