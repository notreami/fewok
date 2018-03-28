package com.fewok.biz.biz;

import com.fewok.dsl.core.execute.ProcessContext;
import com.fewok.dsl.core.execute.BaseProcessor;
import com.fewok.dsl.core.execute.logic.LogicResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 18/3/21.
 */
@Slf4j
public abstract class BaseProcessContextProcessor<P extends ProcessContext> extends BaseProcessor<P, LogicResult> {
    @Override
    protected LogicResult createValidFail(ValidResult validResult) {
        return LogicResult.FAIL;
    }

    @Override
    protected LogicResult handleException(P processContext, LogicResult logicResult, Exception e) {
        log.warn("Process[{}]处理异常,transactionId={}", this.getClass().getSimpleName(), processContext.getObjectId(), e);
        if (logicResult != null) {
            return logicResult;
        } else {
            return LogicResult.FAIL;
        }
    }

}
