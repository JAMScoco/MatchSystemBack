package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.SchoolInfoUndergraduateStudent;
import com.jamscoco.mapper.SchoolInfoUndergraduateStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.SchoolInfoMapper;
import com.jamscoco.domain.SchoolInfo;
import com.jamscoco.service.ISchoolInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学院信息Service业务层处理
 *
 * @author jamscoco
 * @date 2023-04-01
 */
@Service
public class SchoolInfoServiceImpl extends ServiceImpl<SchoolInfoMapper, SchoolInfo> implements ISchoolInfoService {


    @Autowired
    private SchoolInfoUndergraduateStudentMapper undergraduateStudentMapper;
    /**
     * 查询学院信息列表
     *
     * @param schoolInfo 学院信息
     * @return 学院信息
     */
    @Override
    public List<SchoolInfo> selectSchoolInfoList(SchoolInfo schoolInfo) {
        return baseMapper.selectSchoolInfoList(schoolInfo);
    }

    @Transactional
    @Override
    public void saveInfo(List<Map<String, Object>> allInfo) {
        baseMapper.clearTable();
        for (Map<String, Object> info : allInfo) {
            String deptName = String.valueOf(info.get("院系")).trim();
            //检查院系是否存在
            Long sysDeptId = baseMapper.queryDeptSysIdByName(deptName);
            if (null == sysDeptId){
                baseMapper.insetDeptSys(deptName);
            }
            SchoolInfo dept = baseMapper.selectOne(new QueryWrapper<SchoolInfo>().eq("name", deptName));
            if (null == dept){
                dept = new SchoolInfo();
                dept.setName(deptName);
                dept.setLevel(1);
                dept.setDelFlag("0");
                baseMapper.insert(dept);
            }
            String majorName = String.valueOf(info.get("专业")).trim();
            SchoolInfo major = baseMapper.selectOne(new QueryWrapper<SchoolInfo>().eq("name", majorName));
            if(null == major){
                major = new SchoolInfo();
                major.setName(majorName);
                major.setLevel(2);
                major.setDelFlag("0");
                major.setParentId(dept.getId());
                baseMapper.insert(major);
            }
            String className = String.valueOf(info.get("所在行政班")).trim();
            SchoolInfo clazz = baseMapper.selectOne(new QueryWrapper<SchoolInfo>().eq("name", className));
            if(null == clazz){
                clazz = new SchoolInfo();
                clazz.setName(className);
                clazz.setLevel(3);
                clazz.setDelFlag("0");
                clazz.setParentId(major.getId());
                baseMapper.insert(clazz);
            }
        }
    }

    @Override
    public int saveFileRecord(String name, String path) {
        return baseMapper.updateFilePath(name,path);
    }

    @Override
    public String getFilePathByName(String name) {
        return baseMapper.selectFilePathByName(name);
    }


}
