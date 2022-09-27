package com.jamscoco.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.jamscoco.mapper.WorksMemberMapper;
import com.jamscoco.domain.WorksMember;
import com.jamscoco.service.IWorksMemberService;

/**
 * 参与成员Service业务层处理
 *
 * @author jamscoco
 * @date 2022-09-26
 */
@Service
public class WorksMemberServiceImpl extends ServiceImpl<WorksMemberMapper,WorksMember> implements IWorksMemberService
{

    /**
     * 查询参与成员列表
     *
     * @param worksMember 参与成员
     * @return 参与成员
     */
    @Override
    public List<WorksMember> selectWorksMemberList(WorksMember worksMember)
    {
        return baseMapper.selectWorksMemberList(worksMember);
    }
}
