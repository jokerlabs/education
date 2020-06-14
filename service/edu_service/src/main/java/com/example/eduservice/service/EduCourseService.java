package com.example.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.entity.vo.CoursePublishVo;
import com.example.eduservice.entity.vo.CourseQuery;
import com.example.eduservice.mapper.EduCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfo);

    CourseInfoVo getCourseInfo(String id);

    String updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    Page<EduCourse> getPage(Long current, Long limit, CourseQuery courseQuery);

    void deleteCourseById(String courseId);
}
