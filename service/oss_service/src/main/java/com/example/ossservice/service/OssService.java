package com.example.ossservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    String uploadAvatar(MultipartFile file);
}
