package com.jamscoco.mapper;

import java.util.List;
import com.jamscoco.domain.WorksMenber;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 参与成员Mapper接口
 *
 * @author jamscoco
 * @date 2022-09-25
 */
@Mapper
public interface WorksMenberMapper extends BaseMapper<WorksMenber>
{
    /**
     * 查询参与成员列表
     *
     * @param worksMenber 参与成员
     * @return 参与成员集合
     */
    public List<WorksMenber> selectWorksMenberList(WorksMenber worksMenber);

}
