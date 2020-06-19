package com.example.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.client.VodServiceClient;
import com.example.eduservice.entity.EduVideo;
import com.example.eduservice.mapper.EduVideoMapper;
import com.example.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodServiceClient vodServiceClient;

    /**
     * 根据courseId删除小节
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 1. 删除视频
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();

        // 根据课程id查询视频的video_source_id
        wrapper1.eq("course_id", courseId);
        wrapper1.select("video_source_id");
        List<EduVideo> videos = baseMapper.selectList(wrapper1);
        // 从列表中取出video_source_id并把它加入到列表中
        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : videos) {
            String videoId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoId)){
                videoIds.add(videoId);
            }
        }

        // 执行vod服务的删除操作
        if (videoIds.size()>0){
            vodServiceClient.removeVideos(videoIds);
        }


        // 2. 删除数据库中的小节
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        baseMapper.delete(wrapper2);
    }
}
