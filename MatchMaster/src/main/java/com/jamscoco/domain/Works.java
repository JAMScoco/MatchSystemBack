package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 作品对象 works
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@TableName("works")
public class Works extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**  作品名称 */
    @Excel(name = " 作品名称")
    private String name;

    /** 负责人ID */
    private String userId;

    /** 作品报告（文件链接 */
    private String report;

    /** 作品附件（文件链接） */
    private String attachment;

    /** 所属赛事ID */
    private String matchId;

    /** 所属赛道ID */
    @Excel(name = "所属赛道ID")
    private String trackId;

    /** 所属组别ID */
    @Excel(name = "所属组别ID")
    private String groupId;

    /** 所属类别ID */
    @Excel(name = "所属类别ID")
    private String categoryId;

    /** 院级评审平均分 */
    private Double departmentAverageScore;

    /** 校级评审平均分 */
    private Double schoolAverageScore;

    /** 作品状态（-1:报名国家平台截图审核失败；0：待审核截图；1：截图审核通过；2：院系已推荐；3：学校已推荐） */
    @Excel(name = "作品状态", readConverterExp = "1:报名国家平台截图审核失败；0：待审核截图；1：截图审核通过；2：院系已推荐；3：学校已推荐")
    private Long state;

    /** 国家平台报名成功截图 */
    private String screenshot;

    /** 作品概述 */
    private String overview;

    /** 院系评审的名次 */
    private Long departmentSort;

    /** 校级评审的名次 */
    private Long schoolSort;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;


    @TableField(exist = false)
    private List<WorksMember> memberList;

    @TableField(exist = false)
    private List<WorksTeacher> teacherList;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setReport(String report)
    {
        this.report = report;
    }

    public String getReport()
    {
        return report;
    }
    public void setAttachment(String attachment)
    {
        this.attachment = attachment;
    }

    public String getAttachment()
    {
        return attachment;
    }
    public void setMatchId(String matchId)
    {
        this.matchId = matchId;
    }

    public String getMatchId()
    {
        return matchId;
    }
    public void setTrackId(String trackId)
    {
        this.trackId = trackId;
    }

    public String getTrackId()
    {
        return trackId;
    }
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupId()
    {
        return groupId;
    }
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryId()
    {
        return categoryId;
    }
    public void setDepartmentAverageScore(Double departmentAverageScore)
    {
        this.departmentAverageScore = departmentAverageScore;
    }

    public Double getDepartmentAverageScore()
    {
        return departmentAverageScore;
    }
    public void setSchoolAverageScore(Double schoolAverageScore)
    {
        this.schoolAverageScore = schoolAverageScore;
    }

    public Double getSchoolAverageScore()
    {
        return schoolAverageScore;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }
    public void setScreenshot(String screenshot)
    {
        this.screenshot = screenshot;
    }

    public String getScreenshot()
    {
        return screenshot;
    }
    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getOverview()
    {
        return overview;
    }
    public void setDepartmentSort(Long departmentSort)
    {
        this.departmentSort = departmentSort;
    }

    public Long getDepartmentSort()
    {
        return departmentSort;
    }
    public void setSchoolSort(Long schoolSort)
    {
        this.schoolSort = schoolSort;
    }

    public Long getSchoolSort()
    {
        return schoolSort;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public List<WorksMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<WorksMember> memberList) {
        this.memberList = memberList;
    }

    public List<WorksTeacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<WorksTeacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("userId", getUserId())
            .append("report", getReport())
            .append("attachment", getAttachment())
            .append("matchId", getMatchId())
            .append("trackId", getTrackId())
            .append("groupId", getGroupId())
            .append("categoryId", getCategoryId())
            .append("departmentAverageScore", getDepartmentAverageScore())
            .append("schoolAverageScore", getSchoolAverageScore())
            .append("state", getState())
            .append("screenshot", getScreenshot())
            .append("overview", getOverview())
            .append("departmentSort", getDepartmentSort())
            .append("schoolSort", getSchoolSort())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
