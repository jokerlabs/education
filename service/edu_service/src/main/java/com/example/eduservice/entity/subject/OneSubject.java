package com.example.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * 一级目录
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children;
}
