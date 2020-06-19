package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.entity.EduChapter;
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

    /**
     * 查询所有章节
     */
    @GetMapping("/{courseId}")
    public Result getChapters(@PathVariable String courseId) {
        List<ChapterVo> chapters = chapterService.getChapters(courseId);
        return Result.ok().data("items", chapters);
    }

    /**
     * 添加章节
     */
    @PostMapping("/add")
    public Result addChapter(@RequestBody EduChapter chapter) {
        // chapterService.addChapter(chapter);
        chapterService.save(chapter);
        return Result.ok();
    }

    /**
     * 更新章节
     */
    @PostMapping("/update")
    public Result updateChapter(@RequestBody EduChapter chapter) {
        // chapterService.updateChapter(chapter);
        chapterService.updateById(chapter);
        return Result.ok();
    }

    /**
     * 查询章节
     */
    @GetMapping("/find/{id}")
    public Result getChapter(@PathVariable String id) {
        // chapterService.getChapter(id);
        EduChapter chapter = chapterService.getById(id);
        return Result.ok().data("items", chapter);
    }

    /**
     * 删除章节
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteChapter(@PathVariable String id) {
        boolean flag = chapterService.deleteChapter(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

