package com.example.ucenterservice.service;

import com.example.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ucenterservice.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录方法
     * @param ucenterMember 用户类
     * @return token
     */
    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getMemberInfoByToken(HttpServletRequest request);

    UcenterMember getMemberInfoById(String id);

    Integer countRegister(String day);
}
