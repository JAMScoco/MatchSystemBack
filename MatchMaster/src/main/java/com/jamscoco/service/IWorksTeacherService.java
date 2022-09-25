package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.WorksTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 指导老师Service接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
public interface IWorksTeacherService extends IService<WorksTeacher>
{
    /**
     * 查询指导老师列表
     *
     * @param worksTeacher 指导老师
     * @return 指导老师集合
     */
    public List<WorksTeacher> selectWorksTeacherList(WorksTeacher worksTeacher);
}
