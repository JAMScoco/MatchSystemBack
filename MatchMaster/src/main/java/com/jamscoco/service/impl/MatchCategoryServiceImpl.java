package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.MatchGroup;
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

    /**
     * 删除trackId为传进来的id的类别
     * @param trackId
     * @return
     */
    @Override
    public boolean deleteCategoryByTrackId(String trackId) {
        List<MatchCategory> categoryList = baseMapper.selectList(new QueryWrapper<MatchCategory>().eq("track_id", trackId));
        if(categoryList.size() == 0){
            return true;
        }else {
            List<String> arrayList = new ArrayList<>();
            for (MatchCategory matchCategory: categoryList) {
                arrayList.add(matchCategory.getId());
            }
            return baseMapper.deleteBatchIds(arrayList) > 0;
        }
    }
}
