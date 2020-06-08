package com.example.eduservice;

import com.example.eduservice.service.EduTeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MpTest {
    @Autowired
    EduTeacherService teacherService;

    @Test
    public void testList() {
        System.out.println(teacherService.list(null));
    }
}
