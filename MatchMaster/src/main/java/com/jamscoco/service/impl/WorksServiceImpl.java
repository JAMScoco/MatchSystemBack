package com.jamscoco.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.domain.WorksMember;
import com.jamscoco.domain.WorksScore;
import com.jamscoco.domain.WorksTeacher;
import com.jamscoco.dto.SortMoveDto;
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
    public List<WorkInfo> selectWorksListExport(Works works) {
        List<WorkInfo> infoList = baseMapper.selectWorksList(works);
        for (WorkInfo info : infoList) {
            info.setMemberList(memberMapper.getMembersByWorkId(info.getId()));
            info.setTeacherList(teacherMapper.getTeachersByWorkId(info.getId()));
            StringBuilder memNames = new StringBuilder();
            StringBuilder teaNames = new StringBuilder();
            for (WorksMember worksMember : info.getMemberList()) {
                memNames.append(worksMember.getName()).append(";");
            }
            for (WorksTeacher worksTeacher : info.getTeacherList()) {
                teaNames.append(worksTeacher.getName()).append(";");
            }
            if (memNames.length() > 0) {
                memNames.setLength(memNames.length() - 1);
            }
            if (teaNames.length() > 0) {
                teaNames.setLength(teaNames.length() - 1);
            }
            info.setMemberNames(memNames.toString());
            info.setTeacherNames(teaNames.toString());
        }
        return infoList;
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
        return baseMapper.waitReviewWorksDepartment(deptId, matchId);
    }

    @Override
    public String delPreAssign(String key) {
        redisCache.deleteObject(GEN_ASSIGN_KEYS + key);
        return "ok";
    }

    @Override
    @Transactional
    public String ensurePreAssign(String key) {
        String type = null;
        boolean knowType = false;
        String json = redisCache.getCacheObject(GEN_ASSIGN_KEYS + key);
        delPreAssign(key);
        List<JSONObject> array = JSON.parseObject(json).getList("previewAssignData", JSONObject.class);
        for (JSONObject jsonObject : array) {
            String workId = jsonObject.getString("workId");
            if (!knowType) {
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
                if (obj instanceof JSONObject) {
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

    @Transactional
    @Override
    public List<WorkInfo> getReviewResult(String matchId, Long roleType, Long deptId) {
        Works works = new Works();
        works.setMatchId(matchId);
        if (roleType == 1L) {
            works.setDeptId(String.valueOf(deptId));
        }
        List<WorkInfo> workInfos = baseMapper.selectWorksList(works);
        if (workInfos.size() > 0) {
            if (roleType == 1L) {
                workInfos.removeIf(e->e.getState().equals(-1L) || e.getState().equals(0L));
                if (workInfos.get(0).getDepartmentSort() != null) {
                    workInfos.sort(Comparator.comparingLong(WorkInfo::getDepartmentSort));
                    return workInfos;
                }
            } else {
                workInfos.removeIf(e -> e.getState().equals(-1L) || e.getState().equals(0L) || e.getState().equals(1L));
                if (workInfos.get(0).getSchoolSort() != null) {
                    workInfos.sort(Comparator.comparingLong(WorkInfo::getSchoolSort));
                    return workInfos;
                }
            }
            setScore(workInfos, roleType);
            setSort(workInfos, roleType);
            return workInfos;
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public int move(SortMoveDto sortMoveDto, Long roleType) {
        if (roleType == 1L) {
            if (sortMoveDto.getType().equals(SortMoveDto.SORT_UP)) {
                return moveUpDepartment(sortMoveDto.getWorkId());
            } else {
                return moveDownDepartment(sortMoveDto.getWorkId());
            }
        } else {
            if (sortMoveDto.getType().equals(SortMoveDto.SORT_UP)) {
                return moveUpSchool(sortMoveDto.getWorkId());
            } else {
                return moveDownSchool(sortMoveDto.getWorkId());
            }
        }
    }

    @Override
    public int recommend(String id, Long roleType) {
        return baseMapper.setState(id, 3 - roleType);
    }

    @Override
    public int removeWork(String id) {
        baseMapper.deleteById(id);
        return 0;
    }

    private int moveUpSchool(String workId) {
        String nextId = baseMapper.selectNextIdOnUpSchoolSort(workId);
        if (nextId == null || "".equals(nextId)) {
            return 0;
        }
        return baseMapper.exchangeSchoolSort(workId, nextId);
    }

    private int moveDownSchool(String workId) {
        String nextId = baseMapper.selectNextIdOnDownSchoolSort(workId);
        if (nextId == null || "".equals(nextId)) {
            return 0;
        }
        return baseMapper.exchangeSchoolSort(workId, nextId);
    }

    private int moveDownDepartment(String workId) {
        String nextId = baseMapper.selectNextIdOnDownDepartment(workId);
        if (nextId == null || "".equals(nextId)) {
            return 0;
        }
        return baseMapper.exchangeDepartmentSort(workId, nextId);
    }

    private int moveUpDepartment(String workId) {
        String nextId = baseMapper.selectNextIdOnUpDepartment(workId);
        if (nextId == null || "".equals(nextId)) {
            return 0;
        }
        return baseMapper.exchangeDepartmentSort(workId, nextId);
    }

    private void setScore(List<WorkInfo> workInfos, Long roleType) {
        for (WorkInfo workInfo : workInfos) {
            if (roleType == 1L) {
                if (workInfo.getDepartmentAverageScore() != null) {
                    continue;
                }
            } else {
                if (workInfo.getSchoolAverageScore() != null) {
                    continue;
                }
            }
            List<String> score_details = worksScoreMapper.selectScoreDetailsByWorkByType(workInfo.getId(), roleType);
            BigDecimal value = BigDecimal.ZERO;
            for (String score_detail : score_details) {
                BigDecimal total = JSON.parseObject(score_detail).getBigDecimal("total");
                value = value.add(total);
            }
            if (roleType == 1L) {
                System.out.println(workInfo.getId()+"###################"+score_details.size());
                workInfo.setDepartmentAverageScore(value.divide(new BigDecimal(score_details.size()), 2, RoundingMode.HALF_UP).doubleValue());
                baseMapper.updateDepartmentAverageScore(workInfo.getId(), workInfo.getDepartmentAverageScore());
            } else {
                workInfo.setSchoolAverageScore(value.divide(new BigDecimal(score_details.size()), 2, RoundingMode.HALF_UP).doubleValue());
                baseMapper.updateSchoolAverageScore(workInfo.getId(), workInfo.getSchoolAverageScore());
            }
        }
    }

    private void setSort(List<WorkInfo> workInfos, Long roleType) {
        if (roleType == 1L) {
            workInfos.sort(Comparator.comparingDouble(WorkInfo::getDepartmentAverageScore).reversed());
            for (int i = 0; i < workInfos.size(); i++) {
                workInfos.get(i).setDepartmentSort(i + 1L);
                baseMapper.updateDepartmentSort(workInfos.get(i).getId(), i + 1);
            }
        } else {
            workInfos.sort(Comparator.comparingDouble(WorkInfo::getSchoolAverageScore).reversed());
            for (int i = 0; i < workInfos.size(); i++) {
                workInfos.get(i).setSchoolSort(i + 1L);
                baseMapper.updateSchoolSort(workInfos.get(i).getId(), i + 1);
            }
        }
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
//        for (WorksTeacher teacher : teacherList) {
//            QueryWrapper<WorksTeacher> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("name", teacher.getName());
//            queryWrapper.eq("department_id", teacher.getDepartmentId());
//            queryWrapper.eq("phone", teacher.getPhone());
//            WorksTeacher worksTeacher = teacherMapper.selectOne(queryWrapper);
//            //欲添加指导老师已在库中
//            if (worksTeacher != null) {
//                int joinNumber = teacherMapper.getJoinNumber(worksTeacher.getId());
//                if (joinNumber >= 2) {
//                    return worksTeacher.getName() + "老师已经达到了可参与项目数量上限，请检查项目指导老师";
//                } else {
//                    teacher.setId(worksTeacher.getId());
//                }
//            }
//        }
        return Constants.SUCCESS;
    }

    private String invalidMember(List<WorksMember> memberList) {
//        for (WorksMember member : memberList) {
//            QueryWrapper<WorksMember> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("name", member.getName());
//            queryWrapper.eq("sno", member.getSno());
//            queryWrapper.eq("department_id", member.getDepartmentId());
//            queryWrapper.eq("phone", member.getPhone());
//            WorksMember worksMember = memberMapper.selectOne(queryWrapper);
//            //欲添加成员已在库中
//            if (worksMember != null) {
//                int joinNumber = memberMapper.getJoinNumber(worksMember.getId());
//                if (joinNumber >= 2) {
//                    return worksMember.getName() + "同学已经达到了可参与项目数量上限，请检查项目成员";
//                } else {
//                    member.setId(worksMember.getId());
//                }
//            }
//        }
        return Constants.SUCCESS;
    }
}
