package com.example.ossservice.controller;

import com.example.commonutils.Result;
import com.example.ossservice.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OssController {
    @Autowired
    OssService ossService;
    /**
     * 上传头像
     */
    @PostMapping("/avatar")
    public Result uploadAvatar(MultipartFile file) {
        String url = ossService.uploadAvatar(file);
        return Result.ok().data("url", url);
    }

    /**
     * 上传banner
     */
    @PostMapping("/banner")
    public Result uploadBanner(MultipartFile file) {
        String url = ossService.uploadBanner(file);
        return Result.ok().data("url", url);
    }
}
