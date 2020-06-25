package com.example.ucenterservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.baseservice.exception.MyException;
import com.example.commonutils.JwtUtils;
import com.example.ucenterservice.entity.UcenterMember;
import com.example.ucenterservice.service.UcenterMemberService;
import com.example.ucenterservice.utils.HttpClientUtils;
import com.example.ucenterservice.utils.WeixinConstantPropertisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter")
@CrossOrigin
public class WxApiController {
    @Autowired
    UcenterMemberService memberService;
    /**
     * 生成二维码并重定向到生成的二维码的地址
     */
    @GetMapping("/login")
    public String getWxCode() {
//        String url = "https://open/weixin.qq.com/connect/qrconnect?appid"
//                + WeixinConstantPropertisUtil.WX_OPEN_APP_ID
//                + "&response_type=code";
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";


        // 回调地址
        String redirectUrl = WeixinConstantPropertisUtil.WX_OPEN_REDIRECT_URL; // 获取服务器的重定向地址
        redirectUrl = URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);

        // 防止csrf攻击
        String state = "helen";
        System.out.println("state =" + state);

        //设置%s里面值
        String url = String.format(
                baseUrl,
                WeixinConstantPropertisUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state
        );

        //重定向到请求微信地址里面
        return "redirect:" + url;
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/wx/callback")
    public String callback(String code, String state) {
        //1. 获取扫码后的信息
        // 从redis中取出state, 和当前传入的state做比较，一致就放行
        System.out.println("code:" + code);
        System.out.println("state:" + state);
        // --------------------------------------------------------------------------------
        //          2. 向认证服务器发送请求换取access_token
        // --------------------------------------------------------------------------------
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        // 传参, 拼接请求地址
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                WeixinConstantPropertisUtil.WX_OPEN_APP_ID,
                WeixinConstantPropertisUtil.WX_OPEN_APP_SECRET,
                code
        );
        String accessTokenInfo = null;

        // 使用httpclient发送请求，得到返回值access_token, openid
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo："+accessTokenInfo);
            // accessTokenInfo：{"access_token":"34_vmmCWFmdypSyNaMelBkhg4EAlCQEajR1yWJICuYbTaUQUuuHekKUbXjLk8lYELZ9G3Kzo06nk9YzLQ16CsIp-yYo8LRjJxYV5Wr56DFhEzQ","expires_in":7200,"refresh_token":"34_kUkCu3vUESvJt922mtC79upUBkWte5_02HYTWDFa9x-gc3gkg2LCbxz60L95fT-LE4XcfH7DMhON7ttGd-JDbQtCJzmv1-P-txu_VahYp5k","openid":"o3_SC5_cPmIQ-kz_UkVzg6oQZNkA","scope":"snsapi_login","unionid":"oWgGz1LGJNhoD1snKfBGRXEZnSic"}
        } catch (Exception e) {
            // e.printStackTrace();
            throw new MyException(20001, "获取access_token失败");
        }

        // 从accessTokenInfo中取出access_token, openid
        // 把accessTokenInfo字符串转成map获取access_token, openid
        Gson gson = new Gson();
        HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String) map.get("openid");

        // --------------------------------------------------------------------------------
        //          3. 使用access_token和openid获取用户信息
        // --------------------------------------------------------------------------------
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        String userInfoUrl = String.format(
                baseUserInfoUrl,
                accessToken,
                openid
        );

        String userInfo = null;
        try {
            userInfo = HttpClientUtils.get(userInfoUrl);
            System.out.println(userInfo);
            // {"openid":"o3_SC5_cPmIQ-kz_UkVzg6oQZNkA","nickname":"贾旭强","sex":1,"language":"zh_CN","city":"Xi'an","province":"Shaanxi","country":"CN","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTL32WqcHQq2lnMo72xeP2JIXtO6TpGlw1UxaDMA2o4fNjURaYAd74xeIgbBbtBBeick5xLvqw3CdBQ\/132","privilege":[],"unionid":"oWgGz1LGJNhoD1snKfBGRXEZnSic"}
        } catch (Exception e) {
            // e.printStackTrace();
            throw new MyException(20001, "获取用户信息失败");
        }

        HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
        String nickname = (String) userInfoMap.get("nickname");
        String headImgUrl = (String) userInfoMap.get("headimgurl");

        // 把信息添加到数据库中，根据openid判断数据库里是否存在相同的微信用户信息
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member= memberService.getOne(wrapper);
        if (member == null) {
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            member.setOpenid(openid);
            memberService.save(member);
        } else {
            member.setNickname(nickname);
            member.setAvatar(headImgUrl);
            memberService.updateById(member);
        }

        System.out.println(member.getId());
        // 使用jwt生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());


        return "redirect:http://localhost:3000?token=" + jwtToken;
    }
}
