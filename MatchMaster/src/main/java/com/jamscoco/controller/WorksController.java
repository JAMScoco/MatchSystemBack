package com.jamscoco.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import com.jamscoco.domain.Match;
import com.jamscoco.dto.SortMoveDto;
import com.jamscoco.service.IMatchService;
import com.jamscoco.service.IWorksScoreService;
import com.jamscoco.vo.ScoreVo;
import com.jamscoco.vo.WorkInfo;
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
    private IWorksScoreService worksScoreService;

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
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return getDataTable(new ArrayList<>());
        }
        startPage();
        //若是院系管理员，设置筛选条件为本院系
        long roleType = getRoleType();
        if (roleType == 1L) {
            works.setDeptId(String.valueOf(getDeptId()));
        }
        works.setMatchId(currentMatch.getId());
        List<WorkInfo> list = worksService.selectWorksList(works);
        return getDataTable(list);
    }

    /**
     * 导出作品列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:export')")
    @Log(title = "作品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Works works) {
        Match currentMatch = matchService.getCurrentMatch();
        //若是院系管理员，设置筛选条件为本院系
        long roleType = getRoleType();
        if (roleType == 1L) {
            works.setDeptId(String.valueOf(getDeptId()));
            works.setMatchId(currentMatch.getId());
        }
        works.setMatchId(currentMatch.getId());
        List<WorkInfo> list = worksService.selectWorksListExport(works);
        ExcelUtil<WorkInfo> util = new ExcelUtil<WorkInfo>(WorkInfo.class);
        util.exportExcel(response, list, "作品数据");
    }

    /**
     * 获取作品详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('works:work:query,review:work:detail')")
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
        if (user.getTrueName() == null || user.getPhonenumber() == null || (!user.getLevel().equals("校外生") && user.getSno() == null)) {
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
            if (works.getState() == -1L){
//                worksService.removeWork(works.getId());
                return AjaxResult.success("您上次提交的作品审核未通过，请重新提交作品");
            }
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
        }else {
            if (works.getState() == -1L){
                worksService.removeWork(works.getId());
                return AjaxResult.success("您上次提交的作品审核未通过，请重新提交作品");
            }
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
     * 审核作品
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @Log(title = "作品", businessType = BusinessType.UPDATE)
    @PutMapping("/check")
    public AjaxResult check(@RequestBody Works works) {
        return toAjax(worksService.check(works));
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

    @PreAuthorize("@ss.hasPermi('works:work:remove')")
    @GetMapping("/waitReviewWorks")
    public AjaxResult waitReviewWorks() {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.success("当前没有正在进行中的赛事");
        }
        Long roleType = getRoleType();
        if (roleType == 1L){
            return AjaxResult.success(worksService.waitReviewWorksDepartment(getDeptId(), currentMatch.getId()));
        }else {
            return AjaxResult.success(worksService.waitReviewWorksSchool(currentMatch.getId()));
        }

    }

    /**
     * 作品评审结果管理列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @GetMapping("/result")
    public AjaxResult getReviewResult() {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.success("当前没有正在进行中的赛事");
        }

        Date now = new Date();
        Long roleType = getRoleType();
        if (roleType == 1L) {
            Map<String, Object> reviewDetails = worksScoreService.getReviewDetails(currentMatch.getId(), getRoleType(),getUsername());
            List<Map<String,Object>> target =(List<Map<String,Object>>)reviewDetails.get("target");
            for (Map<String, Object> map : target) {
                int len = map.keySet().size() - 1;
                for (int i = 0; i < len; i++) {
                    if(((ScoreVo)map.get("score"+i)).getScoreDetail()==null){
                        return AjaxResult.success("评审未结束，请评审专家评审完成后查看评审结果");
                    }
                }
            }
            List<WorkInfo> reviewResult = worksService.getReviewResult(currentMatch.getId(), roleType, getDeptId());
            Map<String, Object> result = new HashMap<>();
            result.put("result", reviewResult);
            result.put("hasRecommended", countHasRecommended(reviewResult, roleType));
            return AjaxResult.success("ok", result);
            //当前是否在院系评审结束后
            /*if (DateUtil.compare(now, currentMatch.getEndReviewTimeDepartment()) < 0) {
                return AjaxResult.success("院系评审未结束，请评审结束后查看评审结果");
            }*/

        } else {
            //当前是否在校级评审结束后
            if (DateUtil.compare(now, currentMatch.getEndReviewTimeSchool()) < 0) {
                return AjaxResult.success("校级评审未结束，请评审结束后查看评审结果");
            }
        }
        List<WorkInfo> reviewResult = worksService.getReviewResult(currentMatch.getId(), roleType, getDeptId());
        Map<String, Object> result = new HashMap<>();
        result.put("result", reviewResult);
        result.put("hasRecommended", countHasRecommended(reviewResult, roleType));
        return AjaxResult.success("ok", result);
    }

    private int countHasRecommended(List<WorkInfo> reviewResult, Long roleType) {
        int count = 0;
        for (WorkInfo workInfo : reviewResult) {
            if (workInfo.getState() == (3L - roleType)) {
                count++;
            }
        }
        return count;
    }

    @PreAuthorize("@ss.hasPermi('works:work:remove')")
    @PostMapping("/moveSort")
    public AjaxResult moveSort(@RequestBody SortMoveDto sortMoveDto) {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有正在进行中的赛事");
        }

        Date now = new Date();
        Long roleType = getRoleType();
        if (roleType == 1L) {
            if (DateUtil.compare(now, currentMatch.getStartReviewTimeSchool()) > 0) {
                return AjaxResult.error("校级评审已开始，无法修改排序");
            }
        } else {
            if (DateUtil.compare(now, currentMatch.getEndTime()) > 0) {
                return AjaxResult.error("本次比赛已结束，无法修改排序");
            }
        }
        return toAjax(worksService.move(sortMoveDto, roleType));
    }

    /**
     * 推荐作品
     */
    @PreAuthorize("@ss.hasAnyPermi('works:work:query,review:work:detail')")
    @PostMapping(value = "recommend/{id}")
    public AjaxResult recommend(@PathVariable("id") String id) {
        return AjaxResult.success(worksService.recommend(id,getRoleType()));
    }

    @PreAuthorize("@ss.hasRole('student')")
    @PostMapping(value = "change/{id}")
    public AjaxResult changeWorks(@PathVariable("id") String id) {
        WorkInfo work = worksService.getWorkInfoById(id);
        if(work.getState().equals(0L)){
            worksService.removeWork(id);
            return AjaxResult.success("ok");
        }else {
            return AjaxResult.success("院系管理员已审核通过，无法修改。如需修改，请联系院系管理员。");
        }
    }
}
