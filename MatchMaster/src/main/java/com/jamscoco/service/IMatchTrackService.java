package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchTrack;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jamscoco.vo.TrackInfoVo;

/**
 * 赛事对应的赛道信息Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchTrackService extends IService<MatchTrack>
{
    /**
     * 查询赛事对应的赛道信息列表
     *
     * @param matchTrack 赛事对应的赛道信息
     * @return 赛事对应的赛道信息集合
     */
    public List<MatchTrack> selectMatchTrackList(MatchTrack matchTrack);

    /**
     * 查询赛道组别类别信息
     * @param matchId
     * @return
     */
    List<MatchTrack> getTrackInfo(String matchId);
}
