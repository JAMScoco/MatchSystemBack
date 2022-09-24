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
import com.jamscoco.domain.MatchGroup;
import com.jamscoco.service.IMatchGroupService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 组别Controller
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/match/group")
public class MatchGroupController extends BaseController
{
    @Autowired
    private IMatchGroupService matchGroupService;

    /**
     * 查询组别列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchGroup matchGroup)
    {
        startPage();
        List<MatchGroup> list = matchGroupService.selectMatchGroupList(matchGroup);
        return getDataTable(list);
    }

    /**
     * 获取组别详细信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(matchGroupService.getById(id));
    }

    /**
     * 新增组别
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "组别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MatchGroup matchGroup)
    {
        return toAjax(matchGroupService.save(matchGroup));
    }

    /**
     * 修改组别
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "组别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MatchGroup matchGroup)
    {
        return toAjax(matchGroupService.updateById(matchGroup));
    }

    /**
     * 删除组别
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "组别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        System.out.println(99999);
        return toAjax(matchGroupService.removeByIds(Arrays.asList(ids)));
    }
}
