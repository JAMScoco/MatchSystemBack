package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.WorksMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 参与成员Service接口
 *
 * @author jamscoco
 * @date 2022-09-26
 */
public interface IWorksMemberService extends IService<WorksMember>
{
    /**
     * 查询参与成员列表
     *
     * @param worksMember 参与成员
     * @return 参与成员集合
     */
    public List<WorksMember> selectWorksMemberList(WorksMember worksMember);
}
