package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.Match;
import com.jamscoco.dto.MatchFileDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 赛事Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@Mapper
public interface MatchMapper extends BaseMapper<Match>
{
    /**
     * 查询赛事列表（不包括当前正在进行的赛事）
     *
     * @param match 赛事
     * @return 赛事集合
     */
    public List<Match> selectHistoryMatchList(Match match);

    /**
     * 新增赛事文件
     * @param matchFileDto
     * @return
     */
    int addMatchFile(MatchFileDto matchFileDto);

    /**
     * 删除赛事文件
     * @param matchFileDto
     * @return
     */
    int delMatchFile(MatchFileDto matchFileDto);

    Integer selectRecommendNum(@Param("matchId") String matchId, @Param("dept")String dept);

    int updateRecommendNum(@Param("matchId")String matchId, @Param("dept")String dept,@Param("quota")Integer quota);

    int insertRecommendNum(@Param("matchId") String matchId, @Param("dept")String dept,@Param("id") String id);

    Integer selectReviewCount(@Param("matchId") String matchId, @Param("dept")String dept);

    int insertReviewCount(@Param("matchId") String matchId, @Param("dept")String dept,@Param("id") String id);

    Integer updateReviewCount(@Param("matchId")String matchId, @Param("deptId")String deptId,@Param("reviewCount")Integer reviewCount);
}
