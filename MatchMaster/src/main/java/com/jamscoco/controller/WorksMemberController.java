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
import com.jamscoco.domain.WorksMember;
import com.jamscoco.service.IWorksMemberService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 参与成员Controller
 *
 * @author jamscoco
 * @date 2022-09-26
 */
@RestController
@RequestMapping("/works/member")
public class WorksMemberController extends BaseController
{
    @Autowired
    private IWorksMemberService worksMemberService;

    /**
     * 查询参与成员列表
     */
    @PreAuthorize("@ss.hasPermi('works:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorksMember worksMember)
    {
        startPage();
        List<WorksMember> list = worksMemberService.selectWorksMemberList(worksMember);
        return getDataTable(list);
    }

    /**
     * 导出参与成员列表
     */
    @PreAuthorize("@ss.hasPermi('works:member:export')")
    @Log(title = "参与成员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorksMember worksMember)
    {
        List<WorksMember> list = worksMemberService.selectWorksMemberList(worksMember);
        ExcelUtil<WorksMember> util = new ExcelUtil<WorksMember>(WorksMember.class);
        util.exportExcel(response, list, "参与成员数据");
    }

    /**
     * 获取参与成员详细信息
     */
    @PreAuthorize("@ss.hasPermi('works:member:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(worksMemberService.getById(id));
    }

    /**
     * 新增参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:member:add')")
    @Log(title = "参与成员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorksMember worksMember)
    {
        return toAjax(worksMemberService.save(worksMember));
    }

    /**
     * 修改参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:member:edit')")
    @Log(title = "参与成员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorksMember worksMember)
    {
        return toAjax(worksMemberService.updateById(worksMember));
    }

    /**
     * 删除参与成员
     */
    @PreAuthorize("@ss.hasPermi('works:member:remove')")
    @Log(title = "参与成员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(worksMemberService.removeByIds(Arrays.asList(ids)));
    }
}
