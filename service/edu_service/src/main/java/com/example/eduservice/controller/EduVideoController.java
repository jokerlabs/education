package com.example.eduservice.controller;


import com.example.baseservice.exception.MyException;
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

        // 根据videoId, 调用vodService中的方法实现视频删除
        if (!StringUtils.isEmpty(videoId)){
            Result result = vodServiceClient.removeVideo(videoId);
            if (result.getCode() == 20001) {
                throw new MyException(20001, "删除视频失败，熔断器...");
            }
        }

        // 删除小节
        boolean flag = videoService.removeById(id);

        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

