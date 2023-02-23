package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.WorksScore;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param worksScore 评审分值
     * @return 评审分值集合
     */
    public List<WorksScore> selectWorksScoreList(WorksScore worksScore);

    boolean checkGenAssign(List<String> waitReviewWorkIds);
}
