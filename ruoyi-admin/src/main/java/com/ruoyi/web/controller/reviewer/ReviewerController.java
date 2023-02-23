package com.ruoyi.web.controller.reviewer;


import cn.hutool.core.date.DateUtil;
import com.jamscoco.domain.Match;
import com.jamscoco.domain.Works;
import com.jamscoco.service.IMatchService;
import com.jamscoco.service.IWorksScoreService;
import com.jamscoco.service.IWorksService;
import com.jamscoco.vo.WorkInfo;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 评审专家
 */
@RestController
@RequestMapping("/system/reviewer")
public class ReviewerController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IWorksService worksService;

    @Autowired
    private IWorksScoreService worksScoreService;

    /**
     * 获取评审专家列表
     */
    @PreAuthorize("@ss.hasPermi('works:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        //若是院系管理员，设置筛选条件为本院系
        long roleType = getRoleType();
        if (roleType == 1L) {
            user.setDeptId(getLoginUser().getDeptId());
        }
        List<SysUser> list = userService.selectReviewerList(user);
        return getDataTable(list);
    }


    @Log(title = "评审专家", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('works:work:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        //若是院系管理员，设置筛选条件为本院系
        long roleType = getRoleType();
        if (roleType == 1L) {
            user.setDeptId(getLoginUser().getDeptId());
        }
        List<SysUser> list = userService.selectReviewerList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    /**
     * 新增评审专家
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @Log(title = "评审专家管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {
        user.setUserName(user.getPhonenumber());
        //设置初始密码
        user.setPassword(configService.selectConfigByKey("sys.user.initPassword"));
        user.setNickName(user.getTrueName());
        user.setDeptId(getDeptId());
        //设置角色为评审专家
        user.setRoleIds(new Long[]{101L});

        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getTrueName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getTrueName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getTrueName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertReviewer(user));
    }

    /**
     * 修改评审专家
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @Log(title = "评审专家管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUserName(user.getPhonenumber());
        user.setNickName(user.getTrueName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getTrueName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getTrueName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateReviewer(user));
    }

    /**
     * 生成预览分配评审任务数据
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @PostMapping("/genAssignData")
    public AjaxResult getAssignData(@RequestBody List<SysUser> reviewers) {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有进行中的赛事");
        }
        List<String> waitReviewWorkIds = null;
        long roleType = getRoleType();
        if(roleType == 1L){
            //获取当前赛事本院系待评审作品id
            waitReviewWorkIds = worksService.waitReviewWorksDepartment(getDeptId(), currentMatch.getId());
        }
        if(roleType == 0L){
            waitReviewWorkIds = worksService.waitReviewWorksSchool(currentMatch.getId());
        }
        return AjaxResult.success(userService.genAssignData(reviewers, currentMatch.getReviewNumber(), waitReviewWorkIds));
    }

    /**
     * 删除预览分配评审任务数据
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @PostMapping("/delPreAssign/{key}")
    public AjaxResult delPreAssign(@PathVariable String key) {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有进行中的赛事");
        }
        return AjaxResult.success(worksService.delPreAssign(key));
    }

    /**
     * 确认生成分配评审任务数据
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @PostMapping("/ensurePreAssign/{key}")
    public AjaxResult ensurePreAssign(@PathVariable String key) {
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.error("当前没有进行中的赛事");
        }
        return AjaxResult.success(worksService.ensurePreAssign(key));
    }

    /**
     * 检查当前是否可以分配评审任务
     */
    @PreAuthorize("@ss.hasPermi('works:work:edit')")
    @GetMapping("/checkCanAssign")
    public AjaxResult checkCanAssign() {
        //1.当前是否有进行中的赛事
        Match currentMatch = matchService.getCurrentMatch();
        if (null == currentMatch) {
            return AjaxResult.success("当前没有进行中的赛事");
        }
        //2.报名时间是否结束
        Date now = new Date();
        if (DateUtil.compare(now, currentMatch.getEndSubmitTime()) < 0) {
            return AjaxResult.success("报名时间号未结束");
        }
        //3. 是否还有作品国家报名截图未审核
        Works works = new Works();
        works.setState(0L);
        List<WorkInfo> list = worksService.selectWorksList(works);
        if (list.size() > 0) {
            return AjaxResult.success("还有作品国家报名截图未审核");
        }
        //4. 是否已经生成评审任务
        List<String> waitReviewWorkIds = null;
        boolean hasGenAssign = false;
        long roleType = getRoleType();
        if(roleType == 1L){
            //获取当前赛事本院系待评审作品id
            waitReviewWorkIds = worksService.waitReviewWorksDepartment(getDeptId(), currentMatch.getId());
        }
        if(roleType == 0L){
            waitReviewWorkIds = worksService.waitReviewWorksSchool(currentMatch.getId());
        }
        hasGenAssign = worksScoreService.checkGenAssign(waitReviewWorkIds);
        if (hasGenAssign){
            return AjaxResult.success("评审任务已经生成，请查看");
        }
        return AjaxResult.success("ok");
    }

}
