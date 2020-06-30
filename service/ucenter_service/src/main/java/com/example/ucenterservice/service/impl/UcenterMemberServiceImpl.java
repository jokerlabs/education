package com.example.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.baseservice.exception.MyException;
import com.example.commonutils.JwtUtils;
import com.example.commonutils.MD5;
import com.example.ucenterservice.entity.UcenterMember;
import com.example.ucenterservice.entity.vo.RegisterVo;
import com.example.ucenterservice.mapper.UcenterMemberMapper;
import com.example.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    /**
     * 登录方法
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        // 获取登录手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(20001, "账号或密码为空");
        }

        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new MyException(20001, "你输入的账号不存在");
        }

        // 密码编码加密 MD5
        // 判断密码
        if (!MD5.encrypt(password).equals(member.getPassword())){
            throw new MyException(20001, "您输入的密码有误");
        }

        if (member.getIsDisabled()){
            throw new MyException(20001, "您的账号不能使用");
        }

        // 返回token
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    /**
     * 注册方法
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册的数据
        UcenterMember member = new UcenterMember();
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile)){
            throw new MyException(20001, "请输入手机号");
        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new MyException(20001, "您输入的手机号已经使用");
        }

        // 验证注册码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new MyException(20001, "您输入的注册码有误，请核对或者重新发送");
        }

        // 添加数据到数据库中
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setAvatar("...");
        baseMapper.insert(member);
    }

    /**
     * 根据token查询信息
     * @param request request对象包含token
     */
    @Override
    public UcenterMember getMemberInfoByToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        return  baseMapper.selectById(memberId);
    }

    /**
     * 根据id查询信息
     * @param id 用户id
     */
    @Override
    public UcenterMember getMemberInfoById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * 统计一天的注册人数
     * @param day 日期
     */
    @Override
    public Integer countRegister(String day) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("DATE(gmt_create)", day);
        return baseMapper.selectCount(wrapper);
    }
}
