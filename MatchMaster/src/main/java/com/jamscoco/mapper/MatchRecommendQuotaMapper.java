package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchRecommendQuota;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 推荐的作品名额Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchRecommendQuotaMapper extends BaseMapper<MatchRecommendQuota>
{
    /**
     * 查询推荐的作品名额列表
     *
     * @param matchRecommendQuota 推荐的作品名额
     * @return 推荐的作品名额集合
     */
    public List<MatchRecommendQuota> selectMatchRecommendQuotaList(MatchRecommendQuota matchRecommendQuota);

}
