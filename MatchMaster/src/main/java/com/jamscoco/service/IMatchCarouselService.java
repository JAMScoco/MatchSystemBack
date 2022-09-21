package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchCarousel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 发布赛事的轮播图Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchCarouselService extends IService<MatchCarousel>
{
    /**
     * 查询发布赛事的轮播图列表
     *
     * @param matchCarousel 发布赛事的轮播图
     * @return 发布赛事的轮播图集合
     */
    public List<MatchCarousel> selectMatchCarouselList(MatchCarousel matchCarousel);
}
