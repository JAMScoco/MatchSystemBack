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
 * 本科生信息对象 school_info_undergraduate_student
 *
 * @author jamscoco
 * @date 2023-04-04
 */
@TableName("school_info_undergraduate_student")
public class SchoolInfoUndergraduateStudent implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 学号 */
    @TableId(type = IdType.INPUT)
    private String sno;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String sex;

    /** 年级 */
    @Excel(name = "年级")
    private String grade;

    /** 学制 */
    @Excel(name = "学制")
    private String year;

    /** 培养层次 */
    @Excel(name = "培养层次")
    private String level;

    /** 学院 */
    @Excel(name = "学院")
    private String dept;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 班级 */
    @Excel(name = "班级")
    private String clazz;

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
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getLevel()
    {
        return level;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDept()
    {
        return dept;
    }
    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getMajor()
    {
        return major;
    }
    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public String getClazz()
    {
        return clazz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sno", getSno())
            .append("name", getName())
            .append("sex", getSex())
            .append("grade", getGrade())
            .append("year", getYear())
            .append("level", getLevel())
            .append("dept", getDept())
            .append("major", getMajor())
            .append("clazz", getClazz())
            .toString();
    }
}
