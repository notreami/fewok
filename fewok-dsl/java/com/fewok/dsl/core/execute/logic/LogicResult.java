package com.fewok.dsl.core.execute.logic;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.common.ErrorInfo;
import lombok.Data;

/**
 * @author notreami on 18/3/26.
 */
@Data
public class LogicResult<D> implements BaseOutput {

    public static final LogicResult SUCCESS = new LogicResult(true);
    public static final LogicResult FAIL = new LogicResult(false);

    private boolean success;
    private ErrorInfo errorInfo;
    private D data;

    public LogicResult(boolean success) {
        this.success = success;
    }

    public LogicResult(D data) {
        this.success = true;
        this.data = data;
    }

    public LogicResult(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }


    public static <D> LogicResult<D> newLogicResult(boolean suc, D data) {
        LogicResult<D> result = new LogicResult<D>(suc);
        result.setData(data);
        return result;
    }

    public static <D> LogicResult<D> newLogicResult(ErrorInfo errorInfo) {
        LogicResult<D> result = new LogicResult<D>(false);
        result.setErrorInfo(errorInfo);
        return result;
    }
}
