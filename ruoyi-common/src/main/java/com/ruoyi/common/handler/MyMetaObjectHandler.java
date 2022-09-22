package com.ruoyi.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.ruoyi.common.utils.SecurityUtils.getUsername;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", getUsername(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", getUsername(),metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("delFlag", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateBy", getUsername(),metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
