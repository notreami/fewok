package com.fewok.dsl.core.execute.logic;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.common.CommonResponse;
import com.fewok.common.common.ErrorInfo;
import com.fewok.dsl.core.execute.ProcessContext;
import lombok.Data;

/**
 * @author notreami on 18/3/26.
 */
@Data
public class LogicResult<T> implements BaseOutput {

    public static final LogicResult SUCCESS = createSuccess(null);
    public static final LogicResult PARAM_INVALID = createError(ErrorInfo.PARAM_INFO);
    public static final LogicResult SYS_ERROR = createError(ErrorInfo.SYS_INFO);

    private boolean success;
    private ErrorInfo errorInfo;
    private T data;

    public static <T> LogicResult<T> createSuccess(T resp) {
        LogicResult<T> response = new LogicResult<T>();
        response.setSuccess(true);
        response.setData(resp);
        return response;
    }

    public static <T> LogicResult<T> createError(ErrorInfo error) {
        LogicResult<T> response = new LogicResult<T>();
        response.setErrorInfo(error);
        response.setSuccess(false);
        response.setData(null);
        return response;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public static <T> CommonResponse<T> getInstance(LogicResult<T> flowResult) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setSuccess(flowResult.isSuccess());
        commonResponse.setData(flowResult.getData());
        if (!flowResult.isSuccess()) {
            commonResponse.setData(flowResult.getData());
            commonResponse.setErrorInfo(flowResult.getErrorInfo());
        }
        return commonResponse;
    }
}
