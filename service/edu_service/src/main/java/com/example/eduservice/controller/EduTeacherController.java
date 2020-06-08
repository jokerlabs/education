package com.example.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.Result;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.vo.TeacherQuery;
import com.example.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    EduTeacherService teacherService;

    /**
     * 查询讲师表所有数据
     *
     * @return result
     */
    @GetMapping("")
    public Result find() {
        List<EduTeacher> list = teacherService.list(null);
// 测试异常处理
//        try {
//            int i = 10 / 0;
//        } catch (Exception e) {
//            throw new MyException(20001, "自定义异常");
//        }
        return Result.ok().data("items", list);
    }

//    public List<EduTeacher> find() {
//        List<EduTeacher> list = teacherService.list(null);
//        System.out.println(list);
//        return list;
//    }

    /**
     * 逻辑删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        if (teacherService.removeById(id)) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

//    public boolean delete(@PathVariable("id") String id) {
//        return teacherService.removeById(id);
//    }

    /**
     * 分页查询
     */
    @GetMapping("/page/{current}/{limit}")
    public Result pageList(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);         // 创建page对象
        teacherService.page(teacherPage, null);                // 实现分页, 底层封装，把数据封装到teacherPage里边
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Result.ok().data("total", total).data("rows", records);

//        Map<String, Object> map = new HashMap<>();
//        map.put("total", total);
//        map.put("rows", records);
//        return Result.ok().data(map);
    }

    @PostMapping("/pages/{current}/{limit}")
    public Result pageCondition(@PathVariable Long current, @PathVariable Long limit,
                                @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, limit);

        // 构建查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        // 排序条件
        wrapper.orderByDesc("gmt_create");

        teacherService.page(page, wrapper);

        // 获取值进行结果封装，返回
        Long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师
     */
    @PostMapping("")
    public Result save(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 查询信息
     */
    @GetMapping("/{id}")
    public Result select(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return Result.ok().data("item", teacher);
    }

    /**
     * 修改信息
     */
    @PostMapping("/edit")
    public Result update(@RequestBody EduTeacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}

