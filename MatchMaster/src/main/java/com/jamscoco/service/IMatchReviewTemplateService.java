package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchReviewTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评审模板Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchReviewTemplateService extends IService<MatchReviewTemplate>
{
    /**
     * 查询评审模板列表
     *
     * @param matchReviewTemplate 评审模板
     * @return 评审模板集合
     */
    public List<MatchReviewTemplate> selectMatchReviewTemplateList(MatchReviewTemplate matchReviewTemplate);
}
