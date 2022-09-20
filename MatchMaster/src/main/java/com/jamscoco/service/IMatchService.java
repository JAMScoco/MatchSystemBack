package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.Match;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 赛事Service接口
 *
 * @author jamscoco
 * @date 2022-09-20
 */
public interface IMatchService extends IService<Match>
{
    /**
     * 查询赛事列表
     *
     * @param match 赛事
     * @return 赛事集合
     */
    public List<Match> selectMatchList(Match match);
}
