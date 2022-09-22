package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchCategory;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 类别Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchCategoryMapper extends BaseMapper<MatchCategory>
{
    /**
     * 查询类别列表
     *
     * @param matchCategory 类别
     * @return 类别集合
     */
    public List<MatchCategory> selectMatchCategoryList(MatchCategory matchCategory);

}
