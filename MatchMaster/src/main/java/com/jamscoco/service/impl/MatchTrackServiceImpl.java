package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.MatchCategory;
import com.jamscoco.domain.MatchGroup;
import com.jamscoco.mapper.MatchCategoryMapper;
import com.jamscoco.mapper.MatchGroupMapper;
import com.jamscoco.vo.TrackInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MatchTrackServiceImpl extends ServiceImpl<MatchTrackMapper, MatchTrack> implements IMatchTrackService {
    @Autowired
    private MatchGroupMapper matchGroupMapper;
    @Autowired
    private MatchCategoryMapper matchCategoryMapper;

    /**
     * 查询赛事对应的赛道信息列表
     *
     * @param matchTrack 赛事对应的赛道信息
     * @return 赛事对应的赛道信息
     */
    @Override
    public List<MatchTrack> selectMatchTrackList(MatchTrack matchTrack) {
        return baseMapper.selectMatchTrackList(matchTrack);
    }

    /**
     * 查询赛道组别类别信息
     * @param matchId
     * @return
     */
    @Override
    public List<MatchTrack> getTrackInfo(String matchId) {
        List<MatchTrack> trackList = baseMapper.selectList(new QueryWrapper<MatchTrack>().eq("match_id", matchId));
        for (MatchTrack matchTrack : trackList) {
            List<MatchGroup> groupList = matchGroupMapper.selectList(new QueryWrapper<MatchGroup>().eq("track_id", matchTrack.getId()));
            List<TrackInfoVo> groupChildrenList = new ArrayList<>();
            for (MatchGroup matchGroup : groupList) {
                TrackInfoVo trackInfoVoGroup = new TrackInfoVo();
                trackInfoVoGroup.setTrackId(matchTrack.getId());
                trackInfoVoGroup.setName(matchGroup.getName());
                groupChildrenList.add(trackInfoVoGroup);
            }
            List<MatchCategory> categoryList = matchCategoryMapper.selectList(new QueryWrapper<MatchCategory>().eq("track_id", matchTrack.getId()));
            List<TrackInfoVo> categoryChildrenList = new ArrayList<>();
            for (MatchCategory matchCategory : categoryList) {
                TrackInfoVo trackInfoVoCategory = new TrackInfoVo();
                trackInfoVoCategory.setTrackId(matchTrack.getId());
                trackInfoVoCategory.setName(matchCategory.getName());
                categoryChildrenList.add(trackInfoVoCategory);
            }

            TrackInfoVo trackInfoVo1 = new TrackInfoVo();
            trackInfoVo1.setTrackId(matchTrack.getId());
            trackInfoVo1.setName("组别");
            trackInfoVo1.setChildren(groupChildrenList);
            TrackInfoVo trackInfoVo2 = new TrackInfoVo();
            trackInfoVo2.setTrackId(matchTrack.getId());
            trackInfoVo2.setName("类别");
            trackInfoVo2.setChildren(categoryChildrenList);

            List<TrackInfoVo> childrenList = new ArrayList<>();
            childrenList.add(trackInfoVo1);
            childrenList.add(trackInfoVo2);
            matchTrack.setChildren(childrenList);
        }

        return trackList;

    }
}
