package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksMapper;
import com.jamscoco.domain.Works;
import com.jamscoco.service.IWorksService;

/**
 * 作品Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper,Works> implements IWorksService
{

    /**
     * 查询作品列表
     *
     * @param works 作品
     * @return 作品
     */
    @Override
    public List<Works> selectWorksList(Works works)
    {
        return baseMapper.selectWorksList(works);
    }
}
