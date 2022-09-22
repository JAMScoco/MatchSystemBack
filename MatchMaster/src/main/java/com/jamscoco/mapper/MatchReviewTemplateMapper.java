package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchReviewTemplate;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 评审模板Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchReviewTemplateMapper extends BaseMapper<MatchReviewTemplate>
{
    /**
     * 查询评审模板列表
     *
     * @param matchReviewTemplate 评审模板
     * @return 评审模板集合
     */
    public List<MatchReviewTemplate> selectMatchReviewTemplateList(MatchReviewTemplate matchReviewTemplate);

}
