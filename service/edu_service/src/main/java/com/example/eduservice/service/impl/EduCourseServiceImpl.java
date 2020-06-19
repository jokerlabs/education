package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.baseservice.exception.MyException;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduCourseDescription;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.entity.vo.CourseQuery;
import com.example.eduservice.mapper.EduCourseMapper;
import com.example.eduservice.service.EduChapterService;
import com.example.eduservice.service.EduCourseDescriptionService;
import com.example.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    /**
     * 添加课程基本信息
     */
    @Override
    public String  addCourse(CourseInfoVo courseInfo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfo, course);
        int insert = baseMapper.insert(course);
        if (insert == 0){
            throw new MyException(20001, "添加失败");
        }

        // 获取课程id, 添加到Description表中，保持一对一关系
        String cid = course.getId();
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, courseDescription);

        // 添加id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    /**
     * 获取课程信息
     */
    @Override
    public CourseInfoVo getCourseInfo(String id) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        EduCourse course = baseMapper.selectById(id);
        BeanUtils.copyProperties(course, courseInfoVo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        BeanUtils.copyProperties(courseDescription, courseInfoVo);

        return courseInfoVo;
    }

    /**
     * 更新课程信息
     */
    @Override
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update == 0){
            throw new MyException(20001, "添加失败");
        }

        // 更新courseDescription
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);


        return courseInfoVo.getId();
    }

    /**
     * 发布信息确认
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return baseMapper.getCoursePublishInfo(id);
    }


    /**
     * 条件分页查询
     */
    @Override
    public Page<EduCourse> getPage(Long current, Long limit, CourseQuery courseQuery) {
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        // 创建page对象，构建查询条件
        Page<EduCourse> page = new Page<>();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status", status);
        }

        // 分页查询
        baseMapper.selectPage(page, wrapper);
        return page;
    }

    /**
     * 删除课程, 删除课程相关的所有信息
     */
    @Override
    public void deleteCourseById(String courseId) {
        // 删除小节
        videoService.removeVideoByCourseId(courseId);

        // 删除章节
        chapterService.removeChapterByCourseId(courseId);

        // 删除课程描述
        courseDescriptionService.removeById(courseId);

        // 删除课程
        int res = baseMapper.deleteById(courseId);
        if (res == 0){
            throw new MyException(20001,"删除失败");
        }
    }
}
