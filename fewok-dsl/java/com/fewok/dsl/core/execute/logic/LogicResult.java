package com.fewok.dsl.core.execute.logic;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.common.ErrorInfo;
import com.fewok.dsl.core.execute.ProcessContext;
import lombok.Data;

/**
 * @author notreami on 18/3/26.
 */
@Data
public class LogicResult<P extends ProcessContext> implements BaseOutput {

    public static final LogicResult SUCCESS = new LogicResult(true);
    public static final LogicResult FAIL = new LogicResult(false);

    private boolean success;
    private ErrorInfo errorInfo;
    private P processContext;

    public LogicResult(boolean success) {
        this.success = success;
    }

    public LogicResult(P processContext) {
        this.success = true;
        this.processContext = processContext;
    }

    public LogicResult(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }
}
