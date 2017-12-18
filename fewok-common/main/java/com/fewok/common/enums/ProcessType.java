package com.fewok.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作流程
 *
 * @author notreami on 17/11/22.
 */
@Getter
@AllArgsConstructor
public enum ProcessType {
    /**
     * 创建账户
     */
    CREATE_ACCOUNT(0, false, Group.CUSTOMER, "创建账户"),
    /**
     * 申请访问token
     */
    APPLY_ACCESS_TOKEN(1, false, Group.CUSTOMER, "申请访问token"),

    /**
     * 释放访问token
     */
    FREE_ACCESS_TOKEN(2, false, Group.CUSTOMER, "释放访问token");


    private int code;
    /**
     * 是否区分flow 类型
     */
    private boolean isFlow;
    private Group group;
    private String description;

    /**
     * 操作流程组
     */
    @Getter
    @AllArgsConstructor
    public enum Group {
        /**
         * 系统
         */
        SYSTEM(0),
        /**
         * 客服
         */
        CUSTOMER_SERVICE(1),
        /**
         * 商家
         */
        MERCHANTS(2),
        /**
         * 用户
         */
        CUSTOMER(3);

        private int code;
    }
}
