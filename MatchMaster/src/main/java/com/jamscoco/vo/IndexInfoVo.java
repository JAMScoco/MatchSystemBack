package com.jamscoco.vo;

import com.jamscoco.domain.Match;
import com.jamscoco.domain.MatchCarousel;
import com.jamscoco.domain.News;
import lombok.Data;

import java.util.List;

@Data
public class IndexInfoVo {
    //赛事基本信息
    private Match matchInfo;
    //轮播图列表
    private List<MatchCarousel> carouselList;
    //大赛动态列表
    private List<News> matchNewsList;
    //院系动态列表
    private List<News> departmentNewsList;
    //大赛时间流程列表
    private List<MatchTimesVo> matchTimes;
    //主页赛道显示列表
    private List<MatchTracksVo> matchTracks;
}
