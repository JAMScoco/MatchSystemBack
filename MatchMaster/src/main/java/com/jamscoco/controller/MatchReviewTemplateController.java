package com.jamscoco.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.jamscoco.domain.MatchReviewTemplate;
import com.jamscoco.service.IMatchReviewTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评审模板Controller
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/match/template")
public class MatchReviewTemplateController extends BaseController
{
    @Autowired
    private IMatchReviewTemplateService matchReviewTemplateService;

    /**
     * 查询评审模板列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchReviewTemplate matchReviewTemplate)
    {
        List<MatchReviewTemplate> list = matchReviewTemplateService.selectMatchReviewTemplateList(matchReviewTemplate);
        return getDataTable(list);
    }

    /**
     * 获取评审模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(matchReviewTemplateService.getById(id));
    }

    /**
     * 新增评审模板
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "评审模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MatchReviewTemplate matchReviewTemplate)
    {
        return toAjax(matchReviewTemplateService.save(matchReviewTemplate));
    }

    /**
     * 修改评审模板
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "评审模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MatchReviewTemplate matchReviewTemplate)
    {
        return toAjax(matchReviewTemplateService.updateById(matchReviewTemplate));
    }

    /**
     * 删除评审模板
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "评审模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(matchReviewTemplateService.removeByIds(Arrays.asList(ids)));
    }
}
