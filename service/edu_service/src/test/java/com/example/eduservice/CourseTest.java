package com.example.eduservice;

import com.example.eduservice.entity.course.ChapterVo;
import com.example.eduservice.service.EduChapterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseTest {
    @Autowired
    EduChapterService chapterService;
    @Test
    void chapterTest(){
        List<ChapterVo> chapters = chapterService.getChapters("1271346677768372225");
        System.out.println(chapters);
    }
}
