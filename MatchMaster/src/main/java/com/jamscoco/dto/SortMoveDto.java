package com.jamscoco.dto;

import lombok.Data;

public class SortMoveDto {

    public static final String SORT_UP = "up";
    public static final String SORT_DOWN = "down";
    private String workId;
    private String type;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
