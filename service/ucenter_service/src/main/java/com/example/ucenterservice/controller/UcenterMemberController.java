package com.example.ucenterservice.controller;

import com.example.commonutils.Result;
import com.example.commonutils.orderVo.UcenterMemberVo;
import com.example.ucenterservice.entity.UcenterMember;
import com.example.ucenterservice.entity.vo.RegisterVo;
import com.example.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-23
 */
@RestController
@RequestMapping("/ucenter")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    UcenterMemberService memberService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return Result.ok().data("token", token);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result userRegister(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return Result.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/getInfo")
    public Result getMemberInfoByToken(HttpServletRequest request) {
        UcenterMember member = memberService.getMemberInfoByToken(request);
        return Result.ok().data("item", member);
    }

    /**
     * 根据id获取用户信息
     * 提供给外部调用的接口
     */
    @GetMapping("/api/{id}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String id) {
        UcenterMember member = memberService.getMemberInfoById(id);

        // 被member中的值复制到vo
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, ucenterMemberVo);
        return ucenterMemberVo;
    }

    /**
     * 查询某一天的注册人数
     */
    @GetMapping("/api/count/{day}")
    public Integer countRegister(@PathVariable String day){
        return memberService.countRegister(day);
    }
}

