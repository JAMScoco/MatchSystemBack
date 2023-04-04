package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.SchoolInfoUndergraduateStudentMapper;
import com.jamscoco.domain.SchoolInfoUndergraduateStudent;
import com.jamscoco.service.ISchoolInfoUndergraduateStudentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本科生信息Service业务层处理
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@Service
public class SchoolInfoUndergraduateStudentServiceImpl extends ServiceImpl<SchoolInfoUndergraduateStudentMapper,SchoolInfoUndergraduateStudent> implements ISchoolInfoUndergraduateStudentService
{

    /**
     * 查询本科生信息列表
     *
     * @param schoolInfoUndergraduateStudent 本科生信息
     * @return 本科生信息
     */
    @Override
    public List<SchoolInfoUndergraduateStudent> selectSchoolInfoUndergraduateStudentList(SchoolInfoUndergraduateStudent schoolInfoUndergraduateStudent)
    {
        return baseMapper.selectSchoolInfoUndergraduateStudentList(schoolInfoUndergraduateStudent);
    }

    @Override
    @Transactional
    public void saveUndergraduateStudent(List<Map<String, Object>> allInfo) {
        baseMapper.clearTable();
        List<SchoolInfoUndergraduateStudent> list = new ArrayList<>();
        for (Map<String, Object> info : allInfo){
            SchoolInfoUndergraduateStudent student = new SchoolInfoUndergraduateStudent();
            student.setSno(String.valueOf(info.get("学号")).trim());
            student.setName(String.valueOf(info.get("姓名")).trim());
            student.setSex(String.valueOf(info.get("性别")).trim());
            student.setGrade(String.valueOf(info.get("年级")).trim());
            student.setYear(String.valueOf(info.get("学制")).trim());
            student.setLevel(String.valueOf(info.get("培养层次")).trim());
            student.setDept(String.valueOf(info.get("院系")).trim());
            student.setMajor(String.valueOf(info.get("专业")).trim());
            student.setClazz(String.valueOf(info.get("所在行政班级")).trim());
            list.add(student);
        }
        saveBatch(list);
    }
}
