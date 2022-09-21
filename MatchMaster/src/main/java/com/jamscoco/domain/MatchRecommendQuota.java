package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 推荐的作品名额对象 match_recommend_quota
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@TableName("match_recommend_quota")
public class MatchRecommendQuota extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 院系ID */
    @Excel(name = "院系ID")
    private String departmentId;

    /** 院系名额 */
    @Excel(name = "院系名额")
    private Long quota;

    /** 大赛ID */
    private String matchId;

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
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }
    public void setQuota(Long quota)
    {
        this.quota = quota;
    }

    public Long getQuota()
    {
        return quota;
    }
    public void setMatchId(String matchId)
    {
        this.matchId = matchId;
    }

    public String getMatchId()
    {
        return matchId;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("departmentId", getDepartmentId())
            .append("quota", getQuota())
            .append("matchId", getMatchId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
