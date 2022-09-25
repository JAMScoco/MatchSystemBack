package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksTeacherMapper;
import com.jamscoco.domain.WorksTeacher;
import com.jamscoco.service.IWorksTeacherService;

/**
 * 指导老师Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Service
public class WorksTeacherServiceImpl extends ServiceImpl<WorksTeacherMapper,WorksTeacher> implements IWorksTeacherService
{

    /**
     * 查询指导老师列表
     *
     * @param worksTeacher 指导老师
     * @return 指导老师
     */
    @Override
    public List<WorksTeacher> selectWorksTeacherList(WorksTeacher worksTeacher)
    {
        return baseMapper.selectWorksTeacherList(worksTeacher);
    }
}
