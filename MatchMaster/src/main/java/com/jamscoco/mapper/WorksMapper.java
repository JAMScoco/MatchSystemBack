package com.jamscoco.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.jamscoco.domain.Works;
import com.jamscoco.dto.SortMoveDto;
import com.jamscoco.vo.WorkInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 作品Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Mapper
public interface WorksMapper extends BaseMapper<Works>
{
    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品集合
     */
    public List<WorkInfo> selectWorksList(Works works);

    WorkInfo getWorkInfoById(String id);

    int check(Works works);

    List<String> waitReviewWorksDepartment(@Param("deptId")Long deptId, @Param("matchId") String matchId);

    List<String> waitReviewWorksSchool(String matchId);

    int updateDepartmentAverageScore(@Param("id")String id,@Param("score") Double departmentAverageScore);

    int updateSchoolAverageScore(@Param("id")String id,@Param("score") Double schoolAverageScore);

    int updateDepartmentSort(@Param("id")String id,@Param("sort")int sort);

    int updateSchoolSort(@Param("id")String id,@Param("sort")int sort);

    String selectNextIdOnUpSchoolSort(String workId);
    String selectNextIdOnDownSchoolSort(String workId);

    String selectNextIdOnUpDepartment(String workId);
    String selectNextIdOnDownDepartment(String workId);

    @InterceptorIgnore(blockAttack = "true")
    int exchangeSchoolSort(@Param("id1")String workId,@Param("id2") String nextId);
    @InterceptorIgnore(blockAttack = "true")
    int exchangeDepartmentSort(@Param("id1")String workId,@Param("id2") String nextId);
}
