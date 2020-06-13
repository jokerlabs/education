package com.example.eduservice.mapper;

import com.example.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eduservice.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getCoursePublishInfo(String courseId);
}
