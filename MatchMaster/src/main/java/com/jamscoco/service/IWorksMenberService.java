package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.WorksMenber;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 参与成员Service接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
public interface IWorksMenberService extends IService<WorksMenber>
{
    /**
     * 查询参与成员列表
     *
     * @param worksMenber 参与成员
     * @return 参与成员集合
     */
    public List<WorksMenber> selectWorksMenberList(WorksMenber worksMenber);
}
