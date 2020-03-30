package com.riemann.springbootdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用签名工具
 */
public class AppSignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSignUtil.class);

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

    // 签名校验
    public static boolean checkSign(String timestamp, String appCode, String appSecret, String sign) {
        Map<String, String> paramObj = new HashMap<>();
        paramObj.put(TIMESTAMP, timestamp);
        paramObj.put(APP_CODE, appCode);
        return SignUtil.checkSign(paramObj, sign, appSecret);
    }

    /**
     * 获取HMAC-SHA256签名
     *
     * @param paramMap    签名参数（sign不参与签名）
     * @param appSecret   签名密钥
     * @return            HMAC-SHA256签名结果
     */
    public static String sha256Sign(Map<String, Object> paramMap, String appSecret) {
        try {
            String payParam = getSignPay(paramMap, appSecret);
            LOGGER.info("签名原文:{}", payParam);
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(appSecret.getBytes("UTF-8"), "HmacSHA256");
            sha256HMAC.init(secretKey);
            byte[] array = sha256HMAC.doFinal(payParam.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            String sign = sb.toString().toUpperCase();
            LOGGER.info("签名结果:{}", sign);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取签名参数字符串
     *
     * @param paramMap    签名参数（sign字段不参与签名）
     * @param appSecret   签名密钥
     * @return            待签名字符串
     */
    private static String getSignPay(Map<String, Object> paramMap, String appSecret) {
        ArrayList<String> keyList = new ArrayList<>(paramMap.keySet());
        Collections.sort(keyList);
        StringBuilder sbSignParam = new StringBuilder();
        for (String key : keyList) {
            if (!"sign".equals(key) && paramMap.get(key) != null) {
                sbSignParam.append(key).append("=").append(paramMap.get(key)).append("&");
            }
        }
        sbSignParam.delete(sbSignParam.length() - 1, sbSignParam.length());
        sbSignParam.append("&key=").append(appSecret);
        return sbSignParam.toString();
    }

}
