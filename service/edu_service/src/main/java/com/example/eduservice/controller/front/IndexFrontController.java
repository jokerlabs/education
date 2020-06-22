package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.Result;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/index")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    /**
     * 查询课程前8条热门信息
     */
    @GetMapping("/course")
    public Result getCourses() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("LIMIT 8");
        List<EduCourse> list = courseService.list(wrapper);
        return Result.ok().data("items", list);
    }

    /**
     * 查询8个讲师信息
     */
    @GetMapping("/teacher")
    public Result getTeachers() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("LIMIT 4");
        List<EduTeacher> list = teacherService.list(wrapper);
        return Result.ok().data("items", list);
    }
}
