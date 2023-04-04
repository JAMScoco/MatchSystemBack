package com.jamscoco.service;

import java.util.List;
import java.util.Map;

import com.jamscoco.domain.SchoolInfoUndergraduateStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 本科生信息Service接口
 *
 * @author jamscoco
 * @date 2023-04-04
 */
public interface ISchoolInfoUndergraduateStudentService extends IService<SchoolInfoUndergraduateStudent>
{
    /**
     * 查询本科生信息列表
     *
     * @param schoolInfoUndergraduateStudent 本科生信息
     * @return 本科生信息集合
     */
    public List<SchoolInfoUndergraduateStudent> selectSchoolInfoUndergraduateStudentList(SchoolInfoUndergraduateStudent schoolInfoUndergraduateStudent);

    void saveUndergraduateStudent(List<Map<String, Object>> allInfo);
}
