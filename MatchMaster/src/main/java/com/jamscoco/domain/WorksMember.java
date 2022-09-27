package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 参与成员对象 works_member
 *
 * @author jamscoco
 * @date 2022-09-26
 */
@TableName("works_member")
public class WorksMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 学号 */
    @Excel(name = "学号")
    private String sno;

    /** 院系ID */
    @Excel(name = "院系ID")
    private String departmentId;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

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
    public void setSno(String sno)
    {
        this.sno = sno;
    }

    public String getSno()
    {
        return sno;
    }
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }
    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getMajor()
    {
        return major;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
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
            .append("name", getName())
            .append("sno", getSno())
            .append("departmentId", getDepartmentId())
            .append("major", getMajor())
            .append("phone", getPhone())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
