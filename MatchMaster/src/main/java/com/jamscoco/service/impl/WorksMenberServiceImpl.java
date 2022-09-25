package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksMenberMapper;
import com.jamscoco.domain.WorksMenber;
import com.jamscoco.service.IWorksMenberService;

/**
 * 参与成员Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Service
public class WorksMenberServiceImpl extends ServiceImpl<WorksMenberMapper,WorksMenber> implements IWorksMenberService
{

    /**
     * 查询参与成员列表
     *
     * @param worksMenber 参与成员
     * @return 参与成员
     */
    @Override
    public List<WorksMenber> selectWorksMenberList(WorksMenber worksMenber)
    {
        return baseMapper.selectWorksMenberList(worksMenber);
    }
}
