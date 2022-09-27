package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.WorksMember;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 参与成员Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-26
 */
@Mapper
public interface WorksMemberMapper extends BaseMapper<WorksMember>
{
    /**
     * 查询参与成员列表
     *
     * @param worksMember 参与成员
     * @return 参与成员集合
     */
    public List<WorksMember> selectWorksMemberList(WorksMember worksMember);

    int getJoinNumber(String id);

    int insertRelation(@Param("memberId") String memberId, @Param("workId") String workId);
}
