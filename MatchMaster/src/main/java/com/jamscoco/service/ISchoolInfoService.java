package com.jamscoco.service;

import java.util.List;
import java.util.Map;

import com.jamscoco.domain.SchoolInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 学院信息Service接口
 *
 * @author jamscoco
 * @date 2023-04-01
 */
public interface ISchoolInfoService extends IService<SchoolInfo>
{
    /**
     * 查询学院信息列表
     *
     * @param schoolInfo 学院信息
     * @return 学院信息集合
     */
    public List<SchoolInfo> selectSchoolInfoList(SchoolInfo schoolInfo);

    void saveInfo(List<Map<String, Object>> allInfo);

    int saveFileRecord(String name, String path);

    String getFilePathByName(String name);
}
