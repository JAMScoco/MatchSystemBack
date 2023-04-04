package com.jamscoco.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.jamscoco.domain.SchoolInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 学院信息Mapper接口
 *
 * @author jamscoco
 * @date 2023-04-01
 */
@Mapper
public interface SchoolInfoMapper extends BaseMapper<SchoolInfo>
{
    /**
     * 查询学院信息列表
     *
     * @param schoolInfo 学院信息
     * @return 学院信息集合
     */
    public List<SchoolInfo> selectSchoolInfoList(SchoolInfo schoolInfo);

    Long queryDeptSysIdByName(String deptName);

    void insetDeptSys(String deptName);

    int updateFilePath(@Param("name") String name, @Param("path") String path);

    @InterceptorIgnore(blockAttack = "true")
    void clearTable();

    String selectFilePathByName(String name);
}
