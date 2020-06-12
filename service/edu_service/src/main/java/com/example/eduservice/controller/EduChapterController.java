package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.entity.course.ChapterVo;
import com.example.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService chapterService;

    @GetMapping("/{id}")
    public Result getChapters(@PathVariable String id){
        List<ChapterVo> chapters = chapterService.getChapters(id);
        return Result.ok().data("items", chapters);
    }
}

