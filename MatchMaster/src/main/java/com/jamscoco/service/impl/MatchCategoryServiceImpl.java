package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchCategoryMapper;
import com.jamscoco.domain.MatchCategory;
import com.jamscoco.service.IMatchCategoryService;

/**
 * 类别Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchCategoryServiceImpl extends ServiceImpl<MatchCategoryMapper,MatchCategory> implements IMatchCategoryService
{

    /**
     * 查询类别列表
     *
     * @param matchCategory 类别
     * @return 类别
     */
    @Override
    public List<MatchCategory> selectMatchCategoryList(MatchCategory matchCategory)
    {
        return baseMapper.selectMatchCategoryList(matchCategory);
    }
}
