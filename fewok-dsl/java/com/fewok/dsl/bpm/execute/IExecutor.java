package com.fewok.dsl.bpm.execute;

import com.fewok.common.common.BaseInput;
import com.fewok.common.common.CommonInput;
import com.fewok.common.common.CommonOutput;
import com.fewok.dsl.bpm.common.ProcessDefinition;

/**
 * @author notreami on 17/11/23.
 */
public interface IExecutor<IN extends BaseInput, OUT> {
    /**
     * @param processDefinition
     * @param commonInput
     * @return
     */
    ProcessFuture<CommonOutput<OUT>> execute(ProcessDefinition<IN, OUT> processDefinition, CommonInput<IN> commonInput);
}
