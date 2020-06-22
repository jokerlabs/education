package com.example.cmsservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cmsservice.entity.CrmBanner;
import com.example.cmsservice.service.CrmBannerService;
import com.example.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/cms/admin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    CrmBannerService bannerService;

    /**
     * 分页查询, 用于查看banner图片，进行管理
     */
    @GetMapping("/banner/{current}/{limit}")
    public Result getPage(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<CrmBanner> bannerPage = new Page<>();
        bannerService.page(bannerPage, null);
        Long total = bannerPage.getTotal();
        List<CrmBanner> records = bannerPage.getRecords();
        return Result.ok().data("total", total).data("items", records);
    }

    /**
     * 查询信息
     */
    @GetMapping("/banner/{id}")
    public Result select(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("item", banner);
    }

    /**
     * 添加banner图片
     */
    @PostMapping("/banner")
    public Result addBanner(@RequestBody CrmBanner banner) {
        boolean res = bannerService.save(banner);
        if (res) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 修改banner图片
     */
    @PostMapping("/banner/edit")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        boolean res = bannerService.updateById(banner);
        if (res) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 删除banner图片
     */
    @DeleteMapping("/banner/{id}")
    public Result deleteBanner(@PathVariable String id) {
        boolean flag = bannerService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}
