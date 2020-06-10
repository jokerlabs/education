package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService subjectService;

    // 获取课程分类列表
    @GetMapping("")
    public Result getSubjects() {
        List<OneSubject> subjects = subjectService.getSubjects();
        return Result.ok().data("data", subjects);
    }


    // 添加课程分类，获取上传的文件，读数据
    @PostMapping("/add")
    public Result addSubject(MultipartFile file) {
        subjectService.addSubject(file, subjectService);
        return Result.ok().message("添加课程成功");
    }
}

