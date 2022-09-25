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
import com.jamscoco.domain.Works;
import com.jamscoco.service.IWorksService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作品Controller
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@RestController
@RequestMapping("/works/work")
public class WorksController extends BaseController
{
    @Autowired
    private IWorksService worksService;

    /**
     * 查询作品列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(Works works)
    {
        startPage();
        List<Works> list = worksService.selectWorksList(works);
        return getDataTable(list);
    }

    /**
     * 导出作品列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:export')")
    @Log(title = "作品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Works works)
    {
        List<Works> list = worksService.selectWorksList(works);
        ExcelUtil<Works> util = new ExcelUtil<Works>(Works.class);
        util.exportExcel(response, list, "作品数据");
    }

    /**
     * 获取作品详细信息
     */
    @PreAuthorize("@ss.hasPermi('works:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(worksService.getById(id));
    }

    /**
     * 新增作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:add')")
    @Log(title = "作品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Works works)
    {
        return toAjax(worksService.save(works));
    }

    /**
     * 修改作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @Log(title = "作品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Works works)
    {
        return toAjax(worksService.updateById(works));
    }

    /**
     * 删除作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:remove')")
    @Log(title = "作品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(worksService.removeByIds(Arrays.asList(ids)));
    }
}
