package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.client.VodServiceClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    @Autowired
    VodServiceClient vodServiceClient;  // 注入vodServiceClient

    /**
     * 添加章节
     */
    @PostMapping("/add")
    public Result addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return Result.ok();
    }

    /**
     * 更新章节
     */
    @PostMapping("/update")
    public Result updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return Result.ok();
    }

    /**
     * 查询章节
     */
    @GetMapping("/find/{id}")
    public Result getVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return Result.ok().data("items", video);
    }

    /**
     * 删除章节
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteVideo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        String videoId = video.getVideoSourceId();

        boolean flag = videoService.removeById(id);
        // 根据videoId, 调用vodService中的方法实现视频删除
        if (!StringUtils.isEmpty(videoId)){
            vodServiceClient.removeVideo(videoId);
        }

        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

