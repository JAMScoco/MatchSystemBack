package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchTrackMapper;
import com.jamscoco.domain.MatchTrack;
import com.jamscoco.service.IMatchTrackService;

/**
 * 赛事对应的赛道信息Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchTrackServiceImpl extends ServiceImpl<MatchTrackMapper,MatchTrack> implements IMatchTrackService
{

    /**
     * 查询赛事对应的赛道信息列表
     *
     * @param matchTrack 赛事对应的赛道信息
     * @return 赛事对应的赛道信息
     */
    @Override
    public List<MatchTrack> selectMatchTrackList(MatchTrack matchTrack)
    {
        return baseMapper.selectMatchTrackList(matchTrack);
    }
}
