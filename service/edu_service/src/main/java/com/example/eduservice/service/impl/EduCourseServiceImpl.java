package com.example.eduservice.service.impl;

import com.example.baseservice.exception.MyException;
import com.example.eduservice.entity.EduCourse;
import com.example.eduservice.entity.EduCourseDescription;
import com.example.eduservice.entity.vo.CourseInfoVo;
import com.example.eduservice.mapper.EduCourseMapper;
import com.example.eduservice.service.EduCourseDescriptionService;
import com.example.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
