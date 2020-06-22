package com.example.cmsservice.controller;


import com.example.cmsservice.entity.CrmBanner;
import com.example.cmsservice.service.CrmBannerService;
import com.example.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/cms/banner")
@CrossOrigin
public class BannerController {
    @Autowired
    CrmBannerService bannerService;

    @GetMapping("")
    public Result getBanners(){
        List<CrmBanner> list = bannerService.selectBanners();
        return Result.ok().data("items", list);
    }
}

