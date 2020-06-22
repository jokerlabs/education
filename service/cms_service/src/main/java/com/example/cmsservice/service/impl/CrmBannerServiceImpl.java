package com.example.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cmsservice.entity.CrmBanner;
import com.example.cmsservice.mapper.CrmBannerMapper;
import com.example.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 排序查询三条记录
     * 添加缓存
     */
    @Cacheable(value = "banner", key = "'selectBanners'")  // 缓存
    @Override
    public List<CrmBanner> selectBanners() {
        // 根据id降序排序，选择前三条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 拼接语句
        wrapper.last("LIMIT 3");

        return baseMapper.selectList(wrapper);
    }
}
