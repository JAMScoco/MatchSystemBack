package com.jamscoco.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.jamscoco.domain.SchoolInfoUndergraduateStudent;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 本科生信息Mapper接口
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@Mapper
public interface SchoolInfoUndergraduateStudentMapper extends BaseMapper<SchoolInfoUndergraduateStudent>
{
    /**
     * 查询本科生信息列表
     *
     * @param schoolInfoUndergraduateStudent 本科生信息
     * @return 本科生信息集合
     */
    public List<SchoolInfoUndergraduateStudent> selectSchoolInfoUndergraduateStudentList(SchoolInfoUndergraduateStudent schoolInfoUndergraduateStudent);

    @InterceptorIgnore(blockAttack = "true")
    void clearTable();
}
