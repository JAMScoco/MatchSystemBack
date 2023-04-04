package com.jamscoco.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.jamscoco.service.ISchoolInfoGarduateStudentService;
import com.jamscoco.service.ISchoolInfoUndergraduateStudentService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jamscoco.domain.SchoolInfo;
import com.jamscoco.service.ISchoolInfoService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.context.request.async.WebAsyncManager;

/**
 * 学院信息Controller
 *
 * @author jamscoco
 * @date 2023-04-01
 */
@RestController
@RequestMapping("/school/info")
public class SchoolInfoController extends BaseController {
    @Autowired
    private ISchoolInfoService schoolInfoService;

    @Autowired
    private ISchoolInfoUndergraduateStudentService undergraduateStudentService;

    @Autowired
    private ISchoolInfoGarduateStudentService garduateStudentService;

    /**
     * 查询学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('school:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolInfo schoolInfo) {
        startPage();
        List<SchoolInfo> list = schoolInfoService.selectSchoolInfoList(schoolInfo);
        return getDataTable(list);
    }


    /**
     * 获取学院信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('school:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(schoolInfoService.getById(id));
    }

    /**
     * 新增学院信息
     */
    @PreAuthorize("@ss.hasPermi('school:info:add')")
    @Log(title = "学院信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SchoolInfo schoolInfo) {
        return toAjax(schoolInfoService.save(schoolInfo));
    }

    /**
     * 修改学院信息
     */
    @PreAuthorize("@ss.hasPermi('school:info:edit')")
    @Log(title = "学院信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SchoolInfo schoolInfo) {
        return toAjax(schoolInfoService.updateById(schoolInfo));
    }

    /**
     * 删除学院信息
     */
    @PreAuthorize("@ss.hasPermi('school:info:remove')")
    @Log(title = "学院信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(schoolInfoService.removeByIds(Arrays.asList(ids)));
    }

    @PostMapping("/importInfo")
    public AjaxResult importInfo(@RequestParam String info) {
        String localPath = RuoYiConfig.getProfile();
        String filePath = localPath + StringUtils.substringAfter(info, Constants.RESOURCE_PREFIX);
        try {
            ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath));
            List<Map<String, Object>> allInfo = reader.readAll();
            schoolInfoService.saveInfo(allInfo);
            schoolInfoService.saveFileRecord("学院专业班级信息",info);
            reader.close();
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @PostMapping("/importUndergraduate")
    public AjaxResult importUndergraduate(@RequestParam String info) {
        String localPath = RuoYiConfig.getProfile();
        String filePath = localPath + StringUtils.substringAfter(info, Constants.RESOURCE_PREFIX);
        try {
            ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath));
            List<Map<String, Object>> allInfo = reader.readAll();
            undergraduateStudentService.saveUndergraduateStudent(allInfo);
            schoolInfoService.saveFileRecord("本科生学籍信息",info);
            reader.close();
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @PostMapping("/importGraduate")
    public AjaxResult importGraduate(@RequestParam String info) {
        String localPath = RuoYiConfig.getProfile();
        String filePath = localPath + StringUtils.substringAfter(info, Constants.RESOURCE_PREFIX);
        try {
            ExcelReader reader = ExcelUtil.getReader(FileUtil.file(filePath));
            List<Map<String, Object>> allInfo = reader.readAll();
            garduateStudentService.saveGraduateStudent(allInfo);
            schoolInfoService.saveFileRecord("研究生学籍信息",info);
            reader.close();
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    @GetMapping("/queryFilePath")
    public AjaxResult queryFilePath(@RequestParam String name) {
        return AjaxResult.success(schoolInfoService.getFilePathByName(name));
    }



}
