package com.jamscoco.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jamscoco.domain.Match;
import com.jamscoco.service.IMatchService;
import com.ruoyi.common.core.domain.entity.SysRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jamscoco.domain.News;
import com.jamscoco.service.INewsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 动态管理Controller
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@RestController
@RequestMapping("/school/news")
public class NewsController extends BaseController
{
    @Autowired
    private INewsService newsService;

    @Autowired
    private IMatchService matchService;

    /**
     * 查询动态管理列表
     */
    @PreAuthorize("@ss.hasPermi('school:news:list')")
    @GetMapping("/list")
    public TableDataInfo list(News news)
    {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch){
            return getDataTable(new ArrayList<>());
        }
        startPage();
        long roleType = getRoleType();
        news.setType(roleType);
        if (roleType == 0L){
            news.setOwner(currentMatch.getId());
        }else {
            news.setOwner(String.valueOf(getLoginUser().getDeptId()));
        }
        List<News> list = newsService.selectNewsList(news);
        return getDataTable(list);
    }

    /**
     * 导出动态管理列表
     */
    @PreAuthorize("@ss.hasPermi('school:news:export')")
    @Log(title = "动态管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, News news)
    {

        List<News> list = newsService.selectNewsList(news);
        ExcelUtil<News> util = new ExcelUtil<News>(News.class);
        util.exportExcel(response, list, "动态管理数据");
    }

    /**
     * 获取动态管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('school:news:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(newsService.getById(id));
    }

    /**
     * 新增动态
     */
    @PreAuthorize("@ss.hasPermi('school:news:add')")
    @Log(title = "动态管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody News news)
    {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch){
            return AjaxResult.error("当前没有正在进行中的赛事，不能添加动态");
        }
        long roleType = getRoleType();
        news.setType(roleType);
        if (roleType == 0L){
            news.setOwner(currentMatch.getId());
        }else {
            news.setOwner(String.valueOf(getLoginUser().getDeptId()));
        }
        return toAjax(newsService.save(news));
    }

    /**
     * 修改动态管理
     */
    @PreAuthorize("@ss.hasPermi('school:news:edit')")
    @Log(title = "动态管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody News news)
    {
        return toAjax(newsService.updateById(news));
    }

    /**
     * 删除动态管理
     */
    @PreAuthorize("@ss.hasPermi('school:news:remove')")
    @Log(title = "动态管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(newsService.removeByIds(Arrays.asList(ids)));
    }
}
