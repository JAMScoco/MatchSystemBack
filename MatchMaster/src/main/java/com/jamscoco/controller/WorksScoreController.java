package com.jamscoco.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.jamscoco.domain.Match;
import com.jamscoco.dto.ScoreDto;
import com.jamscoco.dto.ScoreSubmitDto;
import com.jamscoco.service.IMatchReviewTemplateService;
import com.jamscoco.service.IMatchService;
import com.jamscoco.service.IWorksService;
import com.jamscoco.vo.ScoreVo;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class WorksScoreController extends BaseController {
    @Autowired
    private IWorksScoreService worksScoreService;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IMatchReviewTemplateService matchReviewTemplateService;


    /**
     * 查询评审分值列表
     */
    @PreAuthorize("@ss.hasPermi('work:score:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScoreDto scoreDto) {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return new TableDataInfo(null, 0);
        }
        startPage();
        scoreDto.setMatchId(currentMatch.getId());
        scoreDto.setUserId(String.valueOf(getUserId()));
        List<ScoreVo> list = worksScoreService.selectWorksScoreList(scoreDto);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('work:score:list')")
    @GetMapping("/indexInfo")
    public AjaxResult indexInfo() {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有正在进行的赛事");
        }
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setMatchId(currentMatch.getId());
        scoreDto.setUserId(String.valueOf(getUserId()));
        Map<String, Object> info = worksScoreService.getIndexInfo(scoreDto);
        info.put("endTimeSchool",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,currentMatch.getEndReviewTimeSchool()));
        info.put("endTimeDept",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD,currentMatch.getEndReviewTimeDepartment()));
        return AjaxResult.success(info);
    }

    /**
     * 查询评审分值列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @GetMapping("/details")
    public AjaxResult details() {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有正在进行的赛事");
        }
        Map<String, Object> reviewDetails = worksScoreService.getReviewDetails(currentMatch.getId(), getRoleType());
        return AjaxResult.success(reviewDetails);

    }

    /**
     * 导出评审分值列表
     */
    @PreAuthorize("@ss.hasPermi('work:score:export')")
    @Log(title = "评审分值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScoreDto scoreDto) {
        List<ScoreVo> list = worksScoreService.selectWorksScoreList(scoreDto);
        ExcelUtil<ScoreVo> util = new ExcelUtil<ScoreVo>(ScoreVo.class);
        util.exportExcel(response, list, "评审分值数据");
    }

    /**
     * 获取评审模板
     */
    @PreAuthorize("@ss.hasPermi('work:score:query')")
    @GetMapping(value = "getReviewTemplate/{id}")
    public AjaxResult getReviewTemplateInfo(@PathVariable("id") String id) {
        return AjaxResult.success(matchReviewTemplateService.getReviewTemplateByScoreId(id));
    }

    /**
     * 提交评审分值
     */
    @PreAuthorize("@ss.hasPermi('work:score:edit')")
    @Log(title = "评审分值", businessType = BusinessType.UPDATE)
    @PostMapping("submit")
    public AjaxResult edit(@RequestBody ScoreSubmitDto scoreSubmitDto) {
        return toAjax(worksScoreService.submitScore(scoreSubmitDto));
    }


    /**
     * 获取得分详细
     */
    @Anonymous
    @GetMapping(value = "getGoalDetail/{id}")
    public AjaxResult getGoalDetail(@PathVariable("id") String id) {
        return AjaxResult.success(worksScoreService.getGoalDetail(id, getRoleType()));
    }

}
