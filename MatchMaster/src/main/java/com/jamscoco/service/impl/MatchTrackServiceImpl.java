package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.MatchCategory;
import com.jamscoco.domain.MatchGroup;
import com.jamscoco.domain.MatchReviewTemplate;
import com.jamscoco.mapper.MatchCategoryMapper;
import com.jamscoco.mapper.MatchGroupMapper;
import com.jamscoco.mapper.MatchReviewTemplateMapper;
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
    @Autowired
    private MatchReviewTemplateMapper matchReviewTemplateMapper;
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
                trackInfoVoGroup.setId(matchGroup.getId());
                trackInfoVoGroup.setTrackId(matchTrack.getId());
                trackInfoVoGroup.setName(matchGroup.getName());
                trackInfoVoGroup.setRemark(matchGroup.getRemark());
                groupChildrenList.add(trackInfoVoGroup);
            }
            List<MatchCategory> categoryList = matchCategoryMapper.selectList(new QueryWrapper<MatchCategory>().eq("track_id", matchTrack.getId()));
            List<TrackInfoVo> categoryChildrenList = new ArrayList<>();
            for (MatchCategory matchCategory : categoryList) {
                TrackInfoVo trackInfoVoCategory = new TrackInfoVo();
                trackInfoVoCategory.setId(matchCategory.getId());
                trackInfoVoCategory.setTrackId(matchTrack.getId());
                trackInfoVoCategory.setName(matchCategory.getName());
                trackInfoVoCategory.setRemark(matchCategory.getRemark());
                categoryChildrenList.add(trackInfoVoCategory);
            }

            TrackInfoVo groupInfo = new TrackInfoVo();
            groupInfo.setTrackId(matchTrack.getId());
            groupInfo.setName("组别");
            groupInfo.setChildren(groupChildrenList);
            TrackInfoVo categoryInfo = new TrackInfoVo();
            categoryInfo.setTrackId(matchTrack.getId());
            categoryInfo.setName("类别");
            categoryInfo.setChildren(categoryChildrenList);
            List<TrackInfoVo> childrenList = new ArrayList<>();
            childrenList.add(groupInfo);
            childrenList.add(categoryInfo);
            matchTrack.setChildren(childrenList);
        }
        return trackList;

    }

    /**
     * 查询赛道组别信息
     * @param matchId
     * @return
     */
    @Override
    public List<MatchTrack> getTrackInfoWithoutCategory(String matchId) {
        List<MatchTrack> trackList = baseMapper.selectList(new QueryWrapper<MatchTrack>().eq("match_id", matchId));
        for (MatchTrack matchTrack : trackList) {
            List<MatchGroup> groupList = matchGroupMapper.selectList(new QueryWrapper<MatchGroup>().eq("track_id", matchTrack.getId()));
            List<TrackInfoVo> groupChildrenList = new ArrayList<>();
            for (MatchGroup matchGroup : groupList) {
                TrackInfoVo trackInfoVoGroup = new TrackInfoVo();
                trackInfoVoGroup.setId(matchGroup.getId());
                trackInfoVoGroup.setTrackId(matchTrack.getId());
                trackInfoVoGroup.setName(matchGroup.getName());
                trackInfoVoGroup.setRemark(matchGroup.getRemark());
                MatchReviewTemplate template =  new MatchReviewTemplate();
                template.setGroupId(matchGroup.getId());
                trackInfoVoGroup.setHasTemplate(!matchReviewTemplateMapper.selectMatchReviewTemplateList(template).isEmpty());
                groupChildrenList.add(trackInfoVoGroup);
            }
            TrackInfoVo groupInfo = new TrackInfoVo();
            groupInfo.setTrackId(matchTrack.getId());
            groupInfo.setName("组别");
            groupInfo.setChildren(groupChildrenList);
            List<TrackInfoVo> childrenList = new ArrayList<>();
            childrenList.add(groupInfo);
            matchTrack.setChildren(childrenList);
        }
        return trackList;
    }
}
