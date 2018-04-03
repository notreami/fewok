package com.fewok.dsl.core.execute;

import com.fewok.common.common.BaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程控控制基本流程
 * @author notreami on 18/3/26.
 */
@Slf4j
public abstract class BaseProcessor<IN, OUT extends BaseOutput> implements Processor<IN, OUT> {

    @Override
    public OUT process(IN input) {
        OUT output = null;
        try {
            preProcess(input);
            ValidResult validResult = checkInput(input);
            if (!validResult.isSuccess()) {
                log.error("Processor前置验证失败: processor={}, message={}", this.getClass().getSimpleName(), validResult.getMessage());
                return createValidFail(validResult);
            }
            beforeProcess(this, input);
            output = doProcess(input);
            afterProcess(this, input, output);
            return output;
        } catch (Exception e) {
            return handleException(input, output, e);
        }
    }

    protected void preProcess(IN input) {
        // do something preProcess
    }

    protected ValidResult checkInput(IN input) {
        return ValidResult.SUCCESS;
    }

    protected abstract OUT createValidFail(ValidResult validResult);


    protected void beforeProcess(Processor<IN, OUT> processor, IN input) {
        // do something beforeProcess
    }

    protected abstract OUT doProcess(IN input) throws Exception;


    protected void afterProcess(Processor<IN, OUT> processor, IN input, OUT output) {
        // do something afterProcess
    }

    protected abstract OUT handleException(IN input, OUT output, Exception e);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class ValidResult implements BaseOutput {
        public static final ValidResult SUCCESS = new ValidResult(true, "验证成功");
        private boolean success;
        private String message;
    }
}
