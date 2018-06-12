package com.fewok.common.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fewok.common.util.JsonBinder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 17/11/23.
 */

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class CommonOutput<T> implements BaseOutput {
    public static final CommonOutput PARAM_ERROR = createError(StatusInfo.PARAM_ERROR);
    public static final CommonOutput UNKNOWN_ERROR = createError(StatusInfo.UNKNOWN_ERROR);
    /**
     * 操作结果
     */
    private boolean success;

    /**
     * 操作错误枚举类型
     */
    private StatusInfo statusInfo;

    /**
     * 泛型数据ClassName
     */
    @JSONField(serialize = false)
    private String dataClassName;

    /**
     * 泛型数据JSON str
     */
    @JSONField(serialize = false)
    private String dataJsonValue;

    /**
     * 具体返回数据
     */
    private T data;

    public T getData() {
        if (data != null) {
            return data;
        } else if (dataJsonValue == null || dataJsonValue.trim().length() == 0) {
            return null;
        } else if (dataClassName == null || dataClassName.trim().length() == 0) {
            return null;
        }

        try {
            return (T) JsonBinder.parseObject(dataJsonValue, Class.forName(dataClassName));
        } catch (Exception e) {
            log.error("数据转换异常, clazz={}, value={}", dataClassName, dataJsonValue, e);
            return null;
        }
    }

    public void setData(T data) {
        this.data = data;
        if (data != null) {
            setDataClassName(data.getClass().getName());
            setDataJsonValue(JsonBinder.toJSONString(data));
        }
    }

    public static <T> CommonOutput<T> createSuccess(T resp, StatusInfo info) {
        CommonOutput<T> result = new CommonOutput<>();
        result.setSuccess(true);
        result.setStatusInfo(info);
        result.setData(resp);
        return result;
    }

    public static <T> CommonOutput<T> createError(StatusInfo info) {
        CommonOutput<T> result = new CommonOutput<>();
        result.setSuccess(false);
        result.setStatusInfo(info);
        result.setData(null);
        return result;
    }

    public static <T> CommonOutput<T> createError(T resp, StatusInfo info) {
        CommonOutput<T> result = new CommonOutput<>();
        result.setSuccess(false);
        result.setStatusInfo(info);
        result.setData(resp);
        return result;
    }

}
