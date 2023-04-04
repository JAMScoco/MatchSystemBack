package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.SchoolInfoUndergraduateStudent;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.SchoolInfoGarduateStudentMapper;
import com.jamscoco.domain.SchoolInfoGarduateStudent;
import com.jamscoco.service.ISchoolInfoGarduateStudentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 研究生Service业务层处理
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@Service
public class SchoolInfoGarduateStudentServiceImpl extends ServiceImpl<SchoolInfoGarduateStudentMapper,SchoolInfoGarduateStudent> implements ISchoolInfoGarduateStudentService
{

    /**
     * 查询研究生列表
     *
     * @param schoolInfoGarduateStudent 研究生
     * @return 研究生
     */
    @Override
    public List<SchoolInfoGarduateStudent> selectSchoolInfoGarduateStudentList(SchoolInfoGarduateStudent schoolInfoGarduateStudent)
    {
        return baseMapper.selectSchoolInfoGarduateStudentList(schoolInfoGarduateStudent);
    }

    @Override
    @Transactional
    public void saveGraduateStudent(List<Map<String, Object>> allInfo) {
        baseMapper.clearTable();
        List<SchoolInfoGarduateStudent> list = new ArrayList<>();
        for (Map<String, Object> info : allInfo){
            SchoolInfoGarduateStudent student = new SchoolInfoGarduateStudent();
            student.setSno(String.valueOf(info.get("学号")).trim());
            student.setName(String.valueOf(info.get("姓名")).trim());
            student.setSex(String.valueOf(info.get("性别")).trim());
            student.setGrade(String.valueOf(info.get("年级")).trim());
            student.setDept(String.valueOf(info.get("学院名称")).trim());
            student.setMajor(String.valueOf(info.get("专业名称")).trim());
            student.setCode(String.valueOf(info.get("专业代码")).trim());
            list.add(student);
        }
        saveBatch(list);
    }
}
