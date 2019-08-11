package com.riemann.springbootdemo.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author riemann
 * @date 2019/08/10 17:20
 */
public class IPUtil {

    /**
     * 获取真实的IP
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0,index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 判断IP是否在指定范围
     * @param ipStart
     * @param ipEnd
     * @param ip
     * @return
     */
    public static boolean ipIsValid(String ipStart,String ipEnd, String ip) {
        if (StringUtils.isEmpty(ipStart)) {
            throw new NullPointerException("起始IP不能为空！");
        }
        if (StringUtils.isEmpty(ipEnd)) {
            throw new NullPointerException("结束IP不能为空！");
        }
        if (StringUtils.isEmpty(ip)) {
            throw new NullPointerException("IP不能为空！");
        }
        ipStart = ipStart.trim();
        ipEnd = ipEnd.trim();
        ip = ip.trim();
        final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
        final String REGX_IPB = REGX_IP + "\\-" + REGX_IP;
        if (!ipStart.matches(REGX_IP) || !ip.matches(REGX_IP) || !ipEnd.matches(REGX_IP)) {
            return false;
        }
        String[] sips = ipStart.split("\\.");
        String[] sipe = ipEnd.split("\\.");
        String[] sipt = ip.split("\\.");
        long ips = 0L, ipe = 0L, ipt = 0L;
        for (int i = 0; i < 4; ++i) {
            ips = ips << 8 | Integer.parseInt(sips[i]);
            ipe = ipe << 8 | Integer.parseInt(sipe[i]);
            ipt = ipt << 8 | Integer.parseInt(sipt[i]);
        }
        if (ips > ipe) {
            long t = ips;
            ips = ipe;
            ipe = t;
        }
        return ips <= ipt && ipt <= ipe;
    }

}

