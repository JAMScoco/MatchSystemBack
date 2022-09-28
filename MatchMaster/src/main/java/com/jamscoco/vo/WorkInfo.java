package com.jamscoco.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jamscoco.domain.WorksMember;
import com.jamscoco.domain.WorksTeacher;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorkInfo {
    /** 主键ID */
    private String id;

    /**  作品名称 */
    private String name;

    /**  负责人姓名 */
    private String trueName;

    /**  负责人学号 */
    private String sno;

    /**  负责人手机号 */
    private String phonenumber;

    /** 作品报告（文件链接 */
    private String report;

    /** 作品附件（文件链接） */
    private String attachment;

    /** 所属赛道ID */
    private String trackId;

    /** 所属赛道名字 */
    private String trackName;

    /** 所属组别ID */
    private String groupId;

    /** 所属组别名字 */
    private String groupName;

    /** 所属类别ID */
    private String categoryId;

    /** 所属类别名字 */
    private String categoryName;

    /** 院级评审平均分 */
    private Double departmentAverageScore;

    /** 校级评审平均分 */
    private Double schoolAverageScore;

    /** 作品状态（-1:报名国家平台截图审核失败；0：待审核报名国家平台截图；1：截图审核通过；2：院系已推荐；3：学校已推荐） */
    private Long state;

    /** 国家平台报名成功截图 */
    private String screenshot;

    /** 作品概述 */
    private String overview;

    /** 院系评审的名次 */
    private Long departmentSort;

    /** 校级评审的名次 */
    private Long schoolSort;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<WorksMember> memberList;

    private List<WorksTeacher> teacherList;
}
