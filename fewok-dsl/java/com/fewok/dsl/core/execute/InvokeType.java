package com.fewok.dsl.core.execute;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行模型
 *
 * @author notreami on 18/3/19.
 */
@Getter
@AllArgsConstructor
public enum InvokeType {
    /**
     * 同步:执行结果要merge
     */
    SYNC("同步"),
    /**
     * 异步:执行结果要merge
     */
    ASYNC("异步"),
    /**
     * 并行:不关心结果
     */
    CONCURRENT("并行");

    private String desc;
}
