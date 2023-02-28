package com.jamscoco.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class ScoreVo {

    private String id;

    /**  作品id */
    private String workId;

    /**  作品名称 */
    @Excel(name = "作品名称")
    private String name;

    /**评审人id */
    private String userId;

    /**评审人姓名 */
    @Excel(name = "评审人姓名")
    private String trueName;

    @Excel(name = "评分详情")
    private String scoreDetail;
}
