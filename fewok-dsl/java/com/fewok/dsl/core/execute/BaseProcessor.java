package com.fewok.dsl.core.execute;

import com.fewok.common.common.BaseOutput;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 18/3/26.
 */
@Slf4j
public abstract class BaseProcessor<IN, OUT extends BaseOutput> implements Processor<IN, OUT> {
    @Override
    public OUT process(IN input) {
        OUT output = null;
        try {
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

    protected OUT handleException(IN input, OUT output, Exception e) {
        return output;
    }

    @Data
    protected static class ValidResult implements BaseOutput {
        public static final ValidResult SUCCESS = new ValidResult(true, "验证成功");
        private boolean success;
        private String message;

        public ValidResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        @Override
        public boolean isSuccess() {
            return success;
        }
    }
}
