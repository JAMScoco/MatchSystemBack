package com.jamscoco.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.NewsMapper;
import com.jamscoco.domain.News;
import com.jamscoco.service.INewsService;

/**
 * 动态管理Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper,News> implements INewsService
{

    /**
     * 查询动态管理列表
     *
     * @param news 动态管理
     * @return 动态管理
     */
    @Override
    public List<News> selectNewsList(News news)
    {
        return baseMapper.selectNewsList(news);
    }

    @Override
    public Map<String, Object> getAllNews() {
        long type = 0;
        List<News> schoolNews = baseMapper.getAllNews(type);
        type = 1;
        List<News> deptNews = baseMapper.getAllNews(type);
        Map<String,Object> result = new HashMap<>();
        result.put("schoolNews",schoolNews);
        result.put("deptNews",deptNews);
        return result;
    }
}
