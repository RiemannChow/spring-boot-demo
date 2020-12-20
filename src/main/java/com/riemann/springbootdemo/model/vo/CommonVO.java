package com.riemann.springbootdemo.model.vo;

import lombok.Data;
import org.slf4j.MDC;

import java.util.List;

@Data
public class CommonVO {

    private Integer code;

    private Object data;

    private String msg;

    private List<String> log;

    private String traceId;

    private long timestamp;

    public CommonVO(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public CommonVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonVO(Integer code, String msg, String traceId) {
        this.code = code;
        this.msg = msg;
        this.traceId = traceId;
    }

    public CommonVO(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public CommonVO(Integer code, Object data, String msg, List<String> log) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.log = log;
    }

    public static CommonVO success(Object data, List<String> log) {
        CommonVO commonVO = new CommonVO(CommonVOCode.SUCCESS, data,null,log);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO success(Object data) {
        CommonVO commonVO = new CommonVO(CommonVOCode.SUCCESS, data);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO success() {
        CommonVO commonVO = success(null);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO badParams(String msg) {
        CommonVO commonVO = new CommonVO(CommonVOCode.BAD_PARAMS, msg);
        commonVO.setTimestamp(System.currentTimeMillis());
        return new CommonVO(CommonVOCode.BAD_PARAMS, msg);
    }

    public static CommonVO badParams(Object data) {
        CommonVO commonVO = badParams(data, null);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO badParams(Object data, String msg) {
        CommonVO commonVO = new CommonVO(CommonVOCode.BAD_PARAMS, data, msg);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO badParams() {
        CommonVO commonVO = badParams(null);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO error(Integer code, String msg, List<String> log) {
        CommonVO commonVO = new CommonVO(code, null,msg,log);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO error(Integer code, String msg) {
        CommonVO commonVO = new CommonVO(code, msg, MDC.get("traceId"));
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO error(Object data) {
        CommonVO commonVO = new CommonVO(CommonVOCode.SERVER_ERROR, data);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

    public static CommonVO error() {
        CommonVO commonVO = error(null);
        commonVO.setTimestamp(System.currentTimeMillis());
        return commonVO;
    }

}