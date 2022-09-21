package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchCarouselMapper;
import com.jamscoco.domain.MatchCarousel;
import com.jamscoco.service.IMatchCarouselService;

/**
 * 发布赛事的轮播图Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchCarouselServiceImpl extends ServiceImpl<MatchCarouselMapper,MatchCarousel> implements IMatchCarouselService
{

    /**
     * 查询发布赛事的轮播图列表
     *
     * @param matchCarousel 发布赛事的轮播图
     * @return 发布赛事的轮播图
     */
    @Override
    public List<MatchCarousel> selectMatchCarouselList(MatchCarousel matchCarousel)
    {
        return baseMapper.selectMatchCarouselList(matchCarousel);
    }
}
