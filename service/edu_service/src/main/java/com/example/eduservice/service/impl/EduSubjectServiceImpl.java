package com.example.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.eduservice.entity.EduSubject;
import com.example.eduservice.entity.excel.SubjectData;
import com.example.eduservice.entity.subject.OneSubject;
import com.example.eduservice.entity.subject.TwoSubject;
import com.example.eduservice.listener.SubjectExcelListener;
import com.example.eduservice.mapper.EduSubjectMapper;
import com.example.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jiaxq
 * @since 2020-06-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据库中获取课程列表，树结构
     */
    @Override
    public List<OneSubject> getSubjects() {
        // 查找一级分类和二级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");

        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> oneList = this.list(oneWrapper);
        List<EduSubject> twoList = this.list(twoWrapper);


        List<OneSubject> oneSubjects = new ArrayList<>();

        // 遍历一级分类, 封装到list集合中
        for (EduSubject one: oneList) {
            OneSubject oneSubject = new OneSubject();

            // oneSubject.setId(one.getId());
            // oneSubject.setTitle(one.getTitle());
            BeanUtils.copyProperties(one, oneSubject);


            List<TwoSubject> twoSubjects = new ArrayList<>();

            // 遍历二级分类，封装到list集合中，再封装到一级分类中
            for (EduSubject two: twoList) {
                if (two.getParentId().equals(one.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    // twoSubject.setId(two.getId());
                    // twoSubject.setTitle(two.getTitle());
                    BeanUtils.copyProperties(two, twoSubject);
                    twoSubjects.add(twoSubject);
                }
            }

            // 把二级分类封装到一级分类中
            oneSubject.setChildren(twoSubjects);

            // 把一级分类封装到list集合中
            oneSubjects.add(oneSubject);
        }
        return oneSubjects;
    }
}
