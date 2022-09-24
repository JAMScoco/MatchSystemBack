package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 类别Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchCategoryService extends IService<MatchCategory>
{
    /**
     * 查询类别列表
     *
     * @param matchCategory 类别
     * @return 类别集合
     */
    public List<MatchCategory> selectMatchCategoryList(MatchCategory matchCategory);

    /**
     * 删除trackId为传进来的id的类别
     * @param trackId
     * @return
     */
    boolean deleteCategoryByTrackId(String trackId);
}
