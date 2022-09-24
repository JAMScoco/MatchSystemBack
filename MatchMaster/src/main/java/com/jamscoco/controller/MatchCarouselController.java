package com.jamscoco.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jamscoco.domain.Match;
import com.jamscoco.service.IMatchService;
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
import com.jamscoco.domain.MatchCarousel;
import com.jamscoco.service.IMatchCarouselService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发布赛事的轮播图Controller
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/match/carousel")
public class MatchCarouselController extends BaseController
{
    @Autowired
    private IMatchCarouselService matchCarouselService;
    @Autowired
    private IMatchService matchService;

    /**
     * 查询发布赛事的轮播图列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchCarousel matchCarousel)
    {
        startPage();
        matchCarousel.setMatchId(matchService.getCurrentMatch().getId());
        List<MatchCarousel> list = matchCarouselService.selectMatchCarouselList(matchCarousel);
        return getDataTable(list);
    }

    /**
     * 获取发布赛事的轮播图详细信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(matchCarouselService.getById(id));
    }

    /**
     * 新增发布赛事的轮播图
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "发布赛事的轮播图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MatchCarousel matchCarousel)
    {
        Match currentMatch = matchService.getCurrentMatch();
        if(currentMatch == null){
            return AjaxResult.error("当前没有正在进行的赛事");
        }else {
            matchCarousel.setMatchId(currentMatch.getId());
            return toAjax(matchCarouselService.save(matchCarousel));
        }
    }

    /**
     * 修改发布赛事的轮播图
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "发布赛事的轮播图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MatchCarousel matchCarousel)
    {
        return toAjax(matchCarouselService.updateById(matchCarousel));
    }

    /**
     * 删除发布赛事的轮播图
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "发布赛事的轮播图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(matchCarouselService.removeByIds(Arrays.asList(ids)));
    }
}
