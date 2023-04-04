package com.jamscoco.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 研究生对象 school_info_garduate_student
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@TableName("school_info_garduate_student")
public class SchoolInfoGarduateStudent implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(type = IdType.INPUT)
    private String sno;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String code;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String major;

    /** 学院 */
    @Excel(name = "学院")
    private String dept;

    /** 年级 */
    @Excel(name = "年级")
    private String grade;

    public void setSno(String sno)
    {
        this.sno = sno;
    }

    public String getSno()
    {
        return sno;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getMajor()
    {
        return major;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDept()
    {
        return dept;
    }
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sno", getSno())
            .append("name", getName())
            .append("sex", getSex())
            .append("code", getCode())
            .append("major", getMajor())
            .append("dept", getDept())
            .append("grade", getGrade())
            .toString();
    }
}
