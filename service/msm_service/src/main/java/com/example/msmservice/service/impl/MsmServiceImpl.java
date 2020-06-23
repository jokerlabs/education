package com.example.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信服务
     *
     * @param param    包含的信息
     * @param phoneNum 电话号
     */
    @Override
    public boolean send(Map<String, Object> param, String phoneNum) {
        if (StringUtils.isEmpty(phoneNum)) return false;
        //初始化ascClient需要的几个参数
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4GCcxKqDFy2mDkXxdRvD",
                "w8ojhd24OARNuQE6SY6g7MYjNe6ZAD");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，
        request.putQueryParameter("PhoneNumbers", phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName", "220实验室");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.putQueryParameter("TemplateCode", "SMS_193517262");
        //可选:模板中的变量替换JSON串
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();

        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
