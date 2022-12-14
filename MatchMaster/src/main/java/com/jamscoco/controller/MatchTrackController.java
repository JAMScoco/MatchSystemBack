package com.jamscoco.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jamscoco.domain.Match;
import com.jamscoco.service.IMatchCategoryService;
import com.jamscoco.service.IMatchGroupService;
import com.jamscoco.service.IMatchService;
import com.jamscoco.vo.TrackInfoVo;
import com.ruoyi.common.annotation.Anonymous;
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
    @Autowired
    private IMatchService matchService;
    @Autowired
    private IMatchGroupService matchGroupService;
    @Autowired
    private IMatchCategoryService matchCategoryService;

    /**
     * 查询赛道组别类别信息
     */
    @Anonymous
    @GetMapping("/getTrackInfo")
    public AjaxResult getTrackInfo(){
        Match currentMatch = matchService.getCurrentMatch();
        if(null == currentMatch){
            return AjaxResult.error("没有正在进行的赛事");
        }else {
            List<MatchTrack> trackList = matchTrackService.getTrackInfo(currentMatch.getId());
            return AjaxResult.success(trackList);
        }
    }

    /**
     * 查询赛道组别信息
     * @return
     */
    @Anonymous
    @GetMapping("/getTrackInfoWithoutCategory")
    public AjaxResult getTrackInfoWithoutCategory(){
        Match currentMatch = matchService.getCurrentMatch();
        if(null == currentMatch){
            return AjaxResult.error("没有正在进行的赛事");
        }else {
            List<MatchTrack> trackList = matchTrackService.getTrackInfoWithoutCategory(currentMatch.getId());
            return AjaxResult.success(trackList);
        }
    }

    /**
     * 查询赛事对应的赛道信息列表
     */
    @PreAuthorize("@ss.hasPermi('match:history:edit')")
    @GetMapping("/list")
    public TableDataInfo list(MatchTrack matchTrack)
    {
        startPage();
        Match currentMatch = matchService.getCurrentMatch();
        if(null == currentMatch){
            return new TableDataInfo(null,0);
        }else {
            matchTrack.setMatchId(currentMatch.getId());
            List<MatchTrack> list = matchTrackService.selectMatchTrackList(matchTrack);
            return getDataTable(list);
        }
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
        Match currentMatch = matchService.getCurrentMatch();
        if(null == currentMatch){
            return AjaxResult.error("当前没有正在进行的赛事");
        }else {
            matchTrack.setMatchId(currentMatch.getId());
            return toAjax(matchTrackService.save(matchTrack));
        }
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
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
    {
        if(matchGroupService.deleteGroupByTrackId(id) && matchCategoryService.deleteCategoryByTrackId(id)){
            return toAjax(matchTrackService.removeById(id));
        }else {
            return AjaxResult.error("删除失败！");
        }

    }
}
