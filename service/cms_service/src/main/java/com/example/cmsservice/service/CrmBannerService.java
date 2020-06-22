package com.example.cmsservice.service;

import com.example.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-21
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectBanners();
}
