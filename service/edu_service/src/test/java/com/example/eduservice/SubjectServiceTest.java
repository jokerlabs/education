package com.example.eduservice;

import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.mapper.EduSubjectMapper;
import com.example.eduservice.service.EduSubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SubjectServiceTest {
    @Autowired
    EduSubjectMapper subjectMapper;

    @Autowired
    EduSubjectService subjectService;

    @Test
    public void getSubject() {
//        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", "0");
//
//        Map<EduSubject, List<EduSubject>> listMap = new HashMap<>();
//
//        List<EduSubject> oneList = subjectMapper.selectList(wrapper);
//        System.out.println(oneList);
//
//        for (EduSubject oneSubject: oneList) {
//            List<EduSubject> twoList = subjectMapper.selectList(
//                    new QueryWrapper<EduSubject>().eq("parent_id", oneSubject.getId()));
//            listMap.put(oneSubject, twoList);
//        }
//        System.out.println(listMap);

        List<OneSubject> subject = subjectService.getSubjects();
        System.out.println(subject);
    }
}
