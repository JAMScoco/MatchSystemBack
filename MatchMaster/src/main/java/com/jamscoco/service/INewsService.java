package com.jamscoco.service;

import java.util.List;
import java.util.Map;

import com.jamscoco.domain.News;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 动态管理Service接口
 *
 * @author jamscoco
 * @date 2022-09-20
 */
public interface INewsService extends IService<News>
{
    /**
     * 查询动态管理列表
     *
     * @param news 动态管理
     * @return 动态管理集合
     */
    public List<News> selectNewsList(News news);

    Map<String, Object> getAllNews();
}
