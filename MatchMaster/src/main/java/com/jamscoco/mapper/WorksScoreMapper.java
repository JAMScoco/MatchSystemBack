package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.WorksScore;
import com.jamscoco.dto.ScoreDto;
import com.jamscoco.dto.ScoreSubmitDto;
import com.jamscoco.vo.ScoreVo;
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
     * @param scoreDto 检索条件
     * @return 评审分值集合
     */
    public List<ScoreVo> selectWorksScoreList(ScoreDto scoreDto);

    Integer check(@Param("id")String id,@Param("type") int i);

    List<ScoreVo> getReviewDetails(@Param("matchId") String matchId,@Param("type") Long type);

    int setScoreDetails(ScoreSubmitDto scoreSubmitDto);
}
