package com.example.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.Result;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.entity.vo.CourseQuery;
import com.example.eduservice.service.EduCourseService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService courseService;

    @GetMapping("/{id}")
    public Result getCourseInfo(@PathVariable String id) {
        CourseInfoVo courseInfo = courseService.getCourseInfo(id);
        return Result.ok().data("items", courseInfo);
    }

    /**
     * 添加操作
     */
    @PostMapping("/add")
    public Result add(@RequestBody CourseInfoVo courseInfo) {
        String cid = courseService.addCourse(courseInfo);
        return Result.ok().data("courseId", cid);
    }

    /**
     * 更新操作
     */
    @PostMapping("/update")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String cid = courseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", cid);
    }

    /**
     * 获取发布信息
     */
    @GetMapping("/publish/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        System.out.println(coursePublishVo);
        return Result.ok().data("items", coursePublishVo);
    }

    /**
     * 发布课程，修改status为normal
     */
    @PostMapping("/publish/{id}")
    public Result publish(@PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return Result.ok();
    }

    @GetMapping("")
    public Result getList(){
        List<EduCourse> list = courseService.list(null);
        return Result.ok().data("items", list);
    }

    /**
     * 条件分页查询
     */
    @PostMapping("/pages/{current}/{limit}")
    public Result getPage(@PathVariable Long current, @PathVariable Long limit,
                           @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = courseService.getPage(current, limit, courseQuery);

        // 对结果进行封装
        Long total = page.getTotal();
        List<EduCourse>  records = page.getRecords();

        return Result.ok().data("total", total).data("items", records);
    }

    @DeleteMapping("/{courseId}")
    public Result deleteById(@PathVariable String courseId){
        courseService.deleteCourseById(courseId);
        return Result.ok();
    }
}

