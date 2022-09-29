package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.Works;
import com.jamscoco.vo.WorkInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 作品Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Mapper
public interface WorksMapper extends BaseMapper<Works>
{
    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品集合
     */
    public List<WorkInfo> selectWorksList(Works works);

    WorkInfo getWorkInfoById(String id);

    int check(Works works);
}
