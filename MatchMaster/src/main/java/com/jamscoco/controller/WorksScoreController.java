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
import com.jamscoco.domain.WorksScore;
import com.jamscoco.service.IWorksScoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评审分值Controller
 *
 * @author jamscoco
 * @date 2023-02-17
 */
@RestController
@RequestMapping("/work/score")
public class WorksScoreController extends BaseController
{
    @Autowired
    private IWorksScoreService worksScoreService;

    /**
     * 查询评审分值列表
     */
    @PreAuthorize("@ss.hasPermi('work:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorksScore worksScore)
    {
        startPage();
        List<WorksScore> list = worksScoreService.selectWorksScoreList(worksScore);
        return getDataTable(list);
    }

    /**
     * 导出评审分值列表
     */
    @PreAuthorize("@ss.hasPermi('work:score:export')")
    @Log(title = "评审分值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorksScore worksScore)
    {
        List<WorksScore> list = worksScoreService.selectWorksScoreList(worksScore);
        ExcelUtil<WorksScore> util = new ExcelUtil<WorksScore>(WorksScore.class);
        util.exportExcel(response, list, "评审分值数据");
    }

    /**
     * 获取评审分值详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:score:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(worksScoreService.getById(id));
    }

    /**
     * 新增评审分值
     */
    @PreAuthorize("@ss.hasPermi('work:score:add')")
    @Log(title = "评审分值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorksScore worksScore)
    {
        return toAjax(worksScoreService.save(worksScore));
    }

    /**
     * 修改评审分值
     */
    @PreAuthorize("@ss.hasPermi('work:score:edit')")
    @Log(title = "评审分值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorksScore worksScore)
    {
        return toAjax(worksScoreService.updateById(worksScore));
    }

    /**
     * 删除评审分值
     */
    @PreAuthorize("@ss.hasPermi('work:score:remove')")
    @Log(title = "评审分值", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(worksScoreService.removeByIds(Arrays.asList(ids)));
    }
}
