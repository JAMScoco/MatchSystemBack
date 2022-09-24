package com.jamscoco.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.MatchGroupMapper;
import com.jamscoco.domain.MatchGroup;
import com.jamscoco.service.IMatchGroupService;

/**
 * 组别Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Service
public class MatchGroupServiceImpl extends ServiceImpl<MatchGroupMapper,MatchGroup> implements IMatchGroupService
{

    /**
     * 查询组别列表
     *
     * @param matchGroup 组别
     * @return 组别
     */
    @Override
    public List<MatchGroup> selectMatchGroupList(MatchGroup matchGroup)
    {
        return baseMapper.selectMatchGroupList(matchGroup);
    }

    /**
     * 删除trackId为传进来的id的组别
     * @param trackId
     * @return
     */
    @Override
    public boolean deleteGroupByTrackId(String trackId) {
        List<MatchGroup> groupList = baseMapper.selectList(new QueryWrapper<MatchGroup>().eq("track_id", trackId));
        if(groupList.size() == 0){
            return true;
        }else {
            List<String> arrayList = new ArrayList<>();
            for (MatchGroup matchGroup : groupList) {
                arrayList.add(matchGroup.getId());
            }
            return baseMapper.deleteBatchIds(arrayList) > 0;
        }
    }
}
