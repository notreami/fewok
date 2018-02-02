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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonOutput<T> {
    public static CommonOutput OK = CommonOutput.createSuccess(null);
    public static CommonOutput FAILURE = CommonOutput.createError(ErrorInfo.PROCESS_INFO);
    /**
     * 操作结果
     */
    private boolean success;

    /**
     * 操作错误枚举类型
     */
    private ErrorInfo errorInfo;

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


    public static <K> CommonOutput<K> createSuccess(K resp) {
        CommonOutput<K> response = new CommonOutput<>();
        response.setSuccess(true);
        response.setData(resp);
        return response;
    }

    public static <K> CommonOutput<K> createError(ErrorInfo error) {
        CommonOutput<K> response = new CommonOutput<>();
        response.setErrorInfo(error);
        response.setSuccess(false);
        response.setData(null);
        return response;
    }

    public static <K> CommonOutput<K> createError(ErrorInfo error, K resp) {
        CommonOutput<K> response = new CommonOutput<>();
        response.setErrorInfo(error);
        response.setSuccess(false);
        response.setData(resp);
        return response;
    }

}
