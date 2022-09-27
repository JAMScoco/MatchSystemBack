package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.Works;
import com.baomidou.mybatisplus.extension.service.IService;

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
    public List<Works> selectWorksList(Works works);

    /**
     * 添加作品
     * @param works
     * @return
     */
    String addWorks(Works works);
}
