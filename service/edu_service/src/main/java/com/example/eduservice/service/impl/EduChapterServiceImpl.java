package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.baseservice.exception.MyException;
import com.example.eduservice.entity.EduChapter;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.entity.course.ChapterVo;
import com.example.eduservice.entity.course.VideoVo;
import com.example.eduservice.mapper.EduChapterMapper;
import com.example.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapters(String id) {
        List<ChapterVo> chapterVos = new ArrayList<>();

        // 根据课程id查询章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        List<EduChapter> chapters = this.list(chapterQueryWrapper);

        // 根据课程id查询小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        List<EduVideo> videos = videoService.list(videoQueryWrapper);


        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo video : videos) {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVos);
            chapterVos.add(chapterVo);
        }

        return chapterVos;
    }

//    /**
//     * 添加章节
//     */
//    @Override
//    public void addChapter(EduChapter chapter) {
//        baseMapper.insert(chapter);
//    }
//
//    /**
//     * 根据id查询章节
//     */
//    @Override
//    public EduChapter getChapter(String id) {
//        return baseMapper.selectById(id);
//    }
//
//    /**
//     * 更新章节
//     */
//    @Override
//    public void updateChapter(EduChapter chapter) {
//        baseMapper.updateById(chapter);
//    }

    /**
     * 根据chapterId删除章节
     */
    @Override
    public boolean deleteChapter(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);

        // 如果有小节，不能删除
        int count = videoService.count(wrapper);

        if (count > 0) {
            throw new MyException(20001, "小节不为空");
        } else {
            int res = baseMapper.deleteById(id);
            return res > 0;
        }
    }

    /**
     * 根据courseId删除章节
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
