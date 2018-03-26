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
}
