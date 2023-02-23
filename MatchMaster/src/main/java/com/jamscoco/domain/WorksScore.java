package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评审分值对象 works_score
 *
 * @author jamscoco
 * @date 2023-02-17
 */
@TableName("works_score")
public class WorksScore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 作品ID */
    @Excel(name = "作品ID")
    private String workId;

    /** 评审专家ID */
    @Excel(name = "评审专家ID")
    private String userId;

    /** 评审类型 (0=校级，1=院级) */
    @Excel(name = "评审类型 (0=校级，1=院级)")
    private String type;

    /** 评审分值详情 */
    @Excel(name = "评审分值详情")
    private String scoreDetail;

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
    public void setWorkId(String workId)
    {
        this.workId = workId;
    }

    public String getWorkId()
    {
        return workId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setScoreDetail(String scoreDetail)
    {
        this.scoreDetail = scoreDetail;
    }

    public String getScoreDetail()
    {
        return scoreDetail;
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
            .append("workId", getWorkId())
            .append("userId", getUserId())
            .append("type", getType())
            .append("scoreDetail", getScoreDetail())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
