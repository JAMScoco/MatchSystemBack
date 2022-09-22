package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.MatchGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 组别Service接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
public interface IMatchGroupService extends IService<MatchGroup>
{
    /**
     * 查询组别列表
     *
     * @param matchGroup 组别
     * @return 组别集合
     */
    public List<MatchGroup> selectMatchGroupList(MatchGroup matchGroup);
}
