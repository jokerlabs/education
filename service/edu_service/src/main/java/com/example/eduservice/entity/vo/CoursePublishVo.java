package com.example.eduservice.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoursePublishVo {
    private static final long serialVersionUID=1L;

    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;

    private String description;
}
