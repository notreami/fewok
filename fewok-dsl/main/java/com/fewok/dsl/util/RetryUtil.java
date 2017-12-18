package com.fewok.dsl.util;

import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 重试
 * @author notreami on 17/11/23.
 */
public class RetryUtil {

    /**
     * 重试指定retry count次数,并且将EXCEPTION纪录在指定的LOGGER中
     *
     * @param executor     外部实现业务
     * @param retryCount   重试次数
     * @param exceptionLog 指定EXCEPTION LOG
     * @param <T>          指定返回类型
     * @return
     */
    public static <T> T retryByCountWhenException(IExecutor<T> executor, int retryCount, Logger exceptionLog) {
        for (int i = 0; i < retryCount; i++) {
            try {
                return executor.execute();
            } catch (Exception e) {
                if (exceptionLog != null) {
                    exceptionLog.error("retryBySetCountWithRetValue ,retry count : " + (i + 1) + ",error:{}", e);
                }
            }
        }

        return null;
    }

    /**
     * 对返回boolean类型的方法进行重试
     *
     * @param executor
     * @param retryCount
     * @param exceptionLog
     * @return
     */
    public static boolean retryByCountWhenReturnTrue(IBooleanExecutor executor, int retryCount, Logger exceptionLog) {
        for (int i = 0; i < retryCount; i++) {
            try {
                if (executor.execute()) {
                    return true;
                }
            } catch (Exception e) {
                if (exceptionLog != null) {
                    exceptionLog.error("retryByCountWhenReturnTrue ,retry count : " + (i + 1) + ",error:{}", e);
                }
            }
        }
        return false;
    }

    /**
     * 重试指定retry count次数,并且将EXCEPTION纪录在指定的LOGGER中
     *
     * @param executor        外部实现业务
     * @param retryCount      重试次数
     * @param exceptionLog    指定EXCEPTION LOG
     * @param <T>             指定返回类型
     * @param isFinalPrintLog 最终是否打印异常
     * @return
     */
    public static <T> T retryByCountWhenException(IExecutor<T> executor, int retryCount, Logger exceptionLog, boolean isFinalPrintLog) {
        Throwable err = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                return executor.execute();
            } catch (Exception e) {
                if (!isFinalPrintLog && exceptionLog != null) {
                    exceptionLog.error("retryBySetCountWithRetValue ,retry count : " + (i + 1) + ",error:{}", e);
                }
                err = e;
            }
        }
        if (isFinalPrintLog && exceptionLog != null) {
            exceptionLog.error("retryBySetCountWithRetValue ,retry count : " + retryCount + ",error:{}", err);
        }

        return null;
    }

    /**
     * 重试指定retry count次数,并且将EXCEPTION纪录在指定的LOGGER中,并且每次失败时候休息一下
     *
     * @param executor
     * @param retryCount
     * @param exceptionLog
     * @param sleepMillis
     * @param <T>
     * @return
     */
    public static <T> T retryByCountWhenException(IExecutor<T> executor, int retryCount, Logger exceptionLog, long sleepMillis, String errorMsg) {
        Exception exception = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                return executor.execute();
            } catch (Exception e) {
                if (exceptionLog != null) {
                    exceptionLog.error("retryBySetCountWithRetValue , model or reason={} ,retry count : " + (i + 1) + ",error:{}", errorMsg, e);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepMillis);
                } catch (Exception ex) {
                    if (exceptionLog != null) {
                        exceptionLog.error("retryByCountWhenException thread is interrupted ! model or reason={}  error:{}", errorMsg, ex);
                    }
                }
                exception = e;
            }
        }
        if (exceptionLog != null) {
            exceptionLog.error("retryBySetCountWithRetValue , model or reason={}, retry count:{},error:{}",
                    new Object[]{errorMsg, retryCount, exception});
        }
        return null;
    }

    /**
     * 根据返回结果重试指定retry count次数,并且将EXCEPTION纪录在指定的LOGGER中,并且每次失败时候休息一下
     *
     * @param executor
     * @param callback
     * @param retryCount
     * @param exceptionLog
     * @param sleepMillis
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> T retryByCountWhenCondition(IExecutor<T> executor, ICallback<T> callback, int retryCount,
                                                  Logger exceptionLog, long sleepMillis, String errorMsg, String rotationMsg) {

        T t = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                t = executor.execute();
                if (callback.execute(t)) {
                    return t;
                }
            } catch (Exception e) {
                if (exceptionLog != null) {
                    exceptionLog.error("retryByCountWhenCondition , model or reason={} ,retry count : " + (i + 1) + ",error:{}", errorMsg, e);
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(sleepMillis);
            } catch (Exception ex) {
                if (exceptionLog != null) {
                    exceptionLog.error("retryByCountWhenCondition thread is interrupted ! model or reason={}  error:{}", errorMsg, ex);
                }
            }
            if (exceptionLog != null) {
                exceptionLog.info(rotationMsg + ",第{}次查询", i + 1);
            }
        }
        return t;
    }

    /**
     * 留给外部实现
     *
     * @param <T>
     */
    public interface IExecutor<T> {
        T execute() throws Exception;
    }

    /**
     * 自定义结果回调函数
     *
     * @param <T>
     */
    public interface ICallback<T> {
        boolean execute(T t) throws Exception;
    }

    /**
     * 返回boolean类型的执行器
     */
    public interface IBooleanExecutor extends IExecutor<Boolean> {
    }
}
