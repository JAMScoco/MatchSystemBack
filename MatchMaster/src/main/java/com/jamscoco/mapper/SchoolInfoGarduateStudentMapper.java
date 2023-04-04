package com.jamscoco.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.jamscoco.domain.SchoolInfoGarduateStudent;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 研究生Mapper接口
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@Mapper
public interface SchoolInfoGarduateStudentMapper extends BaseMapper<SchoolInfoGarduateStudent>
{
    /**
     * 查询研究生列表
     *
     * @param schoolInfoGarduateStudent 研究生
     * @return 研究生集合
     */
    public List<SchoolInfoGarduateStudent> selectSchoolInfoGarduateStudentList(SchoolInfoGarduateStudent schoolInfoGarduateStudent);

    @InterceptorIgnore(blockAttack = "true")
    void clearTable();
}
