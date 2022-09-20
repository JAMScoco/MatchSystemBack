package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.News;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 动态管理Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@Mapper
public interface NewsMapper extends BaseMapper<News>
{
    /**
     * 查询动态管理列表
     *
     * @param news 动态管理
     * @return 动态管理集合
     */
    public List<News> selectNewsList(News news);

}
