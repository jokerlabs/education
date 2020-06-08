package com.example.eduservice.controller;

import com.example.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
@CrossOrigin  // 解决跨域问题
public class EduLoginController {
    // login
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token", "admin");
    }

    // info
    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
