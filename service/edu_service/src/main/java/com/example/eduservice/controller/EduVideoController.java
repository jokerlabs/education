package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * TODO 同时删除视频
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteVideo(@RequestBody @PathVariable String id) {
        boolean flag = videoService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

