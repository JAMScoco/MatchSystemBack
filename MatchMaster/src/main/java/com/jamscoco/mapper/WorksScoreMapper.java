package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.WorksScore;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 评审分值Mapper接口
 *
 * @author jamscoco
 * @date 2023-02-17
 */
@Mapper
public interface WorksScoreMapper extends BaseMapper<WorksScore>
{
    /**
     * 查询评审分值列表
     *
     * @param worksScore 评审分值
     * @return 评审分值集合
     */
    public List<WorksScore> selectWorksScoreList(WorksScore worksScore);

    Integer check(@Param("id")String id,@Param("type") int i);
}
