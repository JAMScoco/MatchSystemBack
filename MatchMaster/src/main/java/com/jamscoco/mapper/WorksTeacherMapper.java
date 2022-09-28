package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.WorksTeacher;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 指导老师Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Mapper
public interface WorksTeacherMapper extends BaseMapper<WorksTeacher>
{
    /**
     * 查询指导老师列表
     *
     * @param worksTeacher 指导老师
     * @return 指导老师集合
     */
    public List<WorksTeacher> selectWorksTeacherList(WorksTeacher worksTeacher);

    int getJoinNumber(String id);

    int insertRelation(@Param("teacherId") String teacherId, @Param("workId") String workId);

    List<WorksTeacher> getTeachersByWorkId(String id);
}
