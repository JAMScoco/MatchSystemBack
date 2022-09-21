package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchReviewTemplateMapper;
import com.jamscoco.domain.MatchReviewTemplate;
import com.jamscoco.service.IMatchReviewTemplateService;

/**
 * 评审模板Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchReviewTemplateServiceImpl extends ServiceImpl<MatchReviewTemplateMapper,MatchReviewTemplate> implements IMatchReviewTemplateService
{

    /**
     * 查询评审模板列表
     *
     * @param matchReviewTemplate 评审模板
     * @return 评审模板
     */
    @Override
    public List<MatchReviewTemplate> selectMatchReviewTemplateList(MatchReviewTemplate matchReviewTemplate)
    {
        return baseMapper.selectMatchReviewTemplateList(matchReviewTemplate);
    }
}
