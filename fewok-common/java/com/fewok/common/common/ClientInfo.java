package com.fewok.common.common;

import lombok.*;

/**
 * 调用方的信息
 * @author notreami on 17/9/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo {
    /**
     * 以下参数可选
     * clientKey 默认调用方的clientkey
     * clientIp 默认调用机器的ip
     * platform 由调用方区分,本服务不做限制
     */
    private String clientAppKey;
    private String clientIp;

    /**
     * 以下参数为客户端固有信息
     */

    /**
     * 请求的客户端平台
     */
    private Platform platform;

    /**
     * 请求的来源
     */
    private Originate originate;

    /**
     * 额外信息,比如UTM,埋点的信息等,组json,服务端根据需求解析
     */
    private String extInfo;

    /**
     * 用户的UUID
     */
    private String uuid;

    /**
     * 客户端版本号
     */
    private String version;

    /**
     * 用户登录token
     */
    private String accessToken;

    /**
     * 客户端签名
     */
    private String printFinger;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户请求的IP
     */
    private String userIp;

    /**
     * 用户请求的定位城市
     */
    private Integer cityId;


    @Getter
    @AllArgsConstructor
    public enum Platform {
        /**
         * Android
         */
        ANDROID(1, "Android"),
        /**
         * iPhone
         */
        IPHONE(2, "iPhone"),
        /**
         * iPad
         */
        IPAD(2, "iPad"),
        /**
         * wp
         */
        WP(3, "wp"),
        /**
         * wap
         */
        WAP(4, "wap"),
        /**
         * pc
         */
        PC(5, "pc"),
        /**
         * weChat
         */
        WECHAT(6, "weChat");

        private int code;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum Originate {
        /**
         * 本服务
         */
        ONESELF(1, "本服务"),
        /**
         * 前置服务（前端）
         */
        FT(2, "前端");

        private int code;
        private String desc;
    }
}
