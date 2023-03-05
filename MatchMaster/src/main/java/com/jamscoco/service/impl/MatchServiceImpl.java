package com.jamscoco.service.impl;

import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.*;
import com.jamscoco.dto.MatchFileDto;
import com.jamscoco.mapper.*;
import com.jamscoco.vo.FileInfoVo;
import com.jamscoco.vo.IndexInfoVo;
import com.jamscoco.vo.MatchTimesVo;
import com.jamscoco.vo.MatchTracksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.service.IMatchService;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询大赛相关文件
     *
     * @param match
     * @return
     */
    @Override
    public List<FileInfoVo> getFiles(Match match) {
        FileInfoVo fileInfoVo1 = new FileInfoVo();
        fileInfoVo1.setName("大赛通知");
        fileInfoVo1.setFileName(match.getCompetitionNotice());
        FileInfoVo fileInfoVo2 = new FileInfoVo();
        fileInfoVo2.setName("评审规则");
        fileInfoVo2.setFileName(match.getReviewRules());
        FileInfoVo fileInfoVo3 = new FileInfoVo();
        fileInfoVo3.setName("学生操作手册");
        fileInfoVo3.setFileName(match.getStudentOperationManual());
        FileInfoVo fileInfoVo4 = new FileInfoVo();
        fileInfoVo4.setName("大赛指南");
        fileInfoVo4.setFileName(match.getCompetitionGuide());
        FileInfoVo fileInfoVo5 = new FileInfoVo();
        fileInfoVo5.setName("院级校级操作手册");
        fileInfoVo5.setFileName(match.getCollegeSchoolOperationManual());
        FileInfoVo fileInfoVo6 = new FileInfoVo();
        fileInfoVo6.setName("商业合作邀请函");
        fileInfoVo6.setFileName(match.getBusinessCooperationInvitation());
        FileInfoVo fileInfoVo7 = new FileInfoVo();
        fileInfoVo7.setName("大赛评审手册");
        fileInfoVo7.setFileName(match.getCompetitionReviewManual());
        ArrayList<FileInfoVo> voArrayList = new ArrayList<>();
        voArrayList.add(fileInfoVo1);
        voArrayList.add(fileInfoVo2);
        voArrayList.add(fileInfoVo3);
        voArrayList.add(fileInfoVo4);
        voArrayList.add(fileInfoVo5);
        voArrayList.add(fileInfoVo6);
        voArrayList.add(fileInfoVo7);
        return voArrayList;
    }

    /**
     * 新增赛事文件
     *
     * @param matchFileDto
     * @return
     */
    @Override
    public boolean addMatchFile(MatchFileDto matchFileDto) {
        return baseMapper.addMatchFile(matchFileDto) > 0;
    }

    /**
     * 删除赛事文件
     *
     * @param matchFileDto
     * @return
     */
    @Override
    public boolean delMatchFile(MatchFileDto matchFileDto) {
        return baseMapper.delMatchFile(matchFileDto) > 0;
    }

    @Transactional
    @Override
    public Integer queryRecommendNum(String matchId, String dept) {
        Integer quota = baseMapper.selectRecommendNum(matchId, dept);
        if (quota == null){
            initRecommendNum(matchId, dept);
        }
        return quota == null ? 0 : quota;
    }

    @Transactional
    @Override
    public Integer queryReviewCount(String matchId, Long dept) {
        Integer reviewCount = baseMapper.selectReviewCount(matchId, String.valueOf(dept));
        if (reviewCount == null){
            initReviewCount(matchId, dept);
        }
        return reviewCount == null ? 0 : reviewCount;
    }

    @Transactional
    @Override
    public Integer updateReviewCount(String matchId, Long deptId, Integer reviewCount) {
        return baseMapper.updateReviewCount(matchId, String.valueOf(deptId), reviewCount);
    }

    private void initReviewCount(String matchId, Long dept) {
        String id = UUID.randomUUID().toString().replace("-", "");
        baseMapper.insertReviewCount(matchId,String.valueOf(dept),id);
    }

    private void initRecommendNum(String matchId, String dept) {
        String id = UUID.randomUUID().toString().replace("-", "");
        baseMapper.insertRecommendNum(matchId,dept,id);
    }

    @Transactional
    @Override
    public int saveRecommendNum(String matchId, String dept, Integer quota) {
        return baseMapper.updateRecommendNum(matchId, dept, quota);
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
