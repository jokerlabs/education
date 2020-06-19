package com.example.vodservice.controller;

import com.example.commonutils.Result;
import com.example.vodservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     */
    @PostMapping("/video/upload")
    public Result uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("item", videoId);
    }

    /**
     * 删除视频
     */
    @DeleteMapping("/video/delete/{videoId}")
    public Result removeVideo(@PathVariable String videoId) {
        vodService.deleteVideo(videoId);
        return Result.ok();
    }

    /**
     * 删除多个视频
     */
    @DeleteMapping("/video/delete/")
    public Result removeVideos(@RequestParam List<String> videoIds) {
        System.out.println(videoIds);
        vodService.deleteVideos(videoIds);
        return Result.ok();
    }
}
