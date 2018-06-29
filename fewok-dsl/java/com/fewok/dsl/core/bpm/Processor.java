package com.fewok.dsl.core.bpm;

import com.fewok.dsl.core.bpm.container.ProcessRule;

/**
 * 流程定义
 *
 * @param <IN>  输入
 * @param <OUT> 输出
 * @author notreami on 18/5/31.
 */
public interface Processor<IN, OUT> {

    /**
     * 流程
     *
     * @param input 输入
     * @return 输出
     */
    OUT process(IN input);

    /**
     * 流程名称
     *
     * @return 流程名称
     */
    default String getProcessorName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 路由配置
     *
     * @return
     */
    default ProcessRule getProcessRule() {
        return ProcessRule.builder().build();
    }
}
