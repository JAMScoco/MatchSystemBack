package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchRecommendQuota;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 推荐的作品名额Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchRecommendQuotaService extends IService<MatchRecommendQuota>
{
    /**
     * 查询推荐的作品名额列表
     *
     * @param matchRecommendQuota 推荐的作品名额
     * @return 推荐的作品名额集合
     */
    public List<MatchRecommendQuota> selectMatchRecommendQuotaList(MatchRecommendQuota matchRecommendQuota);
}
