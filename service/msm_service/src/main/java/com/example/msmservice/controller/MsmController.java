package com.example.msmservice.controller;

import com.example.commonutils.Result;
import com.example.msmservice.service.MsmService;
import com.example.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/msm")
public class MsmController {
    @Autowired
    MsmService msmService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信
     */
    @GetMapping("/send/{phoneNum}")
    public Result send(@PathVariable String phoneNum){
        // 从redis中获取验证码，如果获取，直接返回
        String code = redisTemplate.opsForValue().get(phoneNum);
        if (!StringUtils.isEmpty(code)) return Result.ok();

        // 如果没有获取到，说明没有发送或者超时，则重新发送短信
        // 生成一个随机值，交给阿里云短信服务发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = msmService.send(param, phoneNum);
        if (isSend){
            // 发送成功，把发送成功的验证码放到redis里边
            // 设置超时时间
            redisTemplate.opsForValue().set(phoneNum, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("短信发送失败");
        }
    }
}
