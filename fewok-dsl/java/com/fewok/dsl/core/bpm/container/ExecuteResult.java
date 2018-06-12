package com.fewok.dsl.core.bpm.container;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.common.StatusInfo;
import lombok.Data;

/**
 * 一个活动流程执行结果
 *
 * @author notreami on 18/5/31.
 */
@Data
public class ExecuteResult<T> implements BaseOutput {

    public static final ExecuteResult OK = createSuccess(StatusInfo.OK);
    public static final ExecuteResult SKIP = createSuccess(StatusInfo.SKIP);
    public static final ExecuteResult PARAM_ERROR = createError(StatusInfo.PARAM_ERROR);
    public static final ExecuteResult PARAM_CHECK_ERROR = ExecuteResult.createError(StatusInfo.create(StatusInfo.StatusCode.PARAM_ERROR, "参数校验错误，getClientInfo()为null 或 getData()为null"));
    public static final ExecuteResult UNKNOWN_ERROR = createError(StatusInfo.UNKNOWN_ERROR);

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态信息
     */
    private StatusInfo statusInfo;

    /**
     * 异常栈
     */
    private Exception exception;

    /**
     * 执行结果数据
     */
    private T data;


    public static <T> ExecuteResult<T> createSuccess(StatusInfo info) {
        ExecuteResult<T> result = new ExecuteResult<>();
        result.setSuccess(true);
        result.setStatusInfo(info);
        return result;
    }

    public static <T> ExecuteResult<T> createSuccess(T resp, StatusInfo info) {
        ExecuteResult<T> result = new ExecuteResult<>();
        result.setSuccess(true);
        result.setStatusInfo(info);
        result.setData(resp);
        return result;
    }

    public static <T> ExecuteResult<T> createError(StatusInfo info) {
        ExecuteResult<T> result = new ExecuteResult<>();
        result.setSuccess(false);
        result.setStatusInfo(info);
        return result;
    }
}
