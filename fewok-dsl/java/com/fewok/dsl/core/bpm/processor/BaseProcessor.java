package com.fewok.dsl.core.bpm.processor;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.core.bpm.Processor;
import com.fewok.dsl.core.bpm.container.ExecuteResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程基本执行过程
 *
 * @author notreami on 18/5/31.
 */
@Slf4j
public abstract class BaseProcessor<IN, OUT extends BaseOutput> implements Processor<IN, OUT> {

    /**
     * 流程基本执行过程
     *
     * @param input 输入
     * @return 输出
     */
    @Override
    public OUT process(IN input) {
        OUT output = null;
        try {
            preProcess(input);
            ExecuteResult executeResult = checkInput(input);
            if (!executeResult.isSuccess()) {
                log.error("Processor前置验证失败: processor={}, message={}, input={}", getProcessorName(), executeResult.getStatusInfo().getMessage(), JsonBinder.toJSONString(input));
                return handleCheckFail(executeResult);
            }
            beforeProcess(input);
            output = doProcess(input);
            afterProcess(input, output);
            return output;
        } catch (Exception e) {
            return handleException(input, output, e);
        }
    }

    /**
     * 预处理
     *
     * @param input 输入
     */
    protected void preProcess(IN input) {
        // do something preProcess
    }

    /**
     * 参数校验/活动流程规则判断
     *
     * @param input 输入
     * @return 校验结果
     */
    protected ExecuteResult checkInput(IN input) {
        return ExecuteResult.OK;
    }

    /**
     * 参数校验失败处理
     *
     * @param executeResult 校验结果
     * @return 输出
     */
    protected abstract OUT handleCheckFail(ExecuteResult executeResult);

    /**
     * 流程执行前
     *
     * @param input 输入
     */
    protected void beforeProcess(IN input) {
        // do something beforeProcess
    }

    /**
     * 流程执行
     *
     * @param input 输入
     * @return 输出
     * @throws Exception 异常
     */
    protected abstract OUT doProcess(IN input) throws Exception;

    /**
     * 流程执行后
     *
     * @param input  输入
     * @param output 输出
     */
    protected void afterProcess(IN input, OUT output) {
        // do something afterProcess
    }

    /**
     * 流程执行异常处理
     *
     * @param input  输入
     * @param output 输出
     * @param e      异常
     * @return 最终输出
     */
    protected abstract OUT handleException(IN input, OUT output, Exception e);
}
