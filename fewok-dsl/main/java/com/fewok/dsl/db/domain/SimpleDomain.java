package com.fewok.dsl.db.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author notreami on 17/6/24.
 */
@Data
public class SimpleDomain {
    private long id;
    private Date createtime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updatetime;
}
