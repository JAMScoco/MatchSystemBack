package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.MatchGroup;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 组别Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-21
 */
@Mapper
public interface MatchGroupMapper extends BaseMapper<MatchGroup>
{
    /**
     * 查询组别列表
     *
     * @param matchGroup 组别
     * @return 组别集合
     */
    public List<MatchGroup> selectMatchGroupList(MatchGroup matchGroup);

}
