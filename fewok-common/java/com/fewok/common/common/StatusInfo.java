package com.fewok.common.common;


import lombok.Builder;
import lombok.Data;

/**
 * @author notreami on 17/7/20.
 */
@Data
public class StatusInfo {

    /**
     * 成功
     */
    public static final StatusInfo OK = StatusInfo.create(StatusCode.OK, "成功");
    public static final StatusInfo SKIP = StatusInfo.create(StatusCode.SKIP, "跳过");

    /**
     * 请求错误
     */
    public static final StatusInfo PARAM_ERROR = StatusInfo.create(StatusCode.PARAM_ERROR, "参数异常");
    public static final StatusInfo UNAUTHORIZED_ERROR = StatusInfo.create(StatusCode.UNAUTHORIZED_ERROR, "需要登陆");
    public static final StatusInfo FORBIDDEN_ERROR = StatusInfo.create(StatusCode.FORBIDDEN_ERROR, "没有权限");

    /**
     * 服务器错误
     */
    public static final StatusInfo UNKNOWN_ERROR = StatusInfo.create(StatusCode.UNKNOWN_ERROR, "未知的服务错误");


    /**
     * 服务特性错误
     */
    public static final StatusInfo EXIST_FAIL = StatusInfo.create(StatusCode.EXIST_FAIL, "未执行");


    /**
     * 以HttpStatus为主
     * 同等类型，从*60开始增加状态码
     * 业务错误，从7xx、8xx开始增加状态码
     * 服务特性错误 从9xx开始增加状态码
     */
    public static final class StatusCode {
        /**
         * 成功
         */
        public static final int OK = 200;//成功
        public static final int SKIP = 260;//跳过

        /**
         * 请求错误
         */
        public static final int PARAM_ERROR = 400; // 参数异常
        public static final int UNAUTHORIZED_ERROR = 401; // 需要登陆
        public static final int FORBIDDEN_ERROR = 403; // 没有权限

        /**
         * 服务错误
         */
        public static final int UNKNOWN_ERROR = 500; // 未知错误

        /**
         * 业务错误
         */
        public static final int BIZ_ERROR = 700;

        /**
         * 业务错误
         */
        public static final int BIZ_ERROR2 = 800;

        /**
         * 服务特性错误
         */
        public static final int BIZ_ERROR3 = 900;
        public static final int EXIST_FAIL = 902;//存在失败
    }

    private int code;
    private String message;

    private StatusInfo() {
    }

    public static StatusInfo create(int code, String message) {
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setCode(code);
        statusInfo.setMessage(message);
        return statusInfo;
    }
}
