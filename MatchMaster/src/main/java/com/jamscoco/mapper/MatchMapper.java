package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.Match;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
     * 查询赛事列表
     *
     * @param match 赛事
     * @return 赛事集合
     */
    public List<Match> selectMatchList(Match match);

}
