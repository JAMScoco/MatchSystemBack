package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.*;
import com.jamscoco.mapper.*;
import com.jamscoco.vo.IndexInfoVo;
import com.jamscoco.vo.MatchTimesVo;
import com.jamscoco.vo.MatchTracksVo;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.service.IMatchService;

import static cn.hutool.core.date.DatePattern.NORM_DATE_PATTERN;

/**
 * 赛事Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@Service
public class MatchServiceImpl extends ServiceImpl<MatchMapper, Match> implements IMatchService {
    @Autowired
    private MatchCarouselMapper carouselMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private MatchTrackMapper trackMapper;

    @Autowired
    private MatchGroupMapper groupMapper;

    @Autowired
    private MatchCategoryMapper categoryMapper;

    /**
     * 查询赛事列表（不包括当前正在进行的赛事）
     *
     * @param match 赛事
     * @return 赛事（不包括当前正在进行的赛事）
     */
    @Override
    public List<Match> selectHistoryMatchList(Match match) {
        return baseMapper.selectHistoryMatchList(match);
    }

    @Override
    public Match getCurrentMatch() {
        return baseMapper.selectOne(new QueryWrapper<Match>().ge("end_time", new Date()));
    }

    @Override
    public IndexInfoVo getIndexInfo(Match currentMatch) {
        IndexInfoVo indexInfo = new IndexInfoVo();
        //设置基本信息
        indexInfo.setMatchInfo(currentMatch);
        //设置轮播图
        indexInfo.setCarouselList(getCarouselList(currentMatch.getId()));
        //设置赛事动态，限制三条
        indexInfo.setMatchNewsList(getMatchNewsList(currentMatch.getId()));
        //设置院系动态，限制三条
        indexInfo.setDepartmentNewsList(getDepartmentNewsList());
        //设置时间线
        indexInfo.setMatchTimes(getMatchTimes(currentMatch));
        //设置赛道信息
        indexInfo.setMatchTracks(getMatchTracks(currentMatch.getId()));
        return indexInfo;
    }

    private List<MatchTracksVo> getMatchTracks(String matchId) {
        List<MatchTracksVo> result = new ArrayList<>();
        QueryWrapper<MatchTrack> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId);
        List<MatchTrack> trackList = trackMapper.selectList(queryWrapper);
        for (MatchTrack track : trackList) {
            MatchTracksVo tracksVo = new MatchTracksVo();
            tracksVo.setName(track.getName());
            tracksVo.setGroups(getGroups(track.getId()));
            tracksVo.setCategories(getCategories(track.getId()));
            tracksVo.setAwardContent(track.getAward());
            result.add(tracksVo);
        }
        return result;
    }

    private String getCategories(String trackId) {
        QueryWrapper<MatchCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("track_id", trackId);
        List<MatchCategory> categoryList = categoryMapper.selectList(queryWrapper);
        StringBuilder builder = new StringBuilder();
        for (MatchCategory category : categoryList) {
            builder.append(category.getName()).append("、");
        }
        if (categoryList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private String getGroups(String trackId) {
        QueryWrapper<MatchGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("track_id", trackId);
        List<MatchGroup> groupList = groupMapper.selectList(queryWrapper);
        StringBuilder builder = new StringBuilder();
        for (MatchGroup group : groupList) {
            builder.append(group.getName()).append("、");
        }
        if (groupList.size() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private List<MatchTimesVo> getMatchTimes(Match match) {
        Date now = new Date();
        List<MatchTimesVo> result = new ArrayList<>();
        MatchTimesVo startSubmitTime = getMatchTimesVo(match.getStartSubmitTime(), now);
        startSubmitTime.setContent("开始报名");
        MatchTimesVo endSubmitTime = getMatchTimesVo(match.getEndSubmitTime(), now);
        endSubmitTime.setContent("报名截止");
        MatchTimesVo startReviewTimeDepartment = getMatchTimesVo(match.getStartReviewTimeDepartment(), now);
        startReviewTimeDepartment.setContent("院级评审开始");
        MatchTimesVo endReviewTimeDepartment = getMatchTimesVo(match.getEndReviewTimeDepartment(), now);
        endReviewTimeDepartment.setContent("院级评审结束");
        MatchTimesVo startReviewTimeSchool = getMatchTimesVo(match.getStartReviewTimeSchool(), now);
        startReviewTimeSchool.setContent("校级评审开始");
        MatchTimesVo endReviewTimeSchool = getMatchTimesVo(match.getEndReviewTimeSchool(), now);
        endReviewTimeSchool.setContent("校级评审结束");
        result.add(startSubmitTime);
        result.add(endSubmitTime);
        result.add(startReviewTimeDepartment);
        result.add(endReviewTimeDepartment);
        result.add(startReviewTimeSchool);
        result.add(endReviewTimeSchool);
        return result;
    }

    private MatchTimesVo getMatchTimesVo(Date time, Date now) {
        MatchTimesVo result = new MatchTimesVo();
        int compare = DateUtil.compare(now, time);
        if (compare > 0) {
            result.setType(MatchTimesVo.TYPE);
            result.setColor(MatchTimesVo.COLOR);
        }
        result.setTimestamp(DateUtil.format(time, NORM_DATE_PATTERN));
        return result;
    }

    private List<News> getDepartmentNewsList() {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "picture", "create_time");
        queryWrapper.eq("type", 1L);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 3");
        return newsMapper.selectList(queryWrapper);
    }

    private List<News> getMatchNewsList(String matchId) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title", "picture", "create_time");
        queryWrapper.eq("type", 0L);
        queryWrapper.eq("owner", matchId);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 3");
        return newsMapper.selectList(queryWrapper);
    }

    private List<MatchCarousel> getCarouselList(String matchId) {
        QueryWrapper<MatchCarousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("match_id", matchId);
        queryWrapper.orderByAsc("sort");
        return carouselMapper.selectList(queryWrapper);
    }
}
