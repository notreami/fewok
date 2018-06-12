package com.fewok.dsl.core.bpm.container;

import com.fewok.common.common.BaseOutput;
import com.fewok.common.common.StatusInfo;
import com.fewok.dsl.core.bpm.processor.BaseActivityProcessor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 一组流程执行结果
 *
 * @author notreami on 18/5/31.
 */
@Data
public class ProcessResult<T> implements BaseOutput {

    public static final ProcessResult SKIP = createSuccess(StatusInfo.SKIP);
    public static final ProcessResult PARAM_ERROR = createError(StatusInfo.PARAM_ERROR);
    public static final ProcessResult EXIST_FAIL = createError(StatusInfo.EXIST_FAIL);

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


    public static <T> ProcessResult<T> createSuccess(StatusInfo info) {
        ProcessResult<T> result = new ProcessResult<>();
        result.setSuccess(true);
        result.setStatusInfo(info);
        return result;
    }

    public static <T> ProcessResult<T> createSuccess(T resp, StatusInfo info) {
        ProcessResult<T> result = new ProcessResult<>();
        result.setSuccess(true);
        result.setStatusInfo(info);
        result.setData(resp);
        return result;
    }

    public static <T> ProcessResult<T> createError(StatusInfo info) {
        ProcessResult<T> result = new ProcessResult<>();
        result.setSuccess(false);
        result.setStatusInfo(info);
        return result;
    }

    public static <T> ProcessResult<T> createError(T resp, StatusInfo info) {
        ProcessResult<T> result = new ProcessResult<>();
        result.setSuccess(false);
        result.setStatusInfo(info);
        result.setData(resp);
        return result;
    }
}
