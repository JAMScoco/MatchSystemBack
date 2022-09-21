package com.jamscoco.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchMapper;
import com.jamscoco.domain.Match;
import com.jamscoco.service.IMatchService;

/**
 * 赛事Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@Service
public class MatchServiceImpl extends ServiceImpl<MatchMapper,Match> implements IMatchService
{

    /**
     * 查询赛事列表（不包括当前正在进行的赛事）
     *
     * @param match 赛事
     * @return 赛事（不包括当前正在进行的赛事）
     */
    @Override
    public List<Match> selectHistoryMatchList(Match match)
    {
        return baseMapper.selectHistoryMatchList(match);
    }

    @Override
    public Match getCurrentMatch() {
        return baseMapper.selectOne(new QueryWrapper<Match>().ge("end_time", new Date()));
    }
}
