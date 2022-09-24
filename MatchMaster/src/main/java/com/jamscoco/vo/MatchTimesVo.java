package com.jamscoco.vo;

import lombok.Data;

/**
 * 主页大赛时间线对象
 */
@Data
public class MatchTimesVo {

    public static final String COLOR = "#0bbd87";
    public static final String TYPE = "primary";

    private String content;
    private String timestamp;
    private String type;
    private String color;
}
