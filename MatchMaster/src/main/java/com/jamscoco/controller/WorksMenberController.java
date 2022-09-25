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
import com.jamscoco.domain.WorksMenber;
import com.jamscoco.service.IWorksMenberService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 参与成员Controller
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@RestController
@RequestMapping("/works/menber")
public class WorksMenberController extends BaseController
{
    @Autowired
    private IWorksMenberService worksMenberService;

    /**
     * 查询参与成员列表
     */
    @PreAuthorize("@ss.hasPermi('works:menber:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorksMenber worksMenber)
    {
        startPage();
        List<WorksMenber> list = worksMenberService.selectWorksMenberList(worksMenber);
        return getDataTable(list);
    }

    /**
     * 导出参与成员列表
     */
    @PreAuthorize("@ss.hasPermi('works:menber:export')")
    @Log(title = "参与成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorksMenber worksMenber)
    {
        List<WorksMenber> list = worksMenberService.selectWorksMenberList(worksMenber);
        ExcelUtil<WorksMenber> util = new ExcelUtil<WorksMenber>(WorksMenber.class);
        util.exportExcel(response, list, "参与成员数据");
    }

    /**
     * 获取参与成员详细信息
     */
    @PreAuthorize("@ss.hasPermi('works:menber:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(worksMenberService.getById(id));
    }

    /**
     * 新增参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:menber:add')")
    @Log(title = "参与成员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorksMenber worksMenber)
    {
        return toAjax(worksMenberService.save(worksMenber));
    }

    /**
     * 修改参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:menber:edit')")
    @Log(title = "参与成员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorksMenber worksMenber)
    {
        return toAjax(worksMenberService.updateById(worksMenber));
    }

    /**
     * 删除参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:menber:remove')")
    @Log(title = "参与成员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(worksMenberService.removeByIds(Arrays.asList(ids)));
    }
}
