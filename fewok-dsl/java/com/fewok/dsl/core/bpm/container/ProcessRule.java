package com.fewok.dsl.core.bpm.container;

import com.fewok.dsl.core.bpm.type.InvokeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路由配置
 * @author notreami on 18/5/31.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRule {

    /**
     * 执行方式（同步、异步、并行）（默认 同步）
     */
    @Builder.Default
    private InvokeType invokeType = InvokeType.SYNC;

    /**
     * 等待之前的流程执行
     */
    @Builder.Default
    private boolean isWaitBeforeProcessor = false;

    /**
     * 重试次数（默认 0 次）
     */
    @Builder.Default
    private int retryCount = 0;

    /**
     * 每个流程执行的超时时间（默认 1 s）
     */
    @Builder.Default
    private int timeoutMillis = 1000;
}
