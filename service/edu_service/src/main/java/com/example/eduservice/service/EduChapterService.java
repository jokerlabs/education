package com.example.eduservice.service;

import com.example.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eduservice.entity.course.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo>  getChapters(String id);
}
