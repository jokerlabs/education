package com.example.vodservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);
}
