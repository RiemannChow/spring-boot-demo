package com.riemann.springbootdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

    private static final byte BIT_16 = 16;
    private static final byte BIT_32 = 32;

    public static String encode16(String text) {
        return encodeText(text, BIT_16);
    }

    public static String encode32(String text) {
        return encodeText(text, BIT_32);
    }

    public static String encode16(byte[] bytes) {
        return encodeBytes(bytes, BIT_16);
    }

    public static String encode32(byte[] bytes) {
        return encodeBytes(bytes, BIT_32);
    }

    private static String encodeText(String text, byte b) {
        byte[] originalBytes = null;
        try {
            originalBytes = getBytes(text);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Get bytes error, text:{}, byte:{}", text, b, e);
        }
        return encode(originalBytes, b);
    }

    private static byte[] getBytes(String text) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(text.getBytes());
        return md5.digest();
    }

    private static String encodeBytes(byte[] bytes, byte b) {
        byte[] originalBytes = null;
        try {
            originalBytes = getBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Get bytes error, text:{}, byte:{}", bytes, b, e);
        }
        return encode(originalBytes, b);
    }

    private static byte[] getBytes(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return md5.digest();
    }

    private static String encode(byte[] bytes, byte b) {
        String resStr = encode(bytes);
        if (BIT_32 == b) {
            return resStr;
        }
        return resStr.substring(8, 24);
    }

    private static String encode(byte[] bytes) {
        int i;
        StringBuffer sb = new StringBuffer();
        for (int offset = 0; offset < bytes.length; offset++) {
            i = bytes[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

}
