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
import com.jamscoco.domain.MatchTrack;
import com.jamscoco.service.IMatchTrackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 赛事对应的赛道信息Controller
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/match/track")
public class MatchTrackController extends BaseController
{
    @Autowired
    private IMatchTrackService matchTrackService;

    /**
     * 查询赛事对应的赛道信息列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchTrack matchTrack)
    {
        startPage();
        List<MatchTrack> list = matchTrackService.selectMatchTrackList(matchTrack);
        return getDataTable(list);
    }

    /**
     * 获取赛事对应的赛道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(matchTrackService.getById(id));
    }

    /**
     * 新增赛事对应的赛道信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "赛事对应的赛道信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MatchTrack matchTrack)
    {
        return toAjax(matchTrackService.save(matchTrack));
    }

    /**
     * 修改赛事对应的赛道信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "赛事对应的赛道信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MatchTrack matchTrack)
    {
        return toAjax(matchTrackService.updateById(matchTrack));
    }

    /**
     * 删除赛事对应的赛道信息
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @Log(title = "赛事对应的赛道信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(matchTrackService.removeByIds(Arrays.asList(ids)));
    }
}
