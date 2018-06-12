package com.fewok.common.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fewok.common.enums.OperatorType;
import com.fewok.common.enums.ProcessType;
import com.fewok.common.util.JsonBinder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author notreami on 17/11/23.
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "统一输入模型", description = "统一输入模型")
public class CommonInput<T extends BaseInput> {

    @ApiModelProperty(value = "调用方信息")
    private ClientInfo clientInfo;

    @ApiModelProperty(value = "操作类型")
    private ProcessType processType;

    @ApiModelProperty(value = "操作人类型")
    private OperatorType operatorType;

    @ApiModelProperty(value = "操作人(用户:userId；商家: 登录用户名/账号/联系人 等；客服: 客服编号)")
    private String operator;

    /**
     * 泛型数据ClassName
     */
    @JSONField(serialize = false)
    private String dataClassName;

    /**
     * 泛型数据JSON串
     */
    @JSONField(serialize = false)
    private String dataJsonValue;
    /**
     * 操作对应的数据
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
}
