package com.ruoyi.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

import java.util.Map;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class SysRegisterService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword();

        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (StringUtils.isEmpty(registerBody.getSno()) && !registerBody.getLevel().equals("校外生")) {
            msg = registerBody.getLevel() + "学号不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkSnoUnique(registerBody.getSno()))) {
            msg = "该学号'" + registerBody.getSno() + "'账号已存在";
        } else if (!userService.checkSnoDept(registerBody.getSno(), registerBody.getDeptId(), registerBody.getLevel())) {
            msg = "学籍信息不正确，请检查学号院校以及身份";
        } else {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(registerBody.getPassword()));

            sysUser.setDeptId(registerBody.getDeptId());
            sysUser.setLevel(registerBody.getLevel());
            sysUser.setSno(registerBody.getSno());

            if ("本科生在读".equals(registerBody.getLevel())) {
                Map<String, String> info = userService.queryUndergraduateInfo(registerBody.getSno());
                sysUser.setMajor(info.get("major"));
                sysUser.setDomain(info.get("clazz"));
                sysUser.setTrueName(info.get("name"));
                sysUser.setSex("男".equals(info.get("sex")) ? "0" : "1");
            }
            if ("硕士研究生在读".equals(registerBody.getLevel()) || "博士研究生在读".equals(registerBody.getLevel())) {
                Map<String, String> info = userService.queryGraduateInfo(registerBody.getSno());
                sysUser.setMajor(info.get("major"));
                sysUser.setTrueName(info.get("name"));
                sysUser.setSex("男".equals(info.get("sex")) ? "0" : "1");
            }

            boolean regFlag = userService.registerStudent(sysUser);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
                        MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
