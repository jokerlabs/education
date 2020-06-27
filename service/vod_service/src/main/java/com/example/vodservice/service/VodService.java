package com.example.vodservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    void deleteVideos(List<String> videoIds);

    String  getVideoPlayAuth(String id);
}
