package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchRecommendQuotaMapper;
import com.jamscoco.domain.MatchRecommendQuota;
import com.jamscoco.service.IMatchRecommendQuotaService;

/**
 * 推荐的作品名额Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchRecommendQuotaServiceImpl extends ServiceImpl<MatchRecommendQuotaMapper,MatchRecommendQuota> implements IMatchRecommendQuotaService
{

    /**
     * 查询推荐的作品名额列表
     *
     * @param matchRecommendQuota 推荐的作品名额
     * @return 推荐的作品名额
     */
    @Override
    public List<MatchRecommendQuota> selectMatchRecommendQuotaList(MatchRecommendQuota matchRecommendQuota)
    {
        return baseMapper.selectMatchRecommendQuotaList(matchRecommendQuota);
    }
}
