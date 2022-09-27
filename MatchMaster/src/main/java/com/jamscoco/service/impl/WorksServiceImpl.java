package com.jamscoco.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.Match;
import com.jamscoco.domain.WorksMember;
import com.jamscoco.domain.WorksTeacher;
import com.jamscoco.mapper.WorksMemberMapper;
import com.jamscoco.mapper.WorksTeacherMapper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksMapper;
import com.jamscoco.domain.Works;
import com.jamscoco.service.IWorksService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作品Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements IWorksService {


    @Autowired
    private WorksTeacherMapper teacherMapper;

    @Autowired
    private WorksMemberMapper memberMapper;

    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品
     */
    @Override
    public List<Works> selectWorksList(Works works) {
        return baseMapper.selectWorksList(works);
    }

    @Override
    @Transactional
    public String addWorks(Works works) {
        String invalidMemberResult = invalidMember(works.getMemberList());
        if (!Constants.SUCCESS.equals(invalidMemberResult)) {
            return invalidMemberResult;
        }
        String invalidTeacherResult = invalidTeacher(works.getTeacherList());
        if (!Constants.SUCCESS.equals(invalidTeacherResult)) {
            return invalidTeacherResult;
        }
        baseMapper.insert(works);
        return insertRelation(works);
    }

    private String insertRelation(Works works) {
        for (WorksMember member : works.getMemberList()) {
            if (member.getId() == null) {
                memberMapper.insert(member);
            }
            memberMapper.insertRelation(member.getId(), works.getId());
        }
        for (WorksTeacher teacher : works.getTeacherList()) {
            if (teacher.getId() == null) {
                teacherMapper.insert(teacher);
            }
            teacherMapper.insertRelation(teacher.getId(), works.getId());
        }
        return Constants.SUCCESS;
    }

    private String invalidTeacher(List<WorksTeacher> teacherList) {
        for (WorksTeacher teacher : teacherList) {
            QueryWrapper<WorksTeacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", teacher.getName());
            queryWrapper.eq("department_id", teacher.getDepartmentId());
            queryWrapper.eq("phone", teacher.getPhone());
            WorksTeacher worksTeacher = teacherMapper.selectOne(queryWrapper);
            //欲添加指导老师已在库中
            if (worksTeacher != null) {
                int joinNumber = teacherMapper.getJoinNumber(worksTeacher.getId());
                //TODO 数量待定
                if (joinNumber >= 2) {
                    return worksTeacher.getName() + "老师已经达到了可参与项目数量上限，请检查项目指导老师";
                } else {
                    teacher.setId(worksTeacher.getId());
                }
            }
        }
        return Constants.SUCCESS;
    }

    private String invalidMember(List<WorksMember> memberList) {
        for (WorksMember member : memberList) {
            QueryWrapper<WorksMember> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", member.getName());
            queryWrapper.eq("sno", member.getSno());
            queryWrapper.eq("department_id", member.getDepartmentId());
            queryWrapper.eq("phone", member.getPhone());
            WorksMember worksMember = memberMapper.selectOne(queryWrapper);
            //欲添加成员已在库中
            if (worksMember != null) {
                int joinNumber = memberMapper.getJoinNumber(worksMember.getId());
                //TODO 数量待定
                if (joinNumber >= 2) {
                    return worksMember.getName() + "同学已经达到了可参与项目数量上限，请检查项目成员";
                } else {
                    member.setId(worksMember.getId());
                }
            }
        }
        return Constants.SUCCESS;
    }
}
