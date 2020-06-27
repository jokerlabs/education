package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.Result;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/front/teacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    /**
     * 分页查询显示讲师信息
     * @param page 起始页
     * @param limit 每页的数量
     */
    @PostMapping("/getList/{page}/{limit}")
    public Result getTeacherList(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 把分页数据封装到pageTeacher对象
        teacherService.page(pageTeacher, wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return Result.ok().data(map);
    }

    /**
     * 根据id查询讲师的基本信息和课程信息
     * @param id 讲师的id
     */
    @GetMapping("/info/{id}")
    public Result getTeacherInfo(@PathVariable String id){
        // 查讲师的基本信息
        EduTeacher teacherInfo = teacherService.getById(id);
        // 查询课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);
        return Result.ok().data("teacherInfo", teacherInfo).data("courseList", courseList);
    }
}
