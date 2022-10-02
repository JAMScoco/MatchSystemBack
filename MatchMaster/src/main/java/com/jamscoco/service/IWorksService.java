package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.Works;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jamscoco.vo.WorkInfo;

/**
 * 作品Service接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
public interface IWorksService extends IService<Works>
{
    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品集合
     */
    public List<WorkInfo> selectWorksList(Works works);

    /**
     * 添加作品
     * @param works 作品
     * @return 结果
     */
    String addWorks(Works works);

    WorkInfo getWorkInfoById(String id);

    /**
     * @param userId 当前用户id
     * @param matchId 当前赛事id
     * @return 当前用户在当前赛事作品
     */
    Works currentMatchWork(Long userId, String matchId);

    int check(Works works);

    /**
     * 获取当前赛事本院系待评审作品id
     * @param deptId 院系id
     * @param matchId 当前赛事id
     * @return 当前赛事本院系待评审作品id
     */
    List<String> waitReviewWorksDepartment(Long deptId, String matchId);
}
