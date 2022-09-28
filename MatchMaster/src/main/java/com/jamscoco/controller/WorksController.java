package com.jamscoco.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import com.jamscoco.domain.Match;
import com.jamscoco.service.IMatchService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
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
public class WorksController extends BaseController {
    @Autowired
    private IWorksService worksService;

    @Autowired
    private IMatchService matchService;

    /**
     * 查询作品列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(Works works) {
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
    public void export(HttpServletResponse response, Works works) {
        List<Works> list = worksService.selectWorksList(works);
        ExcelUtil<Works> util = new ExcelUtil<Works>(Works.class);
        util.exportExcel(response, list, "作品数据");
    }

    /**
     * 获取作品详细信息
     */
    @PreAuthorize("@ss.hasPermi('works:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(worksService.getWorkInfoById(id));
    }

    /**
     * 新增作品
     */
    @PreAuthorize("@ss.hasRole('student')")
    @Log(title = "作品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Works works) {
        //1.学生是否完善个人信息
        SysUser user = getLoginUser().getUser();
        if (user.getTrueName() == null || user.getSno() == null || user.getPhonenumber() == null) {
            return AjaxResult.error("个人信息不完善，请先完善个人信息");
        }
        //2.当前是否有进行中的赛事
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有正在进行中的赛事，不能提交作品");
        }
        //3. 当前是否在报名时间内
        Date now = new Date();
        if (DateUtil.compare(now, currentMatch.getStartSubmitTime()) < 0) {
            return AjaxResult.success("未到报名时间，不能提交作品");
        }
        if (DateUtil.compare(now, currentMatch.getEndSubmitTime()) > 0) {
            return AjaxResult.success("报名时间已截止，不能提交作品");
        }
        //4.该学生是否在本次赛事中提交过作品
        Works query = worksService.currentMatchWork(getUserId(), currentMatch.getId());
        if (query != null) {
            return AjaxResult.success("您在本次大赛中已经提交过作品，请直接查看");
        }
        works.setMatchId(currentMatch.getId());
        works.setUserId(String.valueOf(getUserId()));
        String msg = worksService.addWorks(works);
        if (!Constants.SUCCESS.equals(msg)) {
            return AjaxResult.error(msg);
        } else {
            return AjaxResult.success();
        }
    }

    @PreAuthorize("@ss.hasRole('student')")
    @GetMapping("validCommit")
    public AjaxResult validCommit() {
        //1.学生是否完善个人信息
        SysUser user = getLoginUser().getUser();
        if (user.getTrueName() == null || user.getSno() == null || user.getPhonenumber() == null) {
            return AjaxResult.success("个人信息不完善，请先完善个人信息");
        }
        //2.当前是否有进行中的赛事
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.success("当前没有正在进行中的赛事，不能提交作品");
        }
        //3. 当前是否在报名时间内
        Date now = new Date();
        if (DateUtil.compare(now, currentMatch.getStartSubmitTime()) < 0) {
            return AjaxResult.success("未到报名时间，不能提交作品");
        }
        if (DateUtil.compare(now, currentMatch.getEndSubmitTime()) > 0) {
            return AjaxResult.success("报名时间已截止，不能提交作品");
        }
        //4.该学生是否在本次赛事中提交过作品
        Works works = worksService.currentMatchWork(getUserId(), currentMatch.getId());
        if (works != null) {
            return AjaxResult.success("您在本次大赛中已经提交过作品，请直接查看");
        }
        return AjaxResult.success("valid");
    }

    @PreAuthorize("@ss.hasRole('student')")
    @GetMapping("currentWorkInfo")
    public AjaxResult currentWorkInfo() {
        //1.当前是否有进行中的赛事
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.success("当前没有正在进行中的赛事");
        }
        //2.该学生是否在本次赛事中提交过作品
        Works works = worksService.currentMatchWork(getUserId(), currentMatch.getId());
        if (works == null) {
            return AjaxResult.success("您在本次大赛中还未提交过作品，请先提交作品");
        }
        return AjaxResult.success("ok", worksService.getWorkInfoById(works.getId()));
    }


    /**
     * 修改作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @Log(title = "作品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Works works) {
        return toAjax(worksService.updateById(works));
    }

    /**
     * 删除作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:remove')")
    @Log(title = "作品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(worksService.removeByIds(Arrays.asList(ids)));
    }
}
