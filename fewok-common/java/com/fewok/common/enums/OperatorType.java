package com.fewok.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作人类型
 *
 * @author notreami on 17/11/23.
 */
@Getter
@AllArgsConstructor
public enum OperatorType {
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
