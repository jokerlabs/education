package com.example.baseservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor     // 生成有参数构造方法
@NoArgsConstructor      // 生成无参构造方法
@Data
public class MyException extends RuntimeException {
    private Integer code;     // 状态码

    private String msg;   // 信息
}
