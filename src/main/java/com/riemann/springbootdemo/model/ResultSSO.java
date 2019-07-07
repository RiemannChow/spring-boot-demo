package com.riemann.springbootdemo.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author riemann
 * @date 2019/06/29 17:07
 */
public class ResultSSO {

    private static final ObjectMapper mapper = new ObjectMapper();	// 定义jackson对象

    private Integer status;	// 响应业务状态

    private String msg;		// 响应消息

    private Object data;	// 响应中的数据

    public ResultSSO() {

    }

    public ResultSSO(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultSSO(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static ResultSSO ok(Object data) {
        return new ResultSSO(data);
    }

    public static ResultSSO ok() {
        return new ResultSSO(null);
    }

    public static ResultSSO build(Integer status, String msg, Object data) {
        return new ResultSSO(status, msg, data);
    }

    public static ResultSSO build(Integer status, String msg) {
        return new ResultSSO(status, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为SSOResult对象
     *
     * @param jsonData json数据
     * @param clazz SOResult中的object类型
     * @return
     */
    public static ResultSSO formatToModel(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return mapper.readValue(jsonData, ResultSSO.class);
            }
            JsonNode jsonNode = mapper.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object object = null;
            if (clazz != null) {
                if (data.isObject()) {
                    object = mapper.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    object = mapper.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ResultSSO format(String json) {
        try {
            return mapper.readValue(json, ResultSSO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSSO formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = mapper.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object object = null;
            if (data.isArray() && data.size() > 0) {
                object = mapper.readValue(data.traverse(), mapper.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), object);
        } catch (Exception e) {
            return null;
        }
    }

}
