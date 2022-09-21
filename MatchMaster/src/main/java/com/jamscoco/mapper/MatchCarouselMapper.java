package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchCarousel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 发布赛事的轮播图Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchCarouselMapper extends BaseMapper<MatchCarousel>
{
    /**
     * 查询发布赛事的轮播图列表
     *
     * @param matchCarousel 发布赛事的轮播图
     * @return 发布赛事的轮播图集合
     */
    public List<MatchCarousel> selectMatchCarouselList(MatchCarousel matchCarousel);

}
