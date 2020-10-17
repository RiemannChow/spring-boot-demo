package com.riemann.springbootdemo.model;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResultResponse {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_CLIENT_ERROR = 400;
    public static final int CODE_SERVER_ERROR = 500;

    private int code;
    private DataInfo data;
    private String msg;

    public static String build(int code) {
        return build(code, new ArrayList<>());
    }

    public static String build(int code, String msg) {
        return JSON.toJSONString(ResultResponse.builder().code(code).msg(msg).build());
    }

    public static <T> String build(int code, List<T> originalData) {
        ResultResponseBuilder builder = ResultResponse
                .builder()
                .code(code)
                .data(DataInfo.<T>builder().data(originalData).total(originalData.size()).build());
        if (code == CODE_SUCCESS) {
            if (originalData == null || originalData.size() == 0) {
                builder.msg("结果为空");
            } else {
                builder.msg("成功");
            }
        } else if (code == CODE_SERVER_ERROR) {
            builder.msg("服务器异常");
        } else if (code == CODE_CLIENT_ERROR) {
            builder.msg("输入数据有误");
        }
        return JSON.toJSONString(builder.build());
    }

    public static <T> String build(int code, String msg, List<T> originalData) {
        ResultResponseBuilder builder = ResultResponse
                .builder()
                .code(code)
                .msg(msg)
                .data(DataInfo.<T>builder().data(originalData).total(originalData.size()).build());
        if (code == CODE_SUCCESS) {
            if (originalData == null || originalData.size() == 0) {
                builder.msg("结果为空");
            }
        } else if (code == CODE_SERVER_ERROR) {
            builder.msg("服务器异常");
        } else if (code == CODE_CLIENT_ERROR) {
            builder.msg("输入数据有误");
        }
        return JSON.toJSONString(builder.build());
    }

    public static <T> String buildSelect(int code, List<T> originalData, long total) {
        ResultResponse resultResponse = getResultResponse(code, originalData, total);
        if (code == CODE_SUCCESS) {
            if (originalData == null || originalData.size() == 0) {
                resultResponse.setMsg("查询结果为空");
            } else {
                resultResponse.setMsg("查询成功");
            }
        } else if (code == CODE_SERVER_ERROR) {
            resultResponse.setMsg("查询失败,服务器异常!");
        } else if (code == CODE_CLIENT_ERROR) {
            resultResponse.setMsg("查询失败,输入数据为空!");
        }
        return JSON.toJSONString(resultResponse);
    }

    public static String buildInsert(int code) {
        ResultResponse resultResponse = getResultResponse(code, null);
        if (code == CODE_SUCCESS) {
            resultResponse.setMsg("插入成功");
        } else if (code == CODE_SERVER_ERROR) {
            resultResponse.setMsg("插入失败,服务器异常!");
        } else if (code == CODE_CLIENT_ERROR) {
            resultResponse.setMsg("插入失败,输入数据为空!");
        }
        return JSON.toJSONString(resultResponse);
    }

    public static String buildDelete(int code) {
        ResultResponse resultResponse = getResultResponse(code, null);
        if (code == CODE_SUCCESS) {
            resultResponse.setMsg("删除成功");
        } else if (code == CODE_SERVER_ERROR) {
            resultResponse.setMsg("删除失败,服务器异常!");
        } else if (code == CODE_CLIENT_ERROR) {
            resultResponse.setMsg("删除失败,输入数据为空!");
        }
        return JSON.toJSONString(resultResponse);
    }

    public static String buildUpdate(int code) {
        ResultResponse resultResponse = getResultResponse(code, null);
        if (code == CODE_SUCCESS) {
            resultResponse.setMsg("更新成功");
        } else if (code == CODE_SERVER_ERROR) {
            resultResponse.setMsg("更新失败,服务器异常!");
        } else if (code == CODE_CLIENT_ERROR) {
            resultResponse.setMsg("更新失败,输入数据为空!");
        }
        return JSON.toJSONString(resultResponse);
    }

    public static <T> ResultResponse getResultResponse(int code, List<T> originalData, long... total) {
        ResultResponseBuilder builder = ResultResponse.builder().code(code);
        if (originalData != null) {
            builder.data(DataInfo.<T>builder().data(originalData).total(total[0]).build());
        }
        return builder.build();
    }

    @Data
    @Builder
    public static class DataInfo<T> {
        List<T> data;
        long total;
    }

}
