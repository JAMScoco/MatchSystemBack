package com.ruoyi.web.controller.reviewer;


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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
}
