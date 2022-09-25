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
import com.jamscoco.domain.WorksTeacher;
import com.jamscoco.service.IWorksTeacherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 指导老师Controller
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@RestController
@RequestMapping("/works/teacher")
public class WorksTeacherController extends BaseController
{
    @Autowired
    private IWorksTeacherService worksTeacherService;

    /**
     * 查询指导老师列表
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorksTeacher worksTeacher)
    {
        startPage();
        List<WorksTeacher> list = worksTeacherService.selectWorksTeacherList(worksTeacher);
        return getDataTable(list);
    }

    /**
     * 导出指导老师列表
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:export')")
    @Log(title = "指导老师", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorksTeacher worksTeacher)
    {
        List<WorksTeacher> list = worksTeacherService.selectWorksTeacherList(worksTeacher);
        ExcelUtil<WorksTeacher> util = new ExcelUtil<WorksTeacher>(WorksTeacher.class);
        util.exportExcel(response, list, "指导老师数据");
    }

    /**
     * 获取指导老师详细信息
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(worksTeacherService.getById(id));
    }

    /**
     * 新增指导老师
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:add')")
    @Log(title = "指导老师", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorksTeacher worksTeacher)
    {
        return toAjax(worksTeacherService.save(worksTeacher));
    }

    /**
     * 修改指导老师
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:edit')")
    @Log(title = "指导老师", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorksTeacher worksTeacher)
    {
        return toAjax(worksTeacherService.updateById(worksTeacher));
    }

    /**
     * 删除指导老师
     */
    @PreAuthorize("@ss.hasPermi('works:teacher:remove')")
    @Log(title = "指导老师", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(worksTeacherService.removeByIds(Arrays.asList(ids)));
    }
}
