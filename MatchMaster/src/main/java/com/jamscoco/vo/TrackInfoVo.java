package com.jamscoco.vo;

import lombok.Data;

import java.util.List;

/**
 * 返回赛道的组别类别信息
 */
@Data
public class TrackInfoVo {

    private String id;
    private String trackId;
    private String name;
    private String remark;
    private boolean hasTemplate;
    private List<TrackInfoVo> children;
}
