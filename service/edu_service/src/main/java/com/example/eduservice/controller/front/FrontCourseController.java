package com.example.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.Result;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduTeacher;
import com.example.eduservice.entity.course.ChapterVo;
import com.example.eduservice.entity.front.FrontCourse;
import com.example.eduservice.entity.front.FrontCourseInfo;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseService;
import com.example.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduChapterService chapterService;

    @PostMapping("/{page}/{limit}")
    public Result getList(@PathVariable Long page, @PathVariable Long limit,
                          @RequestBody FrontCourse frontCourse){
        Page<EduCourse> pageCourse = new Page<>(page, limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 一级分类
        if (!StringUtils.isEmpty(frontCourse.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", frontCourse.getSubjectParentId());
        }
        // 二级分类
        if (!StringUtils.isEmpty(frontCourse.getSubjectId())) {
            wrapper.eq("subject_id", frontCourse.getSubjectId());
        }
        if (!StringUtils.isEmpty(frontCourse.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(frontCourse.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(frontCourse.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        // 把分页数据封装到pageTeacher对象
        courseService.page(pageCourse, wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

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
     * 根据课程id查询课程信息
     * @param courseId 课程id
     */
    @GetMapping("/info/{courseId}")
    public Result getInfo(@PathVariable String courseId) {
        FrontCourseInfo courseInfo = courseService.getFrontCourseInfo(courseId);
        List<ChapterVo> chapterList = chapterService.getChapters(courseId);
        return Result.ok().data("courseInfo", courseInfo).data("chapterList", chapterList);
    }
}
