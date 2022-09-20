package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
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
     * 查询赛事列表
     *
     * @param match 赛事
     * @return 赛事
     */
    @Override
    public List<Match> selectMatchList(Match match)
    {
        return baseMapper.selectMatchList(match);
    }
}
