package com.example.commonutils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 */
@Data
public class Result {
    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String, Object> data = new HashMap<>();

    // 构造方法私有化
    private Result() {
    }

    /**
     * 成功静态方法
     */
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    /**
     * 失败静态方法
     */
    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.Error);
        r.setMessage("失败");
        return r;
    }

    // 链式编程 this.code().message().data()

    /**
     * 添加消息
     */
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 添加状态码
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 添加键值对数据
     */
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 添加集合数据
     */
    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
