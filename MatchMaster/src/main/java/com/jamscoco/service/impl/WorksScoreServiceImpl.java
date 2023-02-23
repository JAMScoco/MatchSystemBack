package com.jamscoco.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamscoco.mapper.WorksMapper;
import com.jamscoco.vo.WorkInfo;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksScoreMapper;
import com.jamscoco.domain.WorksScore;
import com.jamscoco.service.IWorksScoreService;

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
     * @param worksScore 评审分值
     * @return 评审分值
     */
    @Override
    public List<WorksScore> selectWorksScoreList(WorksScore worksScore) {
        return baseMapper.selectWorksScoreList(worksScore);
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
}
