package com.example.eduservice.service;

import com.example.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    // 从文件中添加课程分类
    void addSubject(MultipartFile file, EduSubjectService SubjectService);

    List<OneSubject> getSubjects();
}
