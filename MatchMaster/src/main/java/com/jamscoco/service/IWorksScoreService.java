package com.jamscoco.service;

import java.util.List;
import java.util.Map;

import com.jamscoco.domain.WorksScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jamscoco.dto.ScoreDto;
import com.jamscoco.dto.ScoreSubmitDto;
import com.jamscoco.vo.ScoreVo;

/**
 * 评审分值Service接口
 *
 * @author jamscoco
 * @date 2023-02-17
 */
public interface IWorksScoreService extends IService<WorksScore>
{
    /**
     * 查询评审分值列表
     *
     * @param scoreDto 检索条件
     * @return 评审分值集合
     */
    public List<ScoreVo> selectWorksScoreList(ScoreDto scoreDto);

    boolean checkGenAssign(List<String> waitReviewWorkIds);

    Map<String,Object> getReviewDetails(String matchId, Long type);

    int submitScore(ScoreSubmitDto scoreSubmitDto);

    List<Object> getGoalDetail(String id, Long roleType);
}
