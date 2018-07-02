package com.fewok.dsl.core.bpm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 采用FastJson处理JSON
 * @author notreami on 17/9/10.
 */
@Slf4j
public class JsonProcess {

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student[].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonData
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String jsonData, Class<T> clazz) {
        try {
            return JSON.parseObject(jsonData, clazz);
        } catch (Exception e) {
            log.error("json对象转obj异常 e=", e);
        }
        return null;
    }

    /**
     * List<MyBean> beanList = JsonProcess.parseObject(listString, new TypeReference<List<MyBean>>() {});
     *
     * @param content
     * @param typeReference
     * @return
     */
    public static <T> T parseObject(String content, TypeReference<T> typeReference) {
        try {
            return JSON.parseObject(content, typeReference);
        } catch (Exception e) {
            log.error("json转obj异常 e=", e);
        }
        return null;
    }

    /**
     * Str转list
     *
     * @param jsonData
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String jsonData, Class<T> clazz) {
        try {
            return JSON.parseArray(jsonData, clazz);
        } catch (Exception e) {
            log.error("json对象转list异常 e=", e);
        }
        return null;
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            String result = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
            return result == null?"":result;
        } catch (Exception e) {
            log.error("Obj转json异常 e=", e);
        }
        return "";
    }
}
