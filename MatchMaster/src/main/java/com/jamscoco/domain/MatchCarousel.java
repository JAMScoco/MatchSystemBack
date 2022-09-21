package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发布赛事的轮播图对象 match_carousel
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@TableName("match_carousel")
public class MatchCarousel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 图片url */
    @Excel(name = "图片url")
    private String url;

    /** 链接 */
    @Excel(name = "链接")
    private String link;

    /** 大赛ID */
    private String matchId;

    /** 图片顺序 */
    @Excel(name = "图片顺序")
    private Long sort;

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
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setLink(String link)
    {
        this.link = link;
    }

    public String getLink()
    {
        return link;
    }
    public void setMatchId(String matchId)
    {
        this.matchId = matchId;
    }

    public String getMatchId()
    {
        return matchId;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
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
            .append("url", getUrl())
            .append("link", getLink())
            .append("matchId", getMatchId())
            .append("sort", getSort())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
