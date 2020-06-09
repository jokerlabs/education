package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    // 添加课程分类，获取上传的文件，读数据
    @PostMapping("/add")
    public Result addSubject(MultipartFile file){
        subjectService.addSubject(file, subjectService);
        return Result.ok();
    }

}

