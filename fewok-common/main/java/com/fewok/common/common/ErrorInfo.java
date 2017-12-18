package com.fewok.common.common;


import lombok.Builder;
import lombok.Data;

import static com.fewok.common.common.ErrorInfo.ErrorCode.*;

/**
 * @author notreami on 17/7/20.
 */
@Data
@Builder
public class ErrorInfo {
    /**
     * 参数异常
     */
    public static final ErrorInfo PARAM_INFO = new ErrorInfo(PARAM_ERROR, "参数异常");
    public static final ErrorInfo PARAM_INFO_CONTACT = new ErrorInfo(PARAM_ERROR_CONTACT, "联系人信息出错");

    /**
     * 处理异常
     */
    public static final ErrorInfo PROCESS_INFO = new ErrorInfo(PROCESS_ERROR, "处理异常");
    public static final ErrorInfo PROCESS_INFO_OFFLINE = new ErrorInfo(PROCESS_ERROR_OFFLINE, "产品已经下线");

    /**
     * 依赖异常
     */
    public static final ErrorInfo DEPT_INFO = new ErrorInfo(DEPT_ERROR, "依赖异常");

    /**
     * 状态异常
     */
    public static final ErrorInfo STATUS_INFO = new ErrorInfo(STATUS_ERROR, "状态异常");
    public static final ErrorInfo STATUS_INFO_AUTH = new ErrorInfo(STATUS_ERROR_AUTH, "需要登陆");
    public static final ErrorInfo STATUS_INFO_ACCESS = new ErrorInfo(STATUS_ERROR_ACCESS, "没有授权");

    /**
     * 系统异常
     */
    public static final ErrorInfo SYS_INFO = new ErrorInfo(SYS_ERROR, "系统异常");
    public static final ErrorInfo SYS_INFO_MAINTAINING = new ErrorInfo(SYS_ERROR_MAINTAINING, "系统维护中,请稍后在试!");


    public static final class ErrorCode {
        /**
         * 参数异常
         */
        public static final int PARAM_ERROR = 100; // 参数异常
        public static final int PARAM_ERROR_CONTACT = 101; // 参数异常(联系人信息出错)

        /**
         * 处理异常
         */
        public static final int PROCESS_ERROR = 200; // 处理异常
        public static final int PROCESS_ERROR_OFFLINE = 201; // 处理异常（产品已经下线）

        /**
         * 依赖异常
         */
        public static final int DEPT_ERROR = 300; // 依赖异常

        /**
         * 状态异常
         */
        public static final int STATUS_ERROR = 400; // 状态异常
        public static final int STATUS_ERROR_AUTH = 401; // 状态异常(需要登陆)
        public static final int STATUS_ERROR_ACCESS = 403; // 状态异常(没有授权)

        /**
         * 系统异常
         */
        public static final int SYS_ERROR = 500; // 系统异常
        public static final int SYS_ERROR_MAINTAINING = 501; // 系统异常(系统维护中)

    }

    private final int value;
    private final String reasonPhrase;

    public static ErrorInfo create(int value, String reasonPhrase) {
        return ErrorInfo.builder().value(value).reasonPhrase(reasonPhrase).build();
    }
}
