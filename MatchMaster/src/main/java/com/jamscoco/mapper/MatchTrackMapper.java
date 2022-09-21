package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchTrack;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 赛事对应的赛道信息Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchTrackMapper extends BaseMapper<MatchTrack>
{
    /**
     * 查询赛事对应的赛道信息列表
     *
     * @param matchTrack 赛事对应的赛道信息
     * @return 赛事对应的赛道信息集合
     */
    public List<MatchTrack> selectMatchTrackList(MatchTrack matchTrack);

}
