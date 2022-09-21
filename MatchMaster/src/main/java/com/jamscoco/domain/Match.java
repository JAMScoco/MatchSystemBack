package com.jamscoco.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 赛事对象 match_info
 *
 * @author jamscoco
 * @date 2022-09-20
 */
@TableName("match_info")
public class Match extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 大赛名称 */
    @Excel(name = "大赛名称")
    private String name;

    /** 大赛logo（链接） */
    @Excel(name = "大赛logo链接")
    private String logo;

    /** 大赛开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "大赛开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 大赛结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "大赛结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 大赛组委会 */
    @Excel(name = "大赛组委会")
    private String organizingCommittee;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 邮编 */
    @Excel(name = "邮编")
    private String postcode;

    /** 大赛投诉邮箱 */
    @Excel(name = "大赛投诉邮箱")
    private String email;

    /** 专家委员会 */
    @Excel(name = "专家委员会")
    private String expertCommittee;

    /** 二维码（链接） */
    private String qrCode;

    /** 开始提交作品时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始提交作品时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startSubmitTime;

    /** 结束提交作品时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束提交作品时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endSubmitTime;

    /** 院系评审专家开始评审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "院系评审专家开始评审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startReviewTimeDepartment;

    /** 院系评审专家结束评审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "院系评审专家结束评审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endReviewTimeDepartment;

    /** 校级评审专家开始评审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "校级评审专家开始评审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startReviewTimeSchool;

    /** 校级评审专家结束评审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "校级评审专家结束评审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endReviewTimeSchool;

    /** 大赛通知（资料下载链接） */
    @Excel(name = "大赛通知（资料下载链接）")
    private String competitionNotice;

    /** 评审规则（资料下载链接） */
    @Excel(name = "评审规则（资料下载链接）")
    private String reviewRules;

    /** 学生操作手册（资料下载链接） */
    @Excel(name = "学生操作手册（资料下载链接）")
    private String studentOperationManual;

    /** 大赛指南（资料下载链接） */
    @Excel(name = "大赛指南（资料下载链接）")
    private String competitionGuide;

    /** 院级校级操作手册（资料下载链接） */
    @Excel(name = "院级校级操作手册（资料下载链接）")
    private String collegeSchoolOperationManual;

    /** 商业合作邀请函（资料下载链接） */
    @Excel(name = "商业合作邀请函（资料下载链接）")
    private String businessCooperationInvitation;

    /** 大赛评审手册（资料下载链接） */
    @Excel(name = "大赛评审手册（资料下载链接）")
    private String competitionReviewManual;

    /** 每个作品评审人数 */
    private Long reviewNumber;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

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
    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getLogo()
    {
        return logo;
    }
    public void setOrganizingCommittee(String organizingCommittee)
    {
        this.organizingCommittee = organizingCommittee;
    }

    public String getOrganizingCommittee()
    {
        return organizingCommittee;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public String getPostcode()
    {
        return postcode;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setExpertCommittee(String expertCommittee)
    {
        this.expertCommittee = expertCommittee;
    }

    public String getExpertCommittee()
    {
        return expertCommittee;
    }
    public void setQrCode(String qrCode)
    {
        this.qrCode = qrCode;
    }

    public String getQrCode()
    {
        return qrCode;
    }
    public void setStartSubmitTime(Date startSubmitTime)
    {
        this.startSubmitTime = startSubmitTime;
    }

    public Date getStartSubmitTime()
    {
        return startSubmitTime;
    }
    public void setEndSubmitTime(Date endSubmitTime)
    {
        this.endSubmitTime = endSubmitTime;
    }

    public Date getEndSubmitTime()
    {
        return endSubmitTime;
    }
    public void setStartReviewTimeDepartment(Date startReviewTimeDepartment)
    {
        this.startReviewTimeDepartment = startReviewTimeDepartment;
    }

    public Date getStartReviewTimeDepartment()
    {
        return startReviewTimeDepartment;
    }
    public void setEndReviewTimeDepartment(Date endReviewTimeDepartment)
    {
        this.endReviewTimeDepartment = endReviewTimeDepartment;
    }

    public Date getEndReviewTimeDepartment()
    {
        return endReviewTimeDepartment;
    }
    public void setStartReviewTimeSchool(Date startReviewTimeSchool)
    {
        this.startReviewTimeSchool = startReviewTimeSchool;
    }

    public Date getStartReviewTimeSchool()
    {
        return startReviewTimeSchool;
    }
    public void setEndReviewTimeSchool(Date endReviewTimeSchool)
    {
        this.endReviewTimeSchool = endReviewTimeSchool;
    }

    public Date getEndReviewTimeSchool()
    {
        return endReviewTimeSchool;
    }
    public void setCompetitionNotice(String competitionNotice)
    {
        this.competitionNotice = competitionNotice;
    }

    public String getCompetitionNotice()
    {
        return competitionNotice;
    }
    public void setReviewRules(String reviewRules)
    {
        this.reviewRules = reviewRules;
    }

    public String getReviewRules()
    {
        return reviewRules;
    }
    public void setStudentOperationManual(String studentOperationManual)
    {
        this.studentOperationManual = studentOperationManual;
    }

    public String getStudentOperationManual()
    {
        return studentOperationManual;
    }
    public void setCompetitionGuide(String competitionGuide)
    {
        this.competitionGuide = competitionGuide;
    }

    public String getCompetitionGuide()
    {
        return competitionGuide;
    }
    public void setCollegeSchoolOperationManual(String collegeSchoolOperationManual)
    {
        this.collegeSchoolOperationManual = collegeSchoolOperationManual;
    }

    public String getCollegeSchoolOperationManual()
    {
        return collegeSchoolOperationManual;
    }
    public void setBusinessCooperationInvitation(String businessCooperationInvitation)
    {
        this.businessCooperationInvitation = businessCooperationInvitation;
    }

    public String getBusinessCooperationInvitation()
    {
        return businessCooperationInvitation;
    }
    public void setCompetitionReviewManual(String competitionReviewManual)
    {
        this.competitionReviewManual = competitionReviewManual;
    }

    public String getCompetitionReviewManual()
    {
        return competitionReviewManual;
    }
    public void setReviewNumber(Long reviewNumber)
    {
        this.reviewNumber = reviewNumber;
    }

    public Long getReviewNumber()
    {
        return reviewNumber;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("logo", getLogo())
            .append("organizingCommittee", getOrganizingCommittee())
            .append("address", getAddress())
            .append("postcode", getPostcode())
            .append("email", getEmail())
            .append("expertCommittee", getExpertCommittee())
            .append("qrCode", getQrCode())
            .append("startSubmitTime", getStartSubmitTime())
            .append("endSubmitTime", getEndSubmitTime())
            .append("startReviewTimeDepartment", getStartReviewTimeDepartment())
            .append("endReviewTimeDepartment", getEndReviewTimeDepartment())
            .append("startReviewTimeSchool", getStartReviewTimeSchool())
            .append("endReviewTimeSchool", getEndReviewTimeSchool())
            .append("competitionNotice", getCompetitionNotice())
            .append("reviewRules", getReviewRules())
            .append("studentOperationManual", getStudentOperationManual())
            .append("competitionGuide", getCompetitionGuide())
            .append("collegeSchoolOperationManual", getCollegeSchoolOperationManual())
            .append("businessCooperationInvitation", getBusinessCooperationInvitation())
            .append("competitionReviewManual", getCompetitionReviewManual())
            .append("reviewNumber", getReviewNumber())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .toString();
    }
}
