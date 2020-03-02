package com.riemann.springbootdemo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author riemann
 * @date 2019/06/30 0:18
 */
public class JsonUtil {

    private static final ObjectMapper jsonMapper;

    static {
        jsonMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonMapper.setDateFormat(df);
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换成json字符串。
     */
    public static String bean2Json(Object data) {
        try {
            return jsonMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据Class<?>对象,将Object转换成对应的类型
     */
    public static String bean2Json(Object data, Class<?> clazz) {
        try {
            return jsonMapper.writerFor(clazz).writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T json2Bean(String jsonData, Class<T> beanType) {
        try {
            return jsonMapper.readValue(jsonData, beanType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json数据转换成Object对象list
     */
    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        try {
            JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(List.class, beanType);
            return jsonMapper.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取泛型的Collection Type
     */
    public static <T> T json2Bean(String jsonData, Class<T> collectionClass, Class<?>... elementClasses) {
        try {
            JavaType javaType = jsonMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return jsonMapper.readValue(jsonData, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
