package com.riemann.springbootdemo.util;

import java.util.HashMap;
import java.util.Map;

public class AppSignUtil {

    private static final String TIMESTAMP = "timestamp";

    private static final String APP_CODE = "appCode";

    public static final String SIGN = "sign";

    // 获取签名
    public static String getSign(String timestamp, String appCode, String appSecret) {
        Map<String, String> paramObj = new HashMap<>();
        paramObj.put(TIMESTAMP, timestamp);
        paramObj.put(APP_CODE, appCode);
        return SignUtil.buildSign(paramObj, appSecret);
    }

    public static boolean checkSign(String timestamp, String appCode, String appSecret, String sign) {
        Map<String, String> paramObj = new HashMap<>();
        paramObj.put(TIMESTAMP, timestamp);
        paramObj.put(APP_CODE, appCode);
        return SignUtil.checkSign(paramObj, sign, appSecret);
    }

}
