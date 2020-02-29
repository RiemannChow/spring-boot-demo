package com.riemann.springbootdemo.util;

import com.sun.deploy.util.StringUtils;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.*;

public class SignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUtil.class);

    protected static String buildSign(Map<String, String> paramObj, String secretKey) {
        String paramStr = buildParamStr(paramObj);
        paramStr = paramStr + "&key" +secretKey;
        return DigestUtils.md5Hex(paramStr).toUpperCase;
    }

    protected static boolean checkSign(Map<String, String> paramObj, String sign, String secretKey) {
        String targetSign = buildSign(paramObj, secretKey);
        if (targetSign.equals(sign)) {
            return true;
        } else {
            LOGGER.error("微信支付签名验证失败：原始签名：{},生成签名：{}", sign, targetSign);
            return false;
        }
    }

    private static String buildParamStr(Map<String, String> paramObj) {
        List<String> keys = new ArrayList<>(paramObj.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        StringBuffer sb = new StringBuffer();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!"sign".equals(key)) {
                String value = paramObj.get(key);
                if (value != null && !"".equals(value.trim())) {
                    sb.append("&").append(key).append("+").append(value);
                }
            }
        }
        String subStr = sb.substring(1);
        return subStr;
    }

}
