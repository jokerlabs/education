package com.example.eduservice.controller;


import com.example.commonutils.Result;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService courseService;

    @GetMapping("/{id}")
    public Result getCourseInfo(@PathVariable String id){
        CourseInfoVo courseInfo = courseService.getCourseInfo(id);
        return Result.ok().data("items", courseInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CourseInfoVo courseInfo){
        String cid = courseService.addCourse(courseInfo);
        return Result.ok().data("courseId", cid);
    }

    @PostMapping("/update")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String cid = courseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", cid);
    }
}

