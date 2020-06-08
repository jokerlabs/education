package com.example.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherQuery implements Serializable {
    @ApiModelProperty("教师名字")
    private String name;   // 教师名称

    @ApiModelProperty("职位级别")
    private Integer level; // 职位级别

    @ApiModelProperty("开始时间")
    private String begin;  // 开始时间

    @ApiModelProperty("结束时间")
    private String end;    // 结束时间
}
