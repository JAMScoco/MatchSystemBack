package com.jamscoco.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.dto.ScoreDto;
import com.jamscoco.dto.ScoreSubmitDto;
import com.jamscoco.mapper.WorksMapper;
import com.jamscoco.vo.ScoreVo;
import com.jamscoco.vo.WorkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksScoreMapper;
import com.jamscoco.domain.WorksScore;
import com.jamscoco.service.IWorksScoreService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评审分值Service业务层处理
 *
 * @author jamscoco
 * @date 2023-02-17
 */
@Service
public class WorksScoreServiceImpl extends ServiceImpl<WorksScoreMapper, WorksScore> implements IWorksScoreService {

    @Autowired
    private WorksMapper worksMapper;

    /**
     * 查询评审分值列表
     *
     * @param scoreDto 检索条件
     * @return 评审分值
     */
    @Override
    public List<ScoreVo> selectWorksScoreList(ScoreDto scoreDto) {
        return baseMapper.selectWorksScoreList(scoreDto);
    }

    @Override
    public boolean checkGenAssign(List<String> waitReviewWorkIds) {
        if (waitReviewWorkIds.size() > 0) {
            WorkInfo workInfo = worksMapper.getWorkInfoById(waitReviewWorkIds.get(0));
            if (workInfo.getState() == 1) {
                return baseMapper.check(workInfo.getId(), 1) > 0;
            }
            if (workInfo.getState() == 2) {
                return baseMapper.check(workInfo.getId(), 0) > 0;
            }
        }
        return false;

    }

    @Override
    public Map<String,Object> getReviewDetails(String matchId, Long type) {
        List<ScoreVo> query = baseMapper.getReviewDetails(matchId,type);
        if (query.size() == 0 ){
            return null;
        }
        List<Map<String,Object>> target = new ArrayList<>();
        List<String> heads = new ArrayList<>();
        Map<String,Object> cur = new HashMap<>();
        int curNum = 0;
        String curWorkName = query.get(0).getName();
        for (ScoreVo score : query) {
            if (score.getName().equals(curWorkName)){
                cur.put("work",curWorkName);
                cur.put("score"+ (curNum++),score);
            }else {
                target.add(cur);
                cur = new HashMap<>();
                curWorkName = score.getName();

                cur.put("work",curWorkName);
                curNum = 0;
                cur.put("score"+ (curNum++),score);
            }
        }
        for (int i = 0; i < curNum; i++) {
            heads.add("score"+ i);
        }
        target.add(cur);
        Map<String,Object> result = new HashMap<>();
        result.put("heads",heads);
        result.put("target",target);
        return result;
    }

    @Override
    @Transactional
    public int submitScore(ScoreSubmitDto scoreSubmitDto) {
        return baseMapper.setScoreDetails(scoreSubmitDto);
    }

    @Override
    public List<Object> getGoalDetail(String workId, Long roleType) {

        return null;
    }
}
