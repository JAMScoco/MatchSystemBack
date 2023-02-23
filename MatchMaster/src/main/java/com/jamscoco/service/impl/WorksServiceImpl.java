package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.WorksMember;
import com.jamscoco.domain.WorksScore;
import com.jamscoco.domain.WorksTeacher;
import com.jamscoco.mapper.WorksMemberMapper;
import com.jamscoco.mapper.WorksScoreMapper;
import com.jamscoco.mapper.WorksTeacherMapper;
import com.jamscoco.vo.WorkInfo;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksMapper;
import com.jamscoco.domain.Works;
import com.jamscoco.service.IWorksService;
import org.springframework.transaction.annotation.Transactional;

import static com.ruoyi.common.constant.CacheConstants.GEN_ASSIGN_KEYS;

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
    @Autowired
    private WorksScoreMapper worksScoreMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品
     */
    @Override
    public List<WorkInfo> selectWorksList(Works works) {
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
        works.setState(0L);
        baseMapper.insert(works);
        return insertRelation(works);
    }

    @Override
    public WorkInfo getWorkInfoById(String id) {
        WorkInfo info = baseMapper.getWorkInfoById(id);
        info.setMemberList(memberMapper.getMembersByWorkId(id));
        info.setTeacherList(teacherMapper.getTeachersByWorkId(id));
        return info;
    }

    @Override
    public Works currentMatchWork(Long userId, String matchId) {
        QueryWrapper<Works> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("match_id", matchId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int check(Works works) {
        return baseMapper.check(works);
    }

    @Override
    public List<String> waitReviewWorksDepartment(Long deptId, String matchId) {
        return baseMapper.waitReviewWorksDepartment(deptId,matchId);
    }

    @Override
    public String delPreAssign(String key) {
        redisCache.deleteObject(GEN_ASSIGN_KEYS+key);
        return "ok";
    }

    @Override
    @Transactional
    public String ensurePreAssign(String key) {
        String type = null;
        boolean knowType = false;
        String json = redisCache.getCacheObject(GEN_ASSIGN_KEYS+key);
        delPreAssign(key);
        List<JSONObject> array = JSON.parseObject(json).getList("previewAssignData",JSONObject.class);
        for (JSONObject jsonObject : array) {
            String workId = jsonObject.getString("workId");
            if (!knowType){
                WorkInfo work = baseMapper.getWorkInfoById(workId);
                if (work.getState() == 1) {
                    type = "1";
                }
                if (work.getState() == 2) {
                    type = "0";
                }
                knowType = true;
            }
            for (String k : jsonObject.keySet()) {
                Object obj = jsonObject.get(k);
                if(obj instanceof JSONObject){
                    String userId = ((JSONObject) obj).getString("userId");
                    WorksScore worksScore = new WorksScore();
                    worksScore.setWorkId(workId);
                    worksScore.setUserId(userId);
                    worksScore.setType(type);
                    worksScoreMapper.insert(worksScore);
                }
            }
        }
        return "ok";
    }

    @Override
    public List<String> waitReviewWorksSchool(String matchId) {
        return baseMapper.waitReviewWorksSchool(matchId);
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
