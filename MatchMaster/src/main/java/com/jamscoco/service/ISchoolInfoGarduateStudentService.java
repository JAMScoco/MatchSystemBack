package com.jamscoco.service;

import java.util.List;
import java.util.Map;

import com.jamscoco.domain.SchoolInfoGarduateStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 研究生Service接口
 *
 * @author jamscoco
 * @date 2023-04-04
 */
public interface ISchoolInfoGarduateStudentService extends IService<SchoolInfoGarduateStudent>
{
    /**
     * 查询研究生列表
     *
     * @param schoolInfoGarduateStudent 研究生
     * @return 研究生集合
     */
    public List<SchoolInfoGarduateStudent> selectSchoolInfoGarduateStudentList(SchoolInfoGarduateStudent schoolInfoGarduateStudent);

    void saveGraduateStudent(List<Map<String, Object>> allInfo);
}
