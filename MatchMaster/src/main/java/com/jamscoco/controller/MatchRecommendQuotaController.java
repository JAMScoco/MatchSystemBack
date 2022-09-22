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
import com.jamscoco.domain.MatchRecommendQuota;
import com.jamscoco.service.IMatchRecommendQuotaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 推荐的作品名额Controller
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/match/quota")
public class MatchRecommendQuotaController extends BaseController
{
    @Autowired
    private IMatchRecommendQuotaService matchRecommendQuotaService;

    /**
     * 查询推荐的作品名额列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchRecommendQuota matchRecommendQuota)
    {
        startPage();
        List<MatchRecommendQuota> list = matchRecommendQuotaService.selectMatchRecommendQuotaList(matchRecommendQuota);
        return getDataTable(list);
    }

    /**
     * 获取推荐的作品名额详细信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(matchRecommendQuotaService.getById(id));
    }

    /**
     * 新增推荐的作品名额
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "推荐的作品名额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MatchRecommendQuota matchRecommendQuota)
    {
        return toAjax(matchRecommendQuotaService.save(matchRecommendQuota));
    }

    /**
     * 修改推荐的作品名额
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "推荐的作品名额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MatchRecommendQuota matchRecommendQuota)
    {
        return toAjax(matchRecommendQuotaService.updateById(matchRecommendQuota));
    }

    /**
     * 删除推荐的作品名额
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "推荐的作品名额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(matchRecommendQuotaService.removeByIds(Arrays.asList(ids)));
    }
}
